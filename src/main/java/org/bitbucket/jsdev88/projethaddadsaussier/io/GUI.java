package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
					GUI window = new GUI(new Grid(10,10));
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
				lblcase = new JLabel("");
				lblcase.setIcon(new ImageIcon(GUI.class.getResource("/org/bitbucket/jsdev88/projethaddadsaussier/io/ONECONNECT.png")));
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
