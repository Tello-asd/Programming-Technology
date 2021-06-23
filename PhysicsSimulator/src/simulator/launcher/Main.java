package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.control.StateComparator;
import simulator.factories.*;
import simulator.model.Body;
import simulator.model.DangerZone;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _stateComparatorDefaultValue = "epseq";
	private final static String _stepDefaultValue = "150";
	private final static String _modeDefaultValue = "batch";

	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static String _inFile = null;
	private static String r = null;
	private static JSONObject _forceLawsInfo = null;
	private static JSONObject _stateComparatorInfo = null;
	private static String _outFile = null;
	private static String _expectedOutput = null;
	private static String _inSteps = null;
	private static String _mode = null;
	//private static Double _ineps = null; //TODO
	private static OutputStream output = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<ForceLaws> _forceLawsFactory;
	private static Factory<StateComparator> _stateComparatorFactory;

	private static void init() {

		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLosingBodyBuilder());
		bodyBuilders.add(new RocketBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);

		ArrayList<Builder<ForceLaws>> forceBuilders = new ArrayList<>();
		forceBuilders.add(new NewtonUniversalGravitationBuilder());
		forceBuilders.add(new MovingTowardsFixedPointBuilder());
		forceBuilders.add(new NoForceBuilder());
		_forceLawsFactory = new BuilderBasedFactory<ForceLaws>(forceBuilders);

		ArrayList<Builder<StateComparator>> comparatorBuilder = new ArrayList<>();
		comparatorBuilder.add(new MassEqualStateBuilder());
		comparatorBuilder.add(new EpsilonEqualStateBuilder());
		_stateComparatorFactory = new BuilderBasedFactory<StateComparator>(comparatorBuilder);
	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);

			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			// TODO add support of -o, -eo, and -s (define corresponding parse methods)

			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			parseStateComparatorOption(line);
			parseOutputOption(line);
			parseExpectedOutputOption(line);
			parseStepsOption(line);
			parseModeOption(line);
			parseDangerZoneOption(line);
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}



	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		// TODO add support for -o, -eo, and -s (add corresponding information to
		// cmdLineOptions)

		// -o
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where output is written.\n"
				+ "Default value: the standard output.").build());

		// -o
		cmdLineOptions.addOption(Option.builder("dzone").longOpt("dzone").hasArg().desc("Danger Zone.\n"
				+ "Default value: the standard output.").build());


		// -eo
		cmdLineOptions.addOption(Option.builder("eo").longOpt("expected-output").hasArg().desc("The expected output file. If not provided\n"
				+ "no comparison is applied").build());


		// -s
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("An integer representing the number of\n"
				+ "simulation steps. Default value: " + _stepDefaultValue + ".").build());


		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());

		// gravity laws
		cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
				.desc("State comparator to be used when comparing states. Possible values: "
						+ factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
						+ _stateComparatorDefaultValue + "'.")
				.build());

		// Batch mode
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg()
				.desc("Execution Mode. Possible values: ’batch’ "
						+ "(Batch mode), ’gui’ (Graphical User "
						+ "Interface mode). " + " Default value: " + _modeDefaultValue + "'.")
				.build());

		return cmdLineOptions;
	}

	private static void parseDangerZoneOption(CommandLine line) throws ParseException {
		if (line.hasOption("dzone")) {
			r = line.getOptionValue("dzone");
			//double asd = Double.parseDouble(r);

			if (r == null) {
				throw new ParseException("In batch mode an input file of bodies is required");
			}	
		}
	}

	public static String factoryPossibleValues(Factory<?> factory) {
		if (factory == null)
			return "No values found (the factory is null)";

		String s = "";

		for (JSONObject fe : factory.getInfo()) {
			if (s.length() > 0) {
				s = s + ", ";
			}
			s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
		}

		s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
		return s;
	}

	private static void parseModeOption(CommandLine line) {
		if (line.hasOption("m")) {

			_mode = line.getOptionValue("m");		

		}

		else {
			_mode = _modeDefaultValue;
		}

	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseOutputOption(CommandLine line)  {
		if (line.hasOption("o")) {

			_outFile = line.getOptionValue("o");					
		}
	}


	private static void parseExpectedOutputOption(CommandLine line) throws ParseException {
		if (line.hasOption("eo")) {
			_expectedOutput = line.getOptionValue("eo");			
		}
	}


	private static void parseStepsOption(CommandLine line) {
		if (line.hasOption("s")) {
			_inSteps = line.getOptionValue("s");

			if (_inSteps == null) {
				_inSteps = _stepDefaultValue;
			}		

		}

		else {
			_inSteps = _stepDefaultValue;
		}
	}


	private static void parseInFileOption(CommandLine line) throws ParseException {
		if (line.hasOption("i")) {
			_inFile = line.getOptionValue("i");
			if (_inFile == null) {
				throw new ParseException("In batch mode an input file of bodies is required");
			}
		}
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		for (JSONObject fe : factory.getInfo()) {
			if (type.equals(fe.getString("type"))) {
				found = true;
				break;
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	private static void parseStateComparatorOption(CommandLine line) throws ParseException {
		String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
		_stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
		if (_stateComparatorInfo == null) {
			throw new ParseException("Invalid state comparator: " + scmp);
		}
	}

	//  # Main.java:
	//
	//      - you already check that it has an option 'o', so it cannot be null
	//
	//      - move this to the method that parses option 's'
	//
	//              if (_inSteps == null) {
	//
	//                  _inSteps = _stepDefaultValue;
	//
	//              }
	//
	//      - instead of calling the controller twice, remove the else-branch and move the call to run after the if 
	//
	//              if(_expectedOutput != null) {
	//                   expOutput = new FileInputStream(_expectedOutput);
	//                  c.run(Integer.parseInt(_inSteps), output, expOutput, cmp);
	//
	//              }
	//              else {
	//                  c.run(Integer.parseInt(_inSteps), output, expOutput, cmp);
	//              }

	//   Added variable private static OutputStream output = null;
	// option 'o' changed.
	// 's' option changed.
	// not calling the controller twice anymore.

	private static void startBatchMode() throws Exception {		
		ForceLaws l = _forceLawsFactory.createInstance(_forceLawsInfo);
		PhysicsSimulator ps = new PhysicsSimulator(l, _dtime);
		Controller c = new Controller(ps, _bodyFactory, _forceLawsFactory);
		InputStream input = new FileInputStream(_inFile); 
		StateComparator cmp = _stateComparatorFactory.createInstance(_stateComparatorInfo);

		if (_outFile == null) {
			output = System.out;
		}
		else {
			output = new FileOutputStream(_outFile);
		}

		//DangerZone dz = new DangerZone(c, Double.parseDouble(r));
		c.loadBodies(input);


		InputStream expOutput = null;

		if(_expectedOutput != null) {
			expOutput = new FileInputStream(_expectedOutput);			

		}

		c.run(Integer.parseInt(_inSteps), output, expOutput, cmp);
		//System.out.println(dz);

	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if(Objects.equals("batch", _mode))
			startBatchMode();
		else
			startGUIMode();
	}

	private static void startGUIMode() throws Exception {

		Controller c;
		// create and connect components, then start the simulator
		if(_forceLawsInfo != null) {
			ForceLaws l = _forceLawsFactory.createInstance(_forceLawsInfo);
			PhysicsSimulator ps = new PhysicsSimulator(l, _dtime);
			c = new Controller(ps, _bodyFactory, _forceLawsFactory);
		}
		else {
			ForceLaws l = null;
			PhysicsSimulator ps = new PhysicsSimulator(l, _dtime);
			c = new Controller(ps, _bodyFactory, _forceLawsFactory);	
		}

		if (_inFile != null) {
			InputStream input = new FileInputStream(_inFile);
			c.loadBodies(input);
		}

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new MainWindow(c);
			}
		});

	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}