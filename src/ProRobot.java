import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import java.awt.Color;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import robocode.AdvancedRobot;


public class ProRobot extends AdvancedRobot{
	
	boolean forward=true;
	double speed=0.0;
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
		
	}

	private void move() {
		
		double angle=1-Math.random()*2;
		
		setTurnLeft(30*angle);
		
		
		if(Math.random()<0.2)
			forward=!forward;
		
		double speed=70*Math.random()+50;
		
		if(forward)
			ahead(speed);
		else
			back(speed);
			
		
		
		double firePower = getFirePower();
		long time = getTime() + (int)Math.round((Math.sqrt( Math.pow(getX()+target.xPos, 2) + Math.pow(getX()+target.xPos, 2))/(20-(3*firePower))));
		ArrayList<Double> hitPos = target.guessPosition(time);
		double gunOffset = getGunHeadingRadians() - (Math.PI/2 - Math.atan2(hitPos.get(0) - getY(), hitPos.get(1) - getX()));
		setTurnGunLeftRadians(gunOffset);
		}

	private double getFirePower() {
		return 3;
	}


	private void doScan() {
		
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		super.onScannedRobot(event);
	}
	
	
	@Override
	public void onHitWall(HitWallEvent event) {

		setTurnRight(Math.random()*30+90);
		
		back(70+Math.random()*30);
		
	}
	
	
	
}
