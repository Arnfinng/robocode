package voidious.micro;

import robocode.*;
import robocode.util.Utils;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * BlizBat - a robot by Voidious
 *
 * A MicroBot melee specialist. Uses Minimum Risk movement and Head-On
 * Targeting. Initially whipped up one afternoon while working on
 * Diamond and waiting for test beds to run.
 *
 * Code is open source, released under the RoboWiki Public Code License:
 * http://robowiki.net/cgi-bin/robowiki?RWPCL
 */

public class BlitzBat extends AdvancedRobot {
    protected static final double TWO_PI = Math.PI * 2;
    
    protected static Rectangle2D.Double fieldItWillStayWithin;
    protected static Point2D.Double whereItWantsToGo;
    protected static String targetName;
    protected static double targetDistance;
    protected static Map<String, EnemyData> enemies = new HashMap();
    protected static Point2D.Double location;
    protected List<Point2D.Double> recentLocations;
    
    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setColors(new Color(90, 40, 12), Color.black, Color.black);
        
        fieldItWillStayWithin = new Rectangle2D.Double(50, 50, 
                getBattleFieldWidth() - 100, getBattleFieldHeight() - 100);
        recentLocations = new ArrayList();
        targetDistance = Double.POSITIVE_INFINITY;
        whereItWantsToGo = null;
        
        do {
            Point2D.Double myLocation = location = 
                new Point2D.Double(getX(), getY());
            recentLocations.add(0, myLocation);
            EnemyData targetData = (EnemyData)enemies.get(targetName);
            
            attack(myLocation, targetData);
            move(myLocation);
            setTurnRadarRightRadians(1);
            execute();
        } while (true);        
    }

	private void move(Point2D.Double myLocation) {
		double bestRisk;
		try {
		    bestRisk = evalDestinationRisk(whereItWantsToGo) * .85;
		} catch (NullPointerException ex) {
		    bestRisk = Double.POSITIVE_INFINITY;
		}
		try {
		    for (double d = 0; d < TWO_PI; d += 0.1) {
		        Point2D.Double newDest = project(myLocation, d,
		            Math.min(targetDistance, 100 + Math.random() * 200));
		        if (fieldItWillStayWithin.contains(newDest) &&
		                evalDestinationRisk(newDest) < bestRisk) {
		            whereItWantsToGo = newDest;
		        }
		    }
		    
		    double angle = Utils.normalRelativeAngle(
		        absoluteBearing(myLocation, whereItWantsToGo) -
		        getHeadingRadians());
		    setTurnRightRadians(Math.tan(angle));
		    setAhead(Math.cos(angle) * Double.POSITIVE_INFINITY);
		} catch (NullPointerException ex) { }
	}

	private void attack(Point2D.Double myLocation, EnemyData targetData) {
		try {
		    setTurnGunRightRadians(Utils.normalRelativeAngle(
		        absoluteBearing(myLocation, targetData) - 
		        getGunHeadingRadians()));
		} catch (NullPointerException ex) { }
		setFire(3 - ((20 - getEnergy()) / 6));
	}
    
    public void onScannedRobot(ScannedRobotEvent e) {
        double eDistance = e.getDistance();
        
        EnemyData eData = new EnemyData();
        eData.setLocation(project(location, e.getBearingRadians() + 
            getHeadingRadians(), eDistance));
        eData.energy = e.getEnergy();

        enemies.put(e.getName(), eData);
        
        if (eDistance < targetDistance || 
                e.getName().equals(targetName)) {
            targetDistance = eDistance;
            targetName = e.getName();
        }
    }
    
    public void onRobotDeath(RobotDeathEvent e) {
        enemies.remove(e.getName());
        targetDistance = Double.POSITIVE_INFINITY;
    }
    
    protected double evalDestinationRisk(Point2D.Double d) {
        double risk = 0;
        
        Iterator enemiesIterator = enemies.values().iterator();
        while (enemiesIterator.hasNext()) {
            EnemyData ed = (EnemyData)enemiesIterator.next();
            double distSq = ed.distanceSq(d);
            int closer = 1;
            Iterator enemiesIterator2 = enemies.values().iterator();
            while (enemiesIterator2.hasNext()) {
                if (ed.distanceSq((EnemyData)enemiesIterator2.next()) 
                    < distSq) {
                    
                    closer++;
                }
            }

            risk += Math.max(0.5, Math.min(ed.energy / getEnergy(), 2))
                * (1 + Math.cos(absoluteBearing(location, d) - 
                        absoluteBearing(location, ed)))
//                * (12 + Math.sqrt(cornerDistance(d)))
                / closer
                / distSq
                / (300000 + d.distanceSq(
                    getBattleFieldWidth() / 2, getBattleFieldHeight() / 2));
        }
        
        for (int x = 1; x < 6; x++) {
            try {
                risk *= 1 + (500 / x / 
                    ((Point2D.Double)recentLocations.get(x * 10))
                        .distanceSq(d));
            } catch (Exception ex) { }
        }
        
        return risk;
    }

    protected static double absoluteBearing(Point2D.Double source, Point2D.Double target) {
        return Math.atan2(target.x - source.x, target.y - source.y);
    }
    
    public static Point2D.Double project(Point2D.Double sourceLocation, 
        double angle, double length) {

        return new Point2D.Double(sourceLocation.x + Math.sin(angle) * length,
            sourceLocation.y + Math.cos(angle) * length);
    }
}

class EnemyData extends Point2D.Double{
    double energy;
}