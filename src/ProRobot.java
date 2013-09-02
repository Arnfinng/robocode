import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import robocode.AdvancedRobot;


public class ProRobot extends AdvancedRobot{
	HashMap<String, Enemy> enemies = new HashMap();
	Enemy target;
	@Override
	public void run() {
		
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setColors(new Color(90, 40, 12), Color.green, Color.green);   
		
		while(true){
			doScan();
			move();
			shoot();
			execute();
		}
	}
	
	private void shoot() {
		double firePower = getFirePower();
		long time = getTime() + (int)Math.round((Math.sqrt( Math.pow(getX()+target.xPos, 2) + Math.pow(getX()+target.xPos, 2))/(20-(3*firePower))));
		ArrayList<Double> hitPos = target.guessPosition(time);
		double gunOffset = getGunHeadingRadians() - (Math.PI/2 - Math.atan2(hitPos.get(0) - getY(), hitPos.get(1) - getX()));
		setTurnGunLeftRadians(gunOffset);
		}

	private double getFirePower() {
		return 3;
	}

	private void move() {
		ahead(30);
	}

	private void doScan() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		// TODO Auto-generated method stub
		super.onScannedRobot(event);
	}
	
}
