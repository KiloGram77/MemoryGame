package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * This class creates a window that allows a player to choose a size of a board
 * Then it runs the game
 */

public class BoardSizeChoiceWindow extends JFrame
{
	
	private Integer [] boardSizeList = new Integer [] {2,4,6,8,10};
	private Color c;
	private static boolean isBoardSizeChosen = false;
	private int boardSize = -1;
	
	
	protected BoardSizeChoiceWindow(Color c) {
		this.c = c;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createBoardSizeChoice();
				}
			});
		}

	private void createBoardSizeChoice() {	
		setTitle("New Game");
		setSize(300,230);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		setLayout(new FlowLayout()); 
		getContentPane().setBackground(c);
			
		add(Box.createRigidArea(new Dimension(300,40)));
		add(Box.createRigidArea(new Dimension(20,50)));
		
		JLabel label = new JLabel("Select Grid Size:");
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		add(label);	
		add(Box.createRigidArea(new Dimension(30,50)));
				
		//Combobox with choice of a board size
		JComboBox<Object> jcb = new JComboBox<Object>(boardSizeList);
		jcb.setSelectedItem(null);
		add(jcb);
				
		add(Box.createRigidArea(new Dimension(300,10)));
				
		// "PLAY" button
		JButton play = new JButton("Play");
		play.setPreferredSize(new Dimension(120,25));
		play.addMouseListener(new MyMouseListener() {
			public void mouseClicked(MouseEvent e) {
				boardSize = (Integer) jcb.getSelectedItem();
				if(boardSize >= 2 ) {
					GameWindow.newGame(c,boardSize);
					dispose();	//Releases all of the native screen resources used by this Window 
				}
			}
		});
		add(play);		
		setVisible(true);
	}
	
	 public static boolean isBoardSizeChosen() {
		return isBoardSizeChosen;
	}

	public void setIsBoardSizeChosen(boolean b) {
		isBoardSizeChosen = b;
	}
	
	public void dispose() {
	    super.dispose();
	}	

}

	