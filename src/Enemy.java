import java.util.ArrayList;


public class Enemy {
	String name;
	double xPos;
	double yPos;
	guessPoition(){
		ArrayList<Double> pos = new ArrayList<>();
		pos.add(xPos);
		pos.add(yPos);
		return pos;
	}
}
