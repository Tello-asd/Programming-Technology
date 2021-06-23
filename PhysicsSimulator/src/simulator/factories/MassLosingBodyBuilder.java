package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	
	public MassLosingBodyBuilder() {
		this.type = "mlb";
		this.desc = "Mass Loosing Body";
		
	}

	@Override
	Body createTheInstance(JSONObject obj) {
		
		return new MassLossingBody(obj.getString("id"), new Vector2D(obj.getJSONArray("p").getDouble(0),obj.getJSONArray("p").getDouble(1)), new Vector2D(obj.getJSONArray("v").getDouble(0),obj.getJSONArray("v").getDouble(1)), obj.getDouble("m"), obj.getDouble("freq"), obj.getDouble("factor"));
		
		
	}

}
