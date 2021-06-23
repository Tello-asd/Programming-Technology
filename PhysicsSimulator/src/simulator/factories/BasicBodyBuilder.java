package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	
	public BasicBodyBuilder() {
		this.type = "basic";
		this.desc = "Basic Body";
		
	}

	@Override
	Body createTheInstance(JSONObject obj) {
		
		return new Body(obj.getString("id"), new Vector2D(obj.getJSONArray("p").getDouble(0),obj.getJSONArray("p").getDouble(1)), new Vector2D(obj.getJSONArray("v").getDouble(0),obj.getJSONArray("v").getDouble(1)), obj.getDouble("m"));
	}	

}
