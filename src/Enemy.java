import java.util.ArrayList;


public class Enemy {
	String name;
	double xPos;
	double yPos;
	public ArrayList<Double> guessPosition(long time){
		ArrayList<Double> pos = new ArrayList<>();
		pos.add(xPos);
		pos.add(yPos);
		return pos;
	}
}
