package org.bitbucket.jsdev88.projethaddadsaussier.main;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.bitbucket.jsdev88.projethaddadsaussier.io.GUI;
import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.solutions.Checker;
import org.bitbucket.jsdev88.projethaddadsaussier.solutions.Generator;
import org.bitbucket.jsdev88.projethaddadsaussier.solutions.Solver;

/**
 * Parser of the program
 *
 */
public class Main {
	private static String inputFile = null;
	private static String outputFile = null;
	private static Integer width = -1;
	private static Integer height = -1;
	private static Integer maxcc = -1;

	public static void main(String[] args) {
		boolean gui = false;
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;

		options.addOption("g", "generate ", true, "Generate a grid of size height x width.");
		options.addOption("c", "check", true, "Check whether the grid in <arg> is solved.");

		options.addOption("s", "solve", true, "Solve the grid stored in <arg>.");
		options.addOption("o", "output", true,
				"Store the generated or solved grid in <arg>. (Use only with --generate and --solve.)");
		options.addOption("t", "threads", true, "Maximum number of solver threads. (Use only with --solve.)");
		options.addOption("x", "nbcc", true, "Maximum number of connected components. (Use only with --generate.)");
		options.addOption("i", "interface", true, "Launching the interface of the <arg> grid");
		options.addOption("h", "help", false, "Display this help");

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Error: invalid command line format.");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("phineloopgen", options);
			System.exit(1);
		}

		try {
			if (cmd.hasOption("g")) { // call the generator
				System.out.println("Running phineloop generator.");
				String[] gridformat = cmd.getOptionValue("g").split("x");
				width = Integer.parseInt(gridformat[0]);
				height = Integer.parseInt(gridformat[1]);
				if (width < 0 || height < 0)
					throw new ParseException("Negatives arguments are forbidden.");
				if (!cmd.hasOption("o"))
					throw new ParseException("Missing mandatory --output argument.");
				outputFile = cmd.getOptionValue("o");

				if (cmd.hasOption("x")) {// call the nbcc option
					String nbcc = cmd.getOptionValue("x");
					if (Integer.valueOf(nbcc) > Math.round((width * height - 1 / 2))) {
						System.err.println("Maximum of connected component is limited to height * width /2");
						HelpFormatter formatter = new HelpFormatter();
						formatter.printHelp("phineloopgen", options);
						System.exit(1);
					} else {
						try {
							Generator.generateLevel(outputFile, new Grid(width, height, Integer.valueOf(nbcc)));
						} catch (IOException e) {
							System.err.println("Erreur during generation");
						}
					}
				} else {
					try {
						Generator.generateLevel(outputFile, new Grid(width, height, maxcc));
					} catch (IOException e) {
						System.err.println("Error during generation");
					}
				}

			} else if (cmd.hasOption("s")) {// call the solver
				System.out.println("Running phineloop solver.");
				inputFile = cmd.getOptionValue("s");
				if (!cmd.hasOption("o"))
					throw new ParseException("Missing mandatory --output argument.");
				outputFile = cmd.getOptionValue("o");
				boolean solved = false;

				try {
					solved = Solver.solveGrid(inputFile, outputFile, "0");
				} catch (IOException e) {
					System.err.println("Check files' name");
				}
				System.out.println("SOLVED: " + solved);
			} else if (cmd.hasOption("c")) {// call the checker
				System.out.println("Running phineloop checker.");
				inputFile = cmd.getOptionValue("c");
				boolean solved = false;

				try {
					solved = Checker.isSolution(inputFile);
				} catch (IOException e) {
					solved = false;
				}
				System.out.println("SOLVED: " + solved);
			} else if (cmd.hasOption("i")) {// call the interface
				System.out.println("Launching the interface...");
				inputFile = cmd.getOptionValue("i");
				System.out.println(inputFile);
				gui = true;
				try {

					GUI.startGUI(inputFile);
				} catch (Exception e) {
					System.err.println("Error during GUI launching");
					e.printStackTrace();
				}

			} else {
				throw new ParseException(
						"You must specify at least one of the following options: -generate -check -solve ");
			}
		} catch (ParseException e) {
			System.err.println("Error parsing commandline : " + e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("phineloopgen", options);
			System.exit(1); // exit with error
		}
		if (!gui)// if there is a gui this line messes up the all thing
			System.exit(0); // exit with success
	}
}
