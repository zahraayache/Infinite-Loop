package fr.dauphine.JavaAvance.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import fr.dauphine.JavaAvance.GUI.GUI;
import fr.dauphine.JavaAvance.GUI.Grid;
import fr.dauphine.JavaAvance.Solve.Checker;
import fr.dauphine.JavaAvance.Solve.Generator;
import fr.dauphine.JavaAvance.Solve.Solver;

import javax.swing.*;

/**
 * 
 * @author Zahra , Maria, Katia
 *
 */
public class Main {
	private static String inputFile = null;
	private static String outputFile = null;
	private static Integer width = -1;
	private static Integer height = -1;


	public static void main(String[] args) {
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;

		options.addOption("g", "generate ", true, "Generate a grid of size height x width.");
		options.addOption("c", "check", true, "Check whether the grid in <arg> is solved.");
		options.addOption("s", "solve", true, "Solve the grid stored in <arg>.");   
		options.addOption("o", "output", true, "Store the generated or solved grid in <arg>. (Use only with --generate and --solve.)");
		options.addOption("t", "threads", true, "Maximum number of solver threads. (Use only with --solve.)");
		options.addOption("x", "nbcc", true, "Maximum number of connected components. (Use only with --generate.)");
		options.addOption("h", "help", false, "Display this help");

		//get the arguments from the cmd and check them
		try {
			cmd = parser.parse( options, args);         
		} catch (ParseException e) {
			System.err.println("Error: invalid command line format.");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "phineloopgen", options );
			System.exit(1);
		}

		try{    
			if( cmd.hasOption( "g" ) ) { //if the argument was "g" we have to create a grid
				System.out.println("Running phineloop generator.");
				String[] gridformat = cmd.getOptionValue( "g" ).split("x");

				//the width and height are entered as the number of column and lines you want to see , example: 3x3 = 3 columns and 3 lines
				width = Integer.parseInt(gridformat[0]); //get the width from the first argument
				height = Integer.parseInt(gridformat[1]);  //get the height from the first argument
				if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");
				outputFile = cmd.getOptionValue( "o" );



				Grid grid1 = Generator.generateLevel("./LoopLevels/" + outputFile, width, height, 0);
				//see the grid throw the interface
				GUI gui1 = new GUI(grid1);

				boolean solved = false;
				//always check if the grid is solved 
				while (!solved) {
					solved = Checker.isSolution(grid1);
				}
				Thread.sleep(10000); // wait 3 seconds before closing the interface!

			}
			else if( cmd.hasOption( "s" ) ) {
				System.out.println("Running phineloop solver.");
				inputFile = cmd.getOptionValue( "s" );
				if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");      
				outputFile = cmd.getOptionValue( "o" );


				// load grid from inputFile, solve it and store result to outputFile
				Grid grid = Checker.readGrid("./LoopLevels/" + inputFile);
				Solver solver = new Solver();
				solver.solveGrid(grid);         
			}

			else if( cmd.hasOption( "c" )) {
				System.out.println("Running phineloop checker.");
				inputFile = cmd.getOptionValue( "c" );

				// load grid from inputFile and check if it is solved
				Grid grid = Checker.readGrid("./LoopLevels/" + inputFile);
				if (Checker.isSolution(grid)) {
					System.out.println("The grid sent is solved!"); 
				}else {
					System.out.println("The grid sent is not solved!"); 
				}


			}
			else if( cmd.hasOption( "h" )) {
				JOptionPane.showMessageDialog(null, "Here to help! go put solve in the args to solve the level", "A la rescousse", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				throw new ParseException("Please specify one the options : -generate -check -solve ");           
			}
		} catch (ParseException e) {
			System.err.println("Error parsing commandline : " + e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "phineloopgen", options );         
			System.exit(1); // exit with error      
		} catch (FileNotFoundException | MalformedURLException | InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0); // exit with success
	}

}
