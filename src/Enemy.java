import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Enemy {
	String name;
	double time;
	double speed;
	double distance;
	double bearing;
	
	double heading;
	public double y;
	public double x;
	public ArrayList<Double> guessPosition(long time){
		double diff = this.time - time;
		double newY = y + Math.cos(heading) * speed * diff;
		double newX = x + Math.sin(heading) * speed * diff;
		ArrayList<Double> pos = new ArrayList<Double>();
		pos.add(newX);
		pos.add(newY);
		return pos;
	}
}
