package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;
import simulator.misc.Vector2D;
import simulator.model.Body;

public class MassEqualStateBuilder extends Builder<StateComparator> {
	
	public MassEqualStateBuilder() {
		this.type = "masseq";
		this.desc = "Mass Equal State";
	}

	@Override
	StateComparator createTheInstance(JSONObject obj) {
		
		return new MassEqualStates();
		
	}

}
