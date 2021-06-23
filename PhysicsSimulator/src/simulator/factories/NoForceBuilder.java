package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	
	
	public NoForceBuilder() {
		this.type = "ng";
		this.desc = "No Force";
		
	}

	@Override
	ForceLaws createTheInstance(JSONObject obj) {
				
		return new NoForce();
		
	}
	
	public JSONObject createData() {
        return new JSONObject();
    }

}
