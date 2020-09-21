package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * This class is a main window in this app.
 * It is a simple window app with one label and three buttons
 * Button "NEW GAME" opens a window with a choice of board size and goes to the game.
 * Button "HIGH SCORES" opens a window with content of a file where are stored scores of a games ordered by score
 * Button "EXIT" closes the whole app.
 * This window is resizable and buttons are always in center of a window.
 * Color of the window is always drawn
 */

public class MemoryWindow extends JFrame
{
	//this variable get randomized color for app windows
	private Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
	//this is an object representing a window for choosing a size of a board of pictures for memory game
	private BoardSizeChoiceWindow bsc;
	
	//constructor
	private MemoryWindow() {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createMemory(); 
			}
		});
	}
	
	//main method for running the app
	public static void main(String[] args) {
		new MemoryWindow();
		}
	

	//method for create a main app window
	private void createMemory() {
	
		//setting values for the main frame
		setTitle("Memory The Game");
		setSize(600,600);
		setLayout(new GridBagLayout());
		setMinimumSize(new Dimension(250,280));
		getContentPane().setBackground(c);
		
		//setting panel
		JPanel panel = new JPanel();
		panel.setBackground(c);
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));	
			
		//creating and adding label to panel
		JLabel label = new JLabel("Memory The Game");
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(0,50))); 
				
		// "NEW GAME" Button, creating and adding to panel
		JButton newGame = new JButton("New Game");
		newGame.setMaximumSize(new Dimension(250,100));
		newGame.addMouseListener(new MyMouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(BoardSizeChoiceWindow.isBoardSizeChosen() == false) {
					bsc = new BoardSizeChoiceWindow(c);
					bsc.setIsBoardSizeChosen(true);
				}
				else if(bsc.isVisible() == false) {
					bsc.setLocationRelativeTo(rootPane);
					//bsc.setLocationRelativeTo(null);		
					bsc.setVisible(true);
				}
			}
		});
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(newGame);
				
		//add break between buttons
		panel.add(Box.createRigidArea(new Dimension(0,20)));
				
		// "HIGH SCORES" button, creating and adding to panel
		JButton highScores = new JButton("High Scores");
		highScores.setMaximumSize(new Dimension(250,100));
		highScores.addMouseListener(new MyMouseListener() {
			public void mouseClicked(MouseEvent e) {
			    new HighScoresWindow(c);	  
			}	
		});
		highScores.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(highScores);
				
		//add break between buttons
		panel.add(Box.createRigidArea(new Dimension(0,20)));
				
		// "EXIT" Button, creating and adding to panel
		JButton exit = new JButton("Exit");
		exit.setMaximumSize(new Dimension(250,100));
		exit.addMouseListener(new MyMouseListener() {
			public void mouseClicked(MouseEvent e) {
			    System.exit(ABORT);	  
			}		
		});
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(exit);
		
		//add panel to frame
		add(panel);
		
		//add last settings to frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}

	