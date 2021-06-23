package simulator.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.control.Controller;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PhysicsTable extends JDialog {

	private static final long serialVersionUID = 1L;

	private int _status;
	private PhysicsTableModel _dataTableModel;
	private List<String> listString;
	private Controller _ctrl;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;	

	public PhysicsTable(Frame frame, Controller ctrl) {

		super(frame, true);		
		_ctrl = ctrl;
		comboBox = new JComboBox();
		initGUI();
	}

	@SuppressWarnings("unchecked")
	private void initGUI() {

		_status = 0;

		setTitle("Build JSON from Table");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		// help
		JLabel help = new JLabel("<html><p>Select a force law and provide values for the parameters in the Value column (default values are used for parameters with no value).</p></html>");

		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// data table
		_dataTableModel = new PhysicsTableModel();
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resized rows to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		listString = new ArrayList<String>();

		for (JSONObject obj : _ctrl.getForceLawsInfo()) {
			String aux = obj.getString("desc");
			listString.add(aux);

		}

		comboBox.setAlignmentX(CENTER_ALIGNMENT);
		comboBox.setModel(new DefaultComboBoxModel<String>(listString.toArray(new String[0])));		
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_dataTableModel.clear();
				int getLaw =  comboBox.getSelectedIndex();
				_dataTableModel.getTableContent(getLaw, _ctrl);				


			}
		});

		comboBox.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(comboBox);

		// bottons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(buttonsPanel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				PhysicsTable.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				
				PhysicsTable.this.setVisible(false);
				JSONObject info = new JSONObject();				

				for ( int i = 0; i < _ctrl.getForceLawsInfo().size(); i++) {

					if(_ctrl.getForceLawsInfo().get(i).get("desc").equals(comboBox.getSelectedItem())) {

						info.put("type", _ctrl.getForceLawsInfo().get(i).get("type"));
						info.put("data", new JSONObject(_dataTableModel.getData()));

					}				

				}

				try {
					_ctrl.setForcesLaws(info);
				} catch(Exception x)
				{
					x.printStackTrace();
				}

			}
		});

		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(700, 400));

		pack();
		setResizable(false); // change to 'true' if you want to allow resizing
		setVisible(false); // we will show it only whe open is called
	}

	public int open() {

		if (getParent() != null)
			setLocation(//
					getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, //
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();		
		setVisible(true);
		return _status;
	}

	public String getJSON() {
		return _dataTableModel.getData();

	}	
}
