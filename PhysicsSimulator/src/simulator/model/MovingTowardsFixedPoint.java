package simulator.model;
import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	protected Vector2D vector_c;
	protected double g;
	
	public MovingTowardsFixedPoint(Vector2D fixedPoint, double strength) {
		this.vector_c = fixedPoint;
		this.g = strength;
	}
	
//  # MovingTowardsFixedPoint.java:
//
//      - you are not using the direction:
//
//                  bi.addForce((this.vector_c.minus(bi.getPosition())).scale(bi.getMass()*this.g));

//      Now we are using direction d.direction();

	@Override
	public void apply(List<Body> bs) {
		
		Vector2D d = new Vector2D();		
		
		for(Body bi : bs) {
			d = vector_c.minus(bi.getPosition());
			d = d.direction();
            bi.addForce(d.scale(bi.getMass()*g));			
		}		
	}
	
	public String toString() {
		return "Move towards fixed point = " + vector_c + " with constant acceleration " + g;
		
	}

}
