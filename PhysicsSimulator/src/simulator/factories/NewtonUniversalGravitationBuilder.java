package simulator.factories;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	public NewtonUniversalGravitationBuilder() {
		this.type = "nlug";
		this.desc = "Newton Universal Gravitation";
		
	}

//  # NewtonUniversalGravitationBuilder.java:
//
//      - you cannot modify obj, use a local variables "double F=6.67E-11" and modify its value if obj has a key "G"


//      Fixed.
	@Override
	ForceLaws createTheInstance(JSONObject obj) {

		double G = 6.67E-11;

		if(obj.has("G")) {
			G = obj.getDouble("G");
		}

		return new NewtonUniversalGravitation(G);

	}

	public JSONObject createData() {
		JSONObject jo = new JSONObject();
		jo.put("G" , "the gravitational constant (a number) ");
		return jo;
	}

}
