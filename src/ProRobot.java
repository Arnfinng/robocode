import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import java.awt.Color;

import robocode.AdvancedRobot;


public class ProRobot extends AdvancedRobot{
	
	boolean forward=true;
	double speed=0.0;
	@Override
	public void run() {
		
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setColors(new Color(90, 40, 12), Color.green, Color.green);   
		
		while(true){
			doScan();
			move();
			shoot();
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
