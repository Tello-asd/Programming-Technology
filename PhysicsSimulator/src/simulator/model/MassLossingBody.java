package simulator.model;


import simulator.misc.Vector2D;

public class MassLossingBody extends Body {

	protected double lossFactor; // between 0 and 1 indicating the mass loss factor.
	protected double lossFrequency; // indicating the time interval (in seconds) after which the object loses mass.
	protected double c = 0.0;


	public MassLossingBody(String id, Vector2D position, Vector2D velocity, double mass, double lossFrequency, double lossFactor) {
		super(id, position, velocity, mass);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;

	}
	

	public void move(double t) {

		super.move(t);

		
		
		c+= t; // use a counter c (initially 0.0) to accumulate time (i.e., parameter t of move)

		if(c >= this.lossFrequency) { //when c <= lossFrequency
			this.m = this.m * (1 - this.lossFactor); //we apply reduction
			c = 0.0; //set again to 0
		}

	}

}
