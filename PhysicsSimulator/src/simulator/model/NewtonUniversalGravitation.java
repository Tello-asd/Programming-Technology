package simulator.model;
import java.util.List;
import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{

	private double G;

	public NewtonUniversalGravitation(double g) {
		this.G = g; // we will mainly use G = 6.67E−11
	}
	
//  # NewtonUniversalGravitation.java:
//
//      - you should not apply force when bi==bj
//
//      - why not putting the distance in a local variable?
//
//          if(Math.pow(Math.abs((bj.getPosition().distanceTo(bi.getPosition()))), 2) > 0) { // if |~pj − ~pi|2  > 0
//              fij = this.G * ((bi.getMass() * bj.getMass()) / Math.pow((bj.getPosition().distanceTo(bi.getPosition())), 2));
//                      }
//      - you are not using the direction:
//
//          dij = bj.getPosition().minus(bi.getPosition());

//Added if condition (bi != bj)
//Added distance variable
//Now we are using direction d.direction();

	public void apply(List<Body> bs) { 
		double fij = 0.0;
		Vector2D dij = new Vector2D();
		Vector2D Fij = new Vector2D();
		double distancePjPi = 0.0;

		for(Body bi : bs) {
			for(Body bj : bs) {

				if(bi != bj) {

					dij = bj.getPosition().minus(bi.getPosition());
					dij = dij.direction();

					distancePjPi = bj.getPosition().distanceTo(bi.getPosition());
					double powDistancePjPi = Math.pow(distancePjPi, 2);

					if(Math.abs(powDistancePjPi) > 0) { // if |~pj − ~pi|2  > 0
						fij = this.G * ((bi.getMass() * bj.getMass()) / powDistancePjPi);	
					}
					
				}
				Fij = dij.scale(fij); //we compute ~di,j and scale it by by fi,j			

				bi.addForce(Fij); 
			}
		}
	}

	public String toString() {
		return "Newtons Universal Gravitation = " + G;
	}

}
