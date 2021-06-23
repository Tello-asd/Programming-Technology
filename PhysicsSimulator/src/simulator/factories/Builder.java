package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	
	protected String type;
	protected String desc;
	// TODO DATA TYPE?
	
	public Builder() {
		
	}	

	public T createInstance(JSONObject info) {


        T obj = null; //creates an object of type T
        if (this.type.equals(info.getString("type"))) {
            obj = createTheInstance(info.getJSONObject("data"));
            return obj;

        } else
            return obj;
    }
	
	 public JSONObject getBuilderInfo() {

	        JSONObject json = new JSONObject();

	        json.put("type", this.type);
	        json.put("desc", this.desc);
	        json.put("data", this.createData());
	       

	        return json;

	    }
	
	
	  public JSONObject createData() {
	        return new JSONObject();

	    }
	
	abstract T createTheInstance(JSONObject obj);
}
