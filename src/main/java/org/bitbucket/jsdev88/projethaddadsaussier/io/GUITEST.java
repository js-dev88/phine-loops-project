package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

public class GUITEST {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void startGUITEST(Grid grid) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITEST window = new GUITEST(grid);
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
	public GUITEST(Grid grid) {
		initialize(grid);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Grid grid) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100,100,grid.getWidth()*70,grid.getHeight()*70);
		frame.setLayout(new GridLayout(grid.getHeight(),grid.getWidth()));
		
	
	
		
		
		/* On ajoute le composant au panel en précisant le GridBagConstraints */
		for(int i =0; i < grid.getHeight(); i++){
			for(int j = 0; j< grid.getWidth(); j++){
				JLabel lblcase = new JLabel("");
				Piece p = grid.getPiece(i, j);
				ImageIcon img;
				if(p.getType().equals(PieceType.ONECONN)){
					if(p.getOrientation().equals(Orientation.NORTH)){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/10.png"));
						
					}
					else if((p.getOrientation().equals(Orientation.EAST))){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/11.png"));
					
					}else if((p.getOrientation().equals(Orientation.SOUTH))){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/12.png"));
					
					}else{//WEST
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/13.png"));
						
					}
				}
				else if(p.getType().equals(PieceType.BAR)){
					if(p.getOrientation().equals(Orientation.NORTH)){//NORTH-OUEST
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/20.png"));
						
					}
					else{//EAST-WEST
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/21.png"));
						
					}
				}
				else if(p.getType().equals(PieceType.TTYPE)){
					if(p.getOrientation().equals(Orientation.NORTH)){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/30.png"));
						
					}
					else if((p.getOrientation().equals(Orientation.EAST))){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/31.png"));
						
					}else if((p.getOrientation().equals(Orientation.SOUTH))){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/32.png"));
						
					}else{//WEST
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/33.png"));
						
					}
				}
				else if(p.getType().equals(PieceType.LTYPE)){
					if(p.getOrientation().equals(Orientation.NORTH)){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/50.png"));
					
					}
					else if((p.getOrientation().equals(Orientation.EAST))){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/51.png"));
						
					}else if((p.getOrientation().equals(Orientation.SOUTH))){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/52.png"));
						
					}else{//WEST
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/53.png"));
						
					}
				}
				else if(p.getType().equals(PieceType.FOURCONN)){
						img = new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/40.png"));
						
				}
				else{//VOID
					img = null;//new ImageIcon(GUITEST.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/background.png"));
				}
				lblcase.setIcon(img);
				frame.getContentPane().add(lblcase, BorderLayout.CENTER);

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
