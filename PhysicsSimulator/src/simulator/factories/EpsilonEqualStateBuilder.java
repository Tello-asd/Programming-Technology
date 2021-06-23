package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;
import simulator.misc.Vector2D;
import simulator.model.Body;

public class EpsilonEqualStateBuilder extends Builder<StateComparator> {
	
	public EpsilonEqualStateBuilder() {
		this.type = "epseq";
		this.desc = "Epsilon Equal State";
		
	}
	
	//
//  # EpsilonEqualStatesBuilder.java:
//
//      - you cannot modify obj, use a local variables "double esp=0.0" and modify its value if obj has a key "eps"

//   > DONE

	@Override
	StateComparator createTheInstance(JSONObject obj) {

		double eps = 0;

		if(obj.has("eps")) {
			eps = obj.getDouble("eps");		
		}

		return new EpsilonEqualStates(eps);

	}

}
