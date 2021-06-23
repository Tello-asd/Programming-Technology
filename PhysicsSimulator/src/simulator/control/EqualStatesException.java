package simulator.control;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class EqualStatesException extends Exception{
	
	public EqualStatesException (String msg, JSONObject json1, JSONObject json2) {
		super(msg + json1 + " " + json2);
	}
	
	

}
