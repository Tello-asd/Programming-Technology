package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.RocketBody;

public class RocketBodyBuilder extends Builder<Body> {
	
	public RocketBodyBuilder() {
		this.type = "rocket";
		this.desc = "rocket desc";
		
	}

	@Override
	Body createTheInstance(JSONObject obj) {
		// TODO Auto-generated method stub
		  return new RocketBody(obj.getString("id"), new Vector2D(obj.getJSONArray("p").getDouble(0),obj.getJSONArray("p").getDouble(1)), new Vector2D(obj.getJSONArray("v").getDouble(0),obj.getJSONArray("v").getDouble(1)), obj.getDouble("m"), obj.getDouble("comb"), obj.getDouble("loss"));
	}

}
