package simulator.factories;


import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	public MovingTowardsFixedPointBuilder() {
		this.type = "mtfp";
		this.desc = "Moving towards fixed point";
		
	}
	
//  # MovingTowardsFixedPointBuilder.java:
//
//      - you cannot modify obj, use local variables with default values for g and c and modify them if obj has corresponding keys

//      fixed.

	@Override
	ForceLaws createTheInstance(JSONObject obj) {

        double g = 9.81;
        JSONArray c = new Vector2D().asJSONArray();

        if(obj.has("c")) {
            c = obj.getJSONArray("c");
        }
        if(obj.has("g")) {
            g = obj.getDouble("g");
        }
                
        return new MovingTowardsFixedPoint(new Vector2D(c.getDouble(0), c.getDouble(1)), g);
    }
	
	public JSONObject createData() {
        JSONObject jo = new JSONObject();

        jo.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0, 50.0])");
        jo.put("g", "the length of the acceleration vector (a number)");

        return jo;
    }
	
}
