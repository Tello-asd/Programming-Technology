package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	// ...
	private Controller _ctrl;

	private ControlPanel controlPanel;
	private BodiesTable bodiesTable;
	private Viewer viewer;
	private StatusBar statusBar;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {

		setSize(800, 500);		

		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel secondaryPanel = new JPanel();

		secondaryPanel.setLayout(new BoxLayout(secondaryPanel, BoxLayout.Y_AXIS));

		setContentPane(mainPanel);
		addControlPanel(mainPanel);
		addBodiesTable(secondaryPanel);
		addViewer(secondaryPanel);
		
		mainPanel.add(secondaryPanel);
		
		addStatusBar(mainPanel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



	}
	
	private void addControlPanel(JPanel Panel) {

		this.controlPanel = new ControlPanel(_ctrl);
		Panel.add(this.controlPanel, BorderLayout.PAGE_START);

	}

	private void addBodiesTable(JPanel Panel) {

		this.bodiesTable = new BodiesTable(_ctrl);
		this.bodiesTable.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/4));
		
		Panel.add(this.bodiesTable);

	}
	
	private void addViewer(JPanel Panel) {

		this.viewer = new Viewer(_ctrl);
		this.viewer.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		
		Panel.add(this.viewer);

	}
	
	private void addStatusBar(JPanel Panel) {

		this.statusBar = new StatusBar(_ctrl);
		Panel.add(this.statusBar, BorderLayout.PAGE_END);

	}
	
}
