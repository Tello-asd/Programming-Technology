package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {

	// ...
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for force laws
	private JLabel _numOfBodies; // for number of bodies

	StatusBar(Controller ctrl) {

		this._currTime = new JLabel();
		this._numOfBodies = new JLabel();
		this._currLaws = new JLabel();


		initGUI();
		ctrl.addObserver(this);
	}


	private void initGUI() { 
		// TODO complete the code to build the tool bar
		this.setLayout( new FlowLayout(FlowLayout.LEFT));
	

		this.add(new JLabel("Time"));
		this.add(_currTime, new JSeparator());

		this.add(new JLabel("Bodies"));
		this.add(_numOfBodies, new JSeparator());

		this.add(new JLabel("Laws"));
		this.add(_currLaws, new JSeparator());

		
	}

	// other private/protected methods
	// ...
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

		this._currTime.setText(String.valueOf(time));
		this._numOfBodies.setText(String.valueOf(bodies.size()));
		this._currLaws.setText(fLawsDesc);


	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

		this._currTime.setText(String.valueOf(0.0));
		this._numOfBodies.setText(String.valueOf(bodies.size()));
		this._currLaws.setText(fLawsDesc);

	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {

		this._numOfBodies.setText(String.valueOf(bodies.size()));

	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		this._currTime.setText(String.valueOf(time));

	}
	@Override
	public void onDeltaTimeChanged(double dt) {

	}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {

		this._currLaws.setText(fLawsDesc);
		

	}


	@Override
	public void onBodyRemoved(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

}
