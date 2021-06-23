package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{

	protected double eps;

	public EpsilonEqualStates (double eps) {
		this.eps = eps;
	}

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {//TODO
		boolean epsEqual = false;


		if(s1.getDouble("time") == s2.getDouble("time")) {
			
			JSONArray a1 = s1.getJSONArray("bodies");
			JSONArray a2 = s2.getJSONArray("bodies");


			for(int i = 0; i < s1.length(); i++) {	
				
				if(((a1.getJSONObject(i).get("id")).equals(a2.getJSONObject(i).get("id")))
					
						&& (Math.abs(new Vector2D(a1.getJSONObject(i).getJSONArray("p").getDouble(0), a1.getJSONObject(i).getJSONArray("p").getDouble(1))
								.distanceTo(new Vector2D( a2.getJSONObject(i).getJSONArray("p").getDouble(0), a2.getJSONObject(i).getJSONArray("p").getDouble(1))))  <= this.eps)
						
						&& (Math.abs(new Vector2D(a1.getJSONObject(i).getJSONArray("v").getDouble(0), a1.getJSONObject(i).getJSONArray("v").getDouble(1))
								.distanceTo(new Vector2D( a2.getJSONObject(i).getJSONArray("v").getDouble(0), a2.getJSONObject(i).getJSONArray("v").getDouble(1))))  <= this.eps)
						
						&& (Math.abs(new Vector2D(a1.getJSONObject(i).getJSONArray("f").getDouble(0), a1.getJSONObject(i).getJSONArray("f").getDouble(1))
								.distanceTo(new Vector2D( a2.getJSONObject(i).getJSONArray("f").getDouble(0), a2.getJSONObject(i).getJSONArray("f").getDouble(1))))  <= this.eps)
						
						&& (Math.abs(a1.getJSONObject(i).getDouble("m") - a2.getJSONObject(i).getDouble("m")) <= eps)) {

					epsEqual = true;

				}

				else {
					epsEqual = false;
					break;
				}
			}
		}
		return epsEqual;
	}

}
