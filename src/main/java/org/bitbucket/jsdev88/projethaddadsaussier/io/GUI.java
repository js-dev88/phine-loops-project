package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;

public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void startGUI(Grid grid) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI(grid);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(Grid grid) {
		initialize(grid);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Grid grid) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblBackGround = new JLabel("");
		lblBackGround.setIcon(new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/background.png")));
		frame.getContentPane().add(lblBackGround, BorderLayout.CENTER);
		
		lblBackGround.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 0, 0, 0);
		
		/* weightx définit le nombre de cases en abscisse */
		gc.weightx = grid.getWidth();
		
		/* weightx définit le nombre de cases en ordonnée */
		gc.weighty = grid.getHeight();;
		
		
		
		JLabel lblcase;
		/* On ajoute le composant au panel en précisant le GridBagConstraints */
		for(int i =0; i < grid.getWidth(); i++){
			for(int j = 0; j< grid.getHeight(); j++){
				Piece p = grid.getPiece(i, j);
				ImageIcon img;
				if(p.getType().equals(PieceType.ONECONN)){
					if(p.getOrientation().equals(Orientation.NORTH)){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/10.png"));
					}
					else if((p.getOrientation().equals(Orientation.EAST))){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/11.png"));
					}else if((p.getOrientation().equals(Orientation.SOUTH))){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/12.png"));
					}else{//WEST
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/13.png"));
					}
				}
				else if(p.getType().equals(PieceType.BAR)){
					if(p.getOrientation().equals(Orientation.NORTH)){//NORTH-OUEST
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/20.png"));
					}
					else{//EAST-WEST
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/21.png"));
					}
				}
				else if(p.getType().equals(PieceType.TTYPE)){
					if(p.getOrientation().equals(Orientation.NORTH)){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/30.png"));
					}
					else if((p.getOrientation().equals(Orientation.EAST))){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/31.png"));
					}else if((p.getOrientation().equals(Orientation.SOUTH))){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/32.png"));
					}else{//WEST
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/33.png"));
					}
				}
				else if(p.getType().equals(PieceType.LTYPE)){
					if(p.getOrientation().equals(Orientation.NORTH)){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/50.png"));
					}
					else if((p.getOrientation().equals(Orientation.EAST))){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/51.png"));
					}else if((p.getOrientation().equals(Orientation.SOUTH))){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/52.png"));
					}else{//WEST
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/53.png"));
					}
				}
				else if(p.getType().equals(PieceType.FOURCONN)){
						img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/40.png"));
				}
				else{//VOID
					img = new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/background.png"));
				}
				lblcase = new JLabel("");
				lblcase.setIcon(img);
				lblBackGround.add(lblcase, gc);
				gc.gridx = i;
				gc.gridy = j;
			}
			
			
		}
		
		
		
		
	}
	 public class ImagePanel extends JPanel {
	     private Image bgImage;

	     public Image getBackgroundImage() {
	        return this.bgImage;
	     }

	     public void setBackgroundImage(Image image) {
	        this.bgImage = image;
	     }

	     public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         g.drawImage( bgImage, 0, 0, bgImage.getWidth(null), bgImage.getHeight(null), null );
	     }
	 }

}
