package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class NoForce implements ForceLaws{

	@Override
	public void apply(List<Body> bs) {
		
		//LEAVE IT EMPTY
	}
	
	public String toString() {
		return "No Force";
	}
	

}
