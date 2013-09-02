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
		
        setColors(new Color(90, 40, 12), Color.green, Color.green);   
		
		while(true){
			doScan();
			move();
			execute();
		}
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
		setTurnRadarLeftRadians(2*Math.PI);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		// TODO Auto-generated method stub
		super.onScannedRobot(e);
		if(e.getDistance()<300){
			fire(2);
		}
	}
	
	
	@Override
	public void onHitWall(HitWallEvent event) {

		setTurnRight(Math.random()*30+90);
		
		back(70+Math.random()*30);
		
	}
	
	
	
}
