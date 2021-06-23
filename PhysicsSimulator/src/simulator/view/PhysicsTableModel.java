package simulator.view;

import java.util.Iterator;
import javax.swing.table.AbstractTableModel;
import org.json.JSONObject;
import simulator.control.Controller;


//This table model stores internally the content of the table. Use
// getData() to get the content as JSON.
//
public class PhysicsTableModel extends AbstractTableModel {		
	/**
	 * 
	 */


	private static final long serialVersionUID = 1L;

	private String[] _header = { "Key", "Value" , "Description"};
	String[][] _data;
	
	Object test = null;

	PhysicsTableModel() {		
		_data = new String[5][3];
		clear();		
	}

	public void clear() {			

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				_data[i][j] = "";
			}
		}		


		fireTableStructureChanged();
	}

	public void getTableContent(int getLaw, Controller _ctrl) {

		JSONObject tableData = _ctrl.getForceLawsInfo().get(getLaw); //gets the whole JSONObject
		JSONObject data = tableData.getJSONObject("data"); //gets the inner jSONObject -> the one we want		
		Iterator<?>keys = data.keys();
		int counter = 0;

		for(Iterator iterator = data.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();

			_data[counter][0] = key;
			_data[counter][2] = (String) data.get(key);			

			counter++;
		}	
		
		fireTableStructureChanged();		
	}	

	@Override
	public String getColumnName(int column) {
		return _header[column];
	}

	@Override
	public int getRowCount() {
		return _data.length;
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0 || columnIndex == 2) {
			return false;
		}
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return _data[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object o, int rowIndex, int columnIndex) {
		_data[rowIndex][columnIndex] = o.toString();
	}

	// Method getData() returns a String corresponding to a JSON structure
	// with column 1 as keys and column 2 as values.

	// This method return the coIt is important to build it as a string, if
	// we create a corresponding JSONObject and use put(key,value), all values
	// will be added as string. This also means that if users want to add a
	// string value they should add the quotes as well as part of the
	// value (2nd column).
	//
	public String getData() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _data.length; i++) {
			if (!_data[i][0].isEmpty() && !_data[i][1].isEmpty()) {
				s.append('"');
				s.append(_data[i][0]);
				s.append('"');
				s.append(':');
				s.append(_data[i][1]);
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');

		return s.toString();
	}

	
}

