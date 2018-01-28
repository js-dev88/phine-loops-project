package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.bitbucket.jsdev88.projethaddadsaussier.solutions.Checker;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

/**
 * This class handles the GUI
 * 
 *
 */
public class GUI {

	private JFrame frame;

	/**
	 * 
	 * @param inputFile
	 *            String from IO
	 * @throws IOException
	 *             if there is a problem with the gui
	 */
	public static void startGUI(String inputFile) throws NullPointerException {
		// We have to check that the grid is generated before to launch the GUI
		// construction
		Runnable task = new Runnable() {
			public void run() {

				try {
					Grid grid = Checker.buildGrid(inputFile);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							GUI window;
							window = new GUI(grid);
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
	 * 
	 * @throws IOException
	 */
	public GUI(Grid grid) {

		initialize(grid);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize(Grid grid) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setBounds(100, 100, grid.getWidth() * 70, grid.getHeight() * 70);
		frame.setLayout(new GridLayout(grid.getHeight(), grid.getWidth()));

		// set image on Jlabel
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				JLabel lblcase = new JLabel("");
				Piece p = grid.getPiece(i, j);
				lblcase.setIcon(getImageIcon(p));
				frame.getContentPane().add(lblcase, BorderLayout.CENTER);
				lblcase.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						p.turn();
						lblcase.setIcon(getImageIcon(p));

						// The grid is resolved, play music and display a
						// message
						if (Checker.isSolution(grid)) {

							Random r = new Random();
							if (r.nextInt(2) == 0) {
								EasterEgg.AAA.play();
							} else {
								EasterEgg.FORABDEL.play();
							}
							JOptionPane.showMessageDialog(frame, "Grid is solved !");
							System.exit(0);
						}

					}
				});

			}

		}

	}

	/**
	 * Display the correct image from the piece's type and orientation
	 * 
	 * @param p
	 *            the piece
	 * @return an image icon
	 */
	private ImageIcon getImageIcon(Piece p) {
		ImageIcon img;
		if (p.getType().equals(PieceType.ONECONN)) {
			if (p.getOrientation().equals(Orientation.NORTH)) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/10.png"));

			} else if ((p.getOrientation().equals(Orientation.EAST))) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/11.png"));

			} else if ((p.getOrientation().equals(Orientation.SOUTH))) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/12.png"));

			} else {// WEST
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/13.png"));

			}
		} else if (p.getType().equals(PieceType.BAR)) {
			if (p.getOrientation().equals(Orientation.NORTH)) {// NORTH-OUEST
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/20.png"));

			} else {// EAST-WEST
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/21.png"));

			}
		} else if (p.getType().equals(PieceType.TTYPE)) {
			if (p.getOrientation().equals(Orientation.NORTH)) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/30.png"));

			} else if ((p.getOrientation().equals(Orientation.EAST))) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/31.png"));

			} else if ((p.getOrientation().equals(Orientation.SOUTH))) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/32.png"));

			} else {// WEST
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/33.png"));

			}
		} else if (p.getType().equals(PieceType.LTYPE)) {
			if (p.getOrientation().equals(Orientation.NORTH)) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/50.png"));

			} else if ((p.getOrientation().equals(Orientation.EAST))) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/51.png"));

			} else if ((p.getOrientation().equals(Orientation.SOUTH))) {
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/52.png"));

			} else {// WEST
				img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/53.png"));

			}
		} else if (p.getType().equals(PieceType.FOURCONN)) {
			img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/40.png"));

		} else {// VOID
			img = null;
		}
		return img;
	}

}
