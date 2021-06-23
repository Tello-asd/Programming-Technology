package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private List<Body> _bodies;
	private String[] labels = { "id", "mass", "position", "velocity", "force"};

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public String getColumnName(int column) {

		return labels[column];
	}

	@Override
	public int getColumnCount() {

		return labels.length;
	}

	@Override
	public int getRowCount() {

		return _bodies.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String str = "";

		Body b = _bodies.get(rowIndex);

		switch (columnIndex) {

		case 0:
			str = b.getState().get("id").toString();
			break;
		case 1:
			str = b.getState().get("m").toString();
			break;
		case 2:
			str = b.getState().get("p").toString();
			break;
		case 3:
			str = b.getState().get("v").toString();
			break;
		case 4:
			str = b.getState().get("f").toString();
			break;

		}

		return str;
	}
	
	public void onCall(List<Body> bodies) {
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {		
		onCall(bodies);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		onCall(bodies);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		onCall(bodies);
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		onCall(bodies);

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
