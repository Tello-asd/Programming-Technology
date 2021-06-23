package simulator.control;

import java.io.*;
import java.util.List;

import org.json.*;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {

	private PhysicsSimulator phsim;
	private  Factory<Body> fBody;
	protected Factory<ForceLaws> forceLaws;

	public Controller(PhysicsSimulator simulator, Factory<Body> body, Factory<ForceLaws> forceLaws) {
		this.phsim = simulator;
		this.fBody = body;
		this.forceLaws = forceLaws;
	}

	public void run(int n) {

		for (int i = 0; i < n; i++) {

			this.phsim.advance();
			new OutputStream() {
				@Override
				public void write(int b) throws IOException {
				};
			};
		}
	}

	public void run (int n, OutputStream out, InputStream expOut, StateComparator cmp) throws EqualStatesException  {

		JSONArray aux1 = null;
		
		if(expOut != null) {			 

			JSONObject jsonInput = new JSONObject(new JSONTokener(expOut));				
			aux1 = jsonInput.getJSONArray("states");	
			
			//System.out.print(aux1.getJSONObject(i));
			//System.out.print(this.phsim.getState());

		}	

		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
	    p.println(phsim.getState());
		for(int i = 0; i < n; ++i) { //it runs the simulator n steps	
			
			if(aux1 != null && !cmp.equal(aux1.getJSONObject(i), this.phsim.getState())) {
				throw new EqualStatesException("Not equal states in controller" , aux1.getJSONObject(i), this.phsim.getState());
			}
			this.phsim.advance();	
			
			p.println(",");
			p.println(this.phsim.toString()); // prints to the different states to out 
			

		}	
		
		p.println("]");
		p.println("}");

		p.close();

	}
	
	public List<Body> retrieveBodies() {
		return phsim.getBodyList();
	}
	
	public void reset() {
		
		phsim.reset();
	}

	public void setDeltaTime(double dt) {
		
		phsim.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		phsim.addObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo() {
	
		
		return forceLaws.getInfo();
		
	}
	
	public void removeBody(String id) {
		phsim.removeBody(id);
	}
	
	public void setForcesLaws(JSONObject info) {
				
		phsim.setForceLaws(this.forceLaws.createInstance(info));		
		
	}
	
	public void loadBodies(InputStream in) {

		JSONObject jsonInput = new JSONObject(new JSONTokener(in)); // converts the input JSON into a JSONObject
		JSONArray jsonArr = jsonInput.getJSONArray("bodies"); //we get the bodies from the input into an array

		for(int b = 0; b < jsonArr.length(); b++) //creates a corresponding body b using the bodies factory, and adds it to the simulator
			this.phsim.addBody(this.fBody.createInstance((JSONObject) jsonArr.get(b)));

	}
}
