package simulator.model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.factories.Factory;

public class PhysicsSimulator implements Observable<SimulatorObserver>{

	protected ForceLaws FLaws;
	protected Double deltaTime, currentTime;
	protected List<Body> bodyList;
	protected List<SimulatorObserver> observerList;

	public PhysicsSimulator(ForceLaws laws, double deltaTime) throws IllegalArgumentException {

		if(laws == null) {
			throw new IllegalArgumentException();
		}

		else if(deltaTime < 0) {
			throw new IllegalArgumentException();
		}

		this.FLaws = laws;
		this.deltaTime = deltaTime;
		this.currentTime = 0.0;
		this.bodyList = new ArrayList<Body>();
		this.observerList = new ArrayList<SimulatorObserver>();
	}	

	public void advance() {

		for(Body bi : this.bodyList) 
			bi.resetForce();


		this.FLaws.apply(bodyList);

		for(Body bi : this.bodyList) 
			bi.move(this.deltaTime);


		this.currentTime += this.deltaTime;		
		
		for (SimulatorObserver o : observerList) {
			o.onAdvance(bodyList, currentTime);
			
		}

	}
	
	public void addObserver(SimulatorObserver o) {
		
		if(this.observerList.contains(o)) {
			throw new IllegalArgumentException("o is already in the list");
		}	
		this.observerList.add(o);
		o.onRegister(bodyList, currentTime, deltaTime, FLaws.toString());
		
	}
	
//  # PhysicsSimulator.java:
//
//      - in addBody you use contains but you have not implemented equals in Body.java

//      We changed the equals() to a for with the right conditions.

	public void addBody (Body bi) throws IllegalArgumentException{

		if(this.bodyList.contains(bi)) {
			throw new IllegalArgumentException("Same id in body");
		}	

		this.bodyList.add(bi);
		for (SimulatorObserver o : observerList) {
			o.onBodyAdded(bodyList, bi);
			
		}
	}
	
	public void removeBody (String id) throws IllegalArgumentException{
		
		boolean found = false;
		
		for(Body bi : bodyList) {
			if(bi.getId().equals(id)) {
				this.bodyList.remove(bi);
				found = true;
				
				for(SimulatorObserver o : observerList)
				{
					o.onBodyRemoved(bodyList, bi);
				}
			}			
		}
		
		if(!found) {
			throw new IllegalArgumentException("Same id in body");
		}		
		
	}
	
	public void reset() {
		
		this.bodyList.clear();
		this.currentTime = 0.0;
		
		
		for (SimulatorObserver o : observerList) {
			o.onReset(bodyList, currentTime, deltaTime, FLaws.toString());
			
		}
		
		
		
	}

	
	public void setDeltaTime(double deltaTime) throws IllegalArgumentException{
		
		this.deltaTime = deltaTime;
		
		if(this.deltaTime == null) {
			throw new IllegalArgumentException("Not valid deltaTime");
		}
		
		for (SimulatorObserver o : observerList) {
			o.onDeltaTimeChanged(this.deltaTime);
			
		}
		
	}
	
	
	public void setForceLaws(ForceLaws laws) throws IllegalArgumentException{
		
		this.FLaws = laws;
		
		if(this.FLaws == null) {
			throw new IllegalArgumentException("Not valid Force Law");
		}
		
		for (SimulatorObserver o : observerList) {
			o.onForceLawsChanged(this.FLaws.toString());
			
		}
		
	}

	public JSONObject getState() {

		JSONObject json = new JSONObject();

		json.put("time", this.currentTime);

		JSONArray arr = new JSONArray();
		for(Body bi : this.bodyList) {
			arr.put(bi.getState());
		}
		json.put("bodies", arr);

		return json;

	}

	public String toString() {
		return getState().toString();

	}

	public List<Body> getBodyList() {
		return bodyList;
	}

	public void setBodyList(List<Body> bodyList) {
		this.bodyList = bodyList;
	}
	
	

}
