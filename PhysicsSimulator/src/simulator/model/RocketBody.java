package simulator.model;

import simulator.misc.Vector2D;

public class RocketBody extends Body{
	
	protected Double comb;
	protected Double loss;

	public RocketBody(String id, Vector2D position, Vector2D velocity, Double mass, double comb, Double loss) {
		super(id, position, velocity, mass);
		this.comb = comb;
		this.loss = loss;
		
	}
	
	public void move(double t) {
		Vector2D p = super.getPosition();
		Vector2D v = super.getVelocity();
		
		if(this.comb > 0) 
		{
			p = p.plus(v.scale(t)); //p = p+(v*t);			
			super.setVector_p(p);			
		}
		
		else
		{
			super.move(t);
		}
	}
}
