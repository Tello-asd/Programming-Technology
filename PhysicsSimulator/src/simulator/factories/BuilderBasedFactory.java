package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T>{
	
	protected List<Builder<T>> bList;
	
	protected List<JSONObject> list;
	
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		
		this.bList = builders;	
		
		this.list = new ArrayList<JSONObject>();
		
		for (Builder<T> builder : bList) {
			list.add(builder.getBuilderInfo());


		}
		
	}
	
//  # BuilderBasedFactory.java:
//
//      TODO - move the loop in getInfo to the constructor, it is always the same list
//
//      - what is this?!?!?! So if it succed to create the object it does it twice, why no simply writing "obj = builder.createInstance(info)" instead of all this? And break if obj is different from null
//
//                  if (builder.createInstance(info) == null) {
//                      obj = null;
//                  }
//                  else {
//                      obj = builder.createInstance(info);
//                      break;
//                  }

//    We changed it

	@Override
	public T createInstance(JSONObject info){

		T obj = null;

		for (Builder<T> builder : bList) {

			obj = builder.createInstance(info);
			if (obj != null) {
				break;
			}

		}


		return obj;


	}

	@Override
	public List<JSONObject> getInfo() {


		return this.list;
	}

}
