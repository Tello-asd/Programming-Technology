package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class Body {

	protected String id;
	protected Vector2D vector_f, vector_v, vector_p;
	protected double m;	

	public Body(String id, Vector2D position, Vector2D velocity, double mass) {
		this.id = id;
		this.vector_v = velocity;
		this.vector_p = position;
		this.m = mass;
		this.vector_f = new Vector2D(); //initialize to 0.0		
		
	}
	
//  # Body.java:
//
//       - addForce, resetForce and move should not be public
//
//       - in move, even if the mass is zero you should change position/veclocity

//   Changed to protected and position/velocity now changes.
	
	protected void addForce(Vector2D f) {		
		this.vector_f = this.vector_f.plus(f);
	}

	protected void resetForce() {
		this.vector_f = new Vector2D();
	}

	protected void move(double t) {		
		Vector2D acceleration = new Vector2D();
		if(this.m <= 0) {
			acceleration = new Vector2D(); //acceleration to (0,0)
		}
		
		else {
			acceleration = this.vector_f.scale(1.0/this.m); //1
			
		}
		this.vector_p = this.vector_p.plus(this.vector_v.scale(t).plus(acceleration.scale(1/2).scale(t*t))); //2
		this.vector_v = this.vector_v.plus(acceleration.scale(t)); //3
	}	
	
	public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if(getClass() != obj.getClass())
            return false;

        Body aux = (Body) obj;

        if(id == null) {

            if(aux.id != null)
                return false;

        }
        else if (!id.equals(aux.id))
            return false;

        return true;

    }

	public JSONObject getState() {
		JSONObject json = new JSONObject();

		json.put("id", this.id);
		json.put("m", this.m);
		json.put("p", this.vector_p.asJSONArray());
		json.put("v", this.vector_v.asJSONArray());
		json.put("f", this.vector_f.asJSONArray());
		

		return json;

	}

	public String toString() {
		return this.getState().toString();

	}
	
	
	/*SOME GETTERS*/
	public String getId() {
		return id;

	}

	public double getMass() {
		return m;

	}

	public Vector2D getPosition() {
		return vector_p;

	}

	public Vector2D getVelocity() {
		return vector_v;

	}

	public Vector2D getForce() {
		return vector_f;

	}	

	public void setVector_p(Vector2D vector_p) {
		this.vector_p = vector_p;
	}
	
	
}



