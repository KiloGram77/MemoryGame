package src;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class GameWindow extends JFrame
{
	
	private Color c; //color inherited from the main window
	private int boardSize; //size of a game board 
	private static GameWindow game; //prevents for multiple game windows
	private File f = new File ("Obrazy 400"); //name of catalog with pictures
	private ArrayList <ImageIcon> picturesList= new ArrayList<>(); //list of pictures that are read from a catalog
	
	private MyButton [] buttonList; //list of buttons on a board. Each button will store a one picture.
	private int [] listForChoosingPictures; //list of shuffled pictures. This list will be used for storing pictures in buttons
	
	//buttons that are clicked to check whether under them are stored same pictures
	private MyButton button1 = null;
	private MyButton button2 = null;
	
	private JPanel panel = new JPanel(); //panel that will store all the buttons

	private int guessCounter  = (boardSize*boardSize)/2; //this variable will count good choices, 
														//when achieves specific value ends the game
	private static Timer time; 
	private int smallCounter;
	private Timer counter; //this timer is counting time from the game beginning
	private int uncoverPicturescounter = 0; 
	private Color color; //color for the borders of a choosen buttons
	
	//constructor
	private GameWindow(Color c, int boardSize) {
		this.c = c;
		this.boardSize = boardSize;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGame();
			}
		});
	}
	
	
	//main method for creating a game
	private void createGame() {
		
		//setting values for the GAME frame
		setTitle("Game");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(d.height-50,d.height);
		setResizable(false);
		setLayout(new BorderLayout()); 
		
		//methods do what is in their names :)
		createListOfPictures(f);
		Collections.shuffle(picturesList);
		createListOfButtons(boardSize);
		addMouseListenerToButtons();
		listForChoosingPictures = shuffleTable(boardSize);
		addPicturesToButtons();
		
		panel.setLayout(new GridLayout(boardSize,boardSize));
		addButtonsToPanel(panel);
	
		//add last settings to frame
		add(panel,BorderLayout.CENTER);
		createAndSetTimer();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		time = new Timer(500, new TimerListener());
		color = new Color (255 - c.getRed(),255 - c.getGreen(),255 - c.getBlue()); 
	}	
	
	//this method resize each image to an appropriate size to fit into place on a board
	private ImageIcon changeIconSize (ImageIcon icon, int height, int weight) {
			Image image = icon.getImage();
			ImageIcon ii = new ImageIcon(image.getScaledInstance(height, weight,0));	
		return ii;
	}
	
	//this method create and run a timer that once a second change a time on a screen
	private void createAndSetTimer() {
		JLabel label =  new JLabel(" ");
		counter = new Timer(1000, new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		        label.setText("Mine³o: " + smallCounter++ +" sekund");
		    }
		});
		counter.start();	
		label.setHorizontalAlignment(getX());
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
		label.setMaximumSize(new Dimension(0,100));
		label.setForeground(Color.RED);
	add(label,BorderLayout.PAGE_END);
	}
	
	//only .jpg pictures are allowed in folder with pictures
	private void createListOfPictures(File dir) {
        for (File file : dir.listFiles()) {
            if (file.isFile() & file.getName().endsWith(".jpg")) {
            	picturesList.add(new ImageIcon(file.getAbsolutePath()));
	                }
            }
        }
	
	//creates list of buttons
	private void createListOfButtons (int boardSize) {
		buttonList = new MyButton [boardSize * boardSize];
		for (int i = 0 ; i < (boardSize * boardSize); i++) {
			MyButton mb = new MyButton();
			mb.setNumber(i);
			mb.setFocusable(false);
			mb.setBackground(c);
			buttonList[i] = mb;
		}
	}
	
	private int [] shuffleTable (int boardSize) {
		int [] array =  new int [boardSize * boardSize] ;
		for (int i = 0; i <array.length; i++ ) {
			array[i] = -1;
		}	
		int counter = 0;
		int var1 = 0;
		int var2 = 0;
		int number = 0;
		while(counter < array.length/2) {
			var1 = 0;
			while (var1 == 0) {
				number = (int)(Math.random()*((boardSize * boardSize )));
				if (array[number] == -1) {
					array[number] = counter;
				var1 = 1;
				}
			}
			var2 = 0;
			while (var2== 0) {
				number = (int)(Math.random()*((boardSize * boardSize)));
				if (array[number] == -1) {
				array[number] = counter;
				var2 = 1;
				counter ++;
				}
			}
		}
		return array;
	}
	
	private void addButtonsToPanel(JPanel jp) {
		for(MyButton button : buttonList) {
			jp.add(button);	
		}
	}
	
	private void addPicturesToButtons() {
		int variable = 0;
		for(MyButton button : buttonList) {
			int number = listForChoosingPictures[variable];
			button.setMyIcon(picturesList.get(number));
			button.setPictureNumber(number);
			variable++;
		}		
	}
	
	private void addMouseListenerToButtons() {
		for(MyButton obrazek : buttonList) {
			obrazek.addMouseListener(new MyMouseListener() {
				public void mouseClicked(MouseEvent e) {
					if (button2 == null  || button1 == null ) {
						if (button1 == null) {
							button1 = obrazek;
							uncoverPicturescounter++;
							obrazek.setIcon(changeIconSize(obrazek.getMyIcon(),obrazek.getWidth(),obrazek.getHeight()));
						}
						else {
							if (button2 == null) {
								if(button1.getNumber() != obrazek.getNumber()) {
									button2 = obrazek;
									obrazek.setIcon(changeIconSize(obrazek.getMyIcon(),obrazek.getWidth(),obrazek.getHeight()));
									uncoverPicturescounter++;
								}
							}
						}
					}
					if(button2 != null && button1 != null) {
						if(button1.getMyIcon().equals(button2.getMyIcon())) {
							guessCounter++;
							button1.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f),color));
							button2.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f),color));
							button1.setBorderPainted(true);
							button2.setBorderPainted(true);
							removeMouseListeners();
							setButtonsNull();
						}else {
							time.start();	
						}
					}
					if(guessCounter == (boardSize*boardSize)/2) {
						createNameChoiceField();
						dispose();
					}
				}
			});
		}
	}

	private void coverPictures() {
		button1.setIcon(null);
		button2.setIcon(null);
	}
	
	private void setButtonsNull() {
		button1 = null;
		button2 = null;
	}
	
	private void removeMouseListeners() {
		for (MouseListener ml : button1.getMouseListeners()) {
			button1.removeMouseListener(ml);
		}
		for (MouseListener ml : button2.getMouseListeners()) {
			button2.removeMouseListener(ml);
		}
	}
	
	private void createNameChoiceField() {
		new NameChoiceWindow(c,boardSize, smallCounter, uncoverPicturescounter);
	}
	
	private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	coverPictures();
			setButtonsNull();
            time.stop();
        }
    }
	
	public static GameWindow newGame (Color c, int boardSize) {
        if (game == null) {
        	game = new GameWindow(c, boardSize);
        }
        return game;
    }
	
	public void dispose() {
		game = null;
	    super.dispose();
	}
	
	
private class MyButton extends JButton{
		
		private ImageIcon icon;
		private int number = -1;
		
		public MyButton() {
			super();	
		}
		
		public ImageIcon getMyIcon() {
			return icon;
		}
		
		public void setMyIcon(ImageIcon im) {
			icon = im;
		}
		
		public void setPictureNumber(int number) {
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}	
	}

}

	