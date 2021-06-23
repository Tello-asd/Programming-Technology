package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {

	private Controller _ctrl;
	private boolean _stopped; 

	private JFileChooser fc;
	private JToolBar tb;

	private JButton open_button;
	private JButton remove_button;
	private JButton physics_button;
	private JButton run_button;
	private JButton stop_button;
	private JButton exit_button;

	private JLabel deltaTime_label;
	private JLabel steps_label;

	private JTextField deltatime_textField;
	private JSpinner steps_spinner;
	private PhysicsTable physicstable;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;

		initGUI();

		_ctrl.addObserver(this);
	}

	private void initGUI() {
		// TODO build the tool bar by adding buttons, etc.		
		setLayout(new BorderLayout());
		tb = new JToolBar();
		this.add(tb, BorderLayout.PAGE_START);		



		// Load
		createOpenButton();
		tb.add(open_button);

		// GravityLaw		
		createPhysicsButton();
		tb.add(physics_button);

		// Run
		createRunButton();
		tb.add(run_button);


		// Stop
		createStopButton();
		tb.add(stop_button);

		removeButton();
		tb.add(remove_button);


		// steps
		steps_label = new JLabel();
		steps_label.setText(" Steps: ");
		tb.add(steps_label);

		steps_spinner = new JSpinner();
		steps_spinner.setValue(10000);
		steps_spinner.setMinimumSize(new Dimension(80, 30));
		steps_spinner.setMaximumSize(new Dimension(200, 30));
		steps_spinner.setPreferredSize(new Dimension(80, 30));
		tb.add(steps_spinner);


		// delta time
		deltaTime_label = new JLabel();
		deltaTime_label.setText(" Delta time: ");
		tb.add(deltaTime_label);


		deltatime_textField = new JTextField();
		deltatime_textField.setText("2500");
		deltatime_textField.setMinimumSize(new Dimension(80, 30));
		deltatime_textField.setMaximumSize(new Dimension(200, 30));
		deltatime_textField.setPreferredSize(new Dimension(80, 30)); 
		tb.add(deltatime_textField);


		// Exit
		createExitButton();
		tb.add(exit_button);


	}

	private void removeButton() {	
	
		remove_button = new JButton();		
		remove_button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				Body res = (Body) JOptionPane.showInputDialog(null, "Choose a Body", "Body Chooser", JOptionPane.QUESTION_MESSAGE, null, _ctrl.retrieveBodies().toArray(), _ctrl.retrieveBodies().get(0));
				_ctrl.removeBody(res.getId());

			}


		});

	}

	private void createExitButton() {

		exit_button = new JButton();
		exit_button.setAlignmentX(RIGHT_ALIGNMENT);
		exit_button.setToolTipText("Exits the simulator");
		exit_button.setIcon(new ImageIcon("resources/icons/exit.png"));

		exit_button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int opt = JOptionPane.showConfirmDialog(null, "Exit Simulation?", "Warning", JOptionPane.YES_NO_OPTION);
				if (opt == JOptionPane.YES_OPTION) 
					System.exit(0);

			}

		});




	}

	private void createStopButton() {

		stop_button = new JButton();
		stop_button.setToolTipText("Stops the simulator");
		stop_button.setIcon(new ImageIcon("resources/icons/stop.png"));
		stop_button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				_stopped = true;

			}

		});


	}

	private void createRunButton() {

		run_button = new JButton();
		run_button.setToolTipText("Runs the simulator");
		run_button.setIcon(new ImageIcon("resources/icons/run.png"));
		run_button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				double deltaTime = 0.0;

				open_button.setEnabled(false);
				physics_button.setEnabled(false);
				run_button.setEnabled(false);
				exit_button.setEnabled(false);
				steps_spinner.setEnabled(false);
				deltatime_textField.setEnabled(false);

				deltaTime = Float.parseFloat(deltatime_textField.getText());
				_stopped = false;
				_ctrl.setDeltaTime(deltaTime);
				run_sim((int) steps_spinner.getValue());

			}

		});


	}

	private void createOpenButton() {

		fc = new JFileChooser();
		fc.setDialogTitle("file to load the bodies");

		open_button = new JButton();
		open_button.setIcon(new ImageIcon("resources/icons/open.png"));
		open_button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				fc.showOpenDialog(fc);                

				File file = fc.getSelectedFile();
				InputStream inputStream;

				try {
					inputStream = new FileInputStream(file);
					_ctrl.reset();
					_ctrl.loadBodies(inputStream);
				} catch (FileNotFoundException e1) {					
					e1.printStackTrace();
				}

			}

		});


	}

	private void createPhysicsButton() {	

		physics_button = new JButton();
		physics_button.setIcon(new ImageIcon("resources/icons/physics.png"));
		physics_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {			

				physicstable = new PhysicsTable((Frame) SwingUtilities.getWindowAncestor(ControlPanel.this), _ctrl);							
				physicstable.open();					

			}
		});





	}

	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);

				// TODO show the error in a dialog box
				// TODO enable all buttons
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error");
				this.open_button.setEnabled(true);
				this.physics_button.setEnabled(true);
				this.run_button.setEnabled(true);
				this.exit_button.setEnabled(true);
				this.steps_spinner.setEnabled(true);
				this.deltatime_textField.setEnabled(true);
				this._stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		} else {
			_stopped = true;
			this.open_button.setEnabled(true);
			this.physics_button.setEnabled(true);
			run_button.setEnabled(true);
			exit_button.setEnabled(true);
			steps_spinner.setEnabled(true);
			deltatime_textField.setEnabled(true);
		}
	}

	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

		this.deltatime_textField.setText(String.valueOf(dt));  

	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {      

		this.deltatime_textField.setText(String.valueOf(dt));
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {


	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		this.deltatime_textField.setText(String.valueOf(dt)); 

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {

	}

	@Override
	public void onBodyRemoved(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub

	}

}