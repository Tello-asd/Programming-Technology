package simulator.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class DangerZone implements SimulatorObserver {
    //Atributes:
    double r;
    Map<String, Integer> triggers;
    Map<String, Boolean> states; // false > out , true in
    
    //Constructor:
    public DangerZone(Controller ctrl, double r){
    	ctrl.addObserver(this);
        this.r = r;
    }

    public String getMapStr() {
        String output = "";
        for (String str : triggers.keySet()) {
            output += str + " -> " + triggers.get(str) + "\n";
        }
        
        return output;
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        triggers = new HashMap<>();
        states = new HashMap<>();
        updateAllDZ(bodies);
    }
    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        triggers.clear();
        updateAllDZ(bodies);
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        updateDZ(b);
    }
    
    @Override
    public void onAdvance(List<Body> bodies, double time) {
        updateAllDZ(bodies);
    }
    
    public void updateDZ(Body b) {
        if (!triggers.containsKey(b.getId()))
        {
            states.put(b.getId(), false);
            triggers.put(b.getId(), 0);
        }

        if (b.getPosition().magnitude() < r) {
            if (!states.get(b.getId())) {   //If was outside and is now inside
                triggers.put(b.getId(), triggers.get(b.getId())+1);
            }
            states.put(b.getId(), true);   //Is now inside 
        }
        else {
            states.put(b.getId(), false);   //Is now outside
        }
    }
    
    public void updateAllDZ(List<Body> bodies) {
        for (Body b : bodies) {
            updateDZ(b);
        }       
    }

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyRemoved(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}
}