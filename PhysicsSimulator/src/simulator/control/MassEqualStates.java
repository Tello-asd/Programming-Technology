package simulator.control;

import org.json.JSONObject;

public class MassEqualStates  implements StateComparator{
	
	public MassEqualStates() {
		
	}
	
//  # MassEqualStates.java:
//
//      - you are comparing strings using ==

//   We changed the "==" for the equals() for comparing strings.
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {

		boolean MassEql = false;

		if(s1.getDouble("time") == (s2.getDouble("time"))) {

			for(int i = 0; i <= s1.length(); i++) {
				if(s1.getJSONArray("id").get(i).equals(s2.getJSONArray("id").get(i))
						&& s1.getJSONArray("mass").get(i).equals(s2.getJSONArray("mass").get(i)) ) {
					
					MassEql = true;
				}
				
				else {
					MassEql = false;
					break;
				}

			}
		}

		return MassEql;

	}

}
