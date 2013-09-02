import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import robocode.AdvancedRobot;


public class ProRobot extends AdvancedRobot{
	
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
		// TODO Auto-generated method stub
		
	}

	private void move() {
		// TODO Auto-generated method stub
		
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
