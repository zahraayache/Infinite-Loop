package fr.dauphine.JavaAvance.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;

import javax.swing.*;
import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.Solve.Checker;

/**
 * Graphical user interface : where the user can click on the pieces to change it's orientation
 * 
 *
 */
public class GUI implements ActionListener {
	//variables needed
	private JFrame frame;
	private final int widthOfPiece = 100;
	private final int heightOfPiece = 100;
	JButton[][] places;
	Grid grid;


	/**
	 *This method will start a new Graphic user interface on a new thread
	 * @param inputFile String
	 * @throws NullPointerException
	 */
	public static void startGUI(String inputFile) throws NullPointerException {
		// We have to check that the grid is generated before to launch the GUI
		// We can open several mains (GUI) bcs each GUI is running on a thread
		Runnable task = new Runnable() {
			public void run() {

				try {
					Grid grid = Checker.readGrid(inputFile);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							GUI window = null;
							try {
								window = new GUI(grid);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							window.frame.setVisible(true);
						}
					});
				} catch (IOException e) {
					throw new NullPointerException("Error with input file");
				}

			}
		};
		new Thread(task).start();
	}

	/**
	 * Create the application.
	 * @param grid Grid
	 * @throws MalformedURLException ImageIcon
	 */
	//constructor
	public GUI(Grid grid ) throws MalformedURLException {
		this.grid = grid;
		frame = new JFrame("Infinity Loops");
		initialize(grid);

	}

	/**
	 * This method will fill the grid with all the pieces images in teh right place and orientation
	 * @param grid Grid
	 * @throws MalformedURLException ImageIcon
	 */
	private void initialize(Grid grid) throws MalformedURLException {
		frame.setVisible(true);
		frame.setSize(grid.getWidth() * widthOfPiece,grid.getHeight() * heightOfPiece);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		places = new JButton[grid.getHeight()][grid.getWidth()];
		JPanel panelForButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelForButtons.setLayout(new GridLayout(grid.getHeight(), grid.getWidth()));

		for (int line = 0; line < grid.getHeight(); line++) {
			for (int column = 0; column < grid.getWidth(); column++) {
				Icon icon = new ImageIcon(this.getImageIcon(grid.getPiece(line, column)).getImage().getScaledInstance(widthOfPiece, heightOfPiece, Image.SCALE_SMOOTH));
				JButton temp = new JButton(icon);
				panelForButtons.add(temp);
				//add action Listener on each piece so it can be clickable
				temp.addActionListener(this);
				places[line][column] = temp;

			}
		}
		frame.add(panelForButtons);
		frame.setVisible(true);
	}


	/**
	 * @param piece
	 * @return the image icon of the piece with the right orientation
	 */
	private ImageIcon getImageIcon(Piece p) throws MalformedURLException {
		String image = "";
		switch (p.getType()) {
		case VOID -> {
			image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/background.png";
		}
		case ONECONN -> {
			switch (p.getOrientation()) {
			case NORTH -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/1.png";
			case EAST -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/2.png";
			case SOUTH -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/3.png";
			case WEST -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/4.png";
			}
		}
		case BAR -> {
			switch (p.getOrientation()) {
			case NORTH -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/5.png";
			case EAST -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/6.png";
			}
		}
		case TTYPE -> {
			switch (p.getOrientation()) {
			case NORTH -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/7.png";
			case EAST -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/8.png";
			case SOUTH -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/9.png";
			case WEST -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/10.png";
			}
		}
		case FOURCONN -> {
			if (p.getOrientation() == Orientation.NORTH) {
				image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/11.png";
			}
		}
		case LTYPE -> {
			switch (p.getOrientation()) {
			case NORTH -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/12.png";
			case EAST -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/13.png";
			case SOUTH -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/14.png";
			case WEST -> image = "src/main/resources/fr/dauphine/JavaAvance/icons/io/15.png";
			}
		}
		}
		return new ImageIcon(image);
	}

	/**
	 * listener
	 * this method make the image clickable and then can make the piece turn 90 degrees
	 * @param e action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				if (e.getSource() == places[i][j]) {
					grid.getPiece(i, j).turn();
					try {
						Icon icon = new ImageIcon(this.getImageIcon(grid.getPiece(i, j)).getImage().getScaledInstance(widthOfPiece, heightOfPiece, Image.SCALE_SMOOTH));
						places[i][j].setIcon(icon);
						if (Checker.isSolution(grid)) {
							JOptionPane.showMessageDialog(null, "Success!", "Congratulations on winnig Infinity LOOP", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		frame.repaint();
	}
}
