package src;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

/*
 * This class creates a window where a player can choose his nickname.
 * The score, time of a game and nickname are stored in a file
 */

public class NameChoiceWindow extends JFrame
{

	private Color c;
	private int boardSize;
	private int smallCounter;
	private File file = new File("wyniki.txt");
	private PrintWriter out;
	private ArrayList <String> scoreList = new ArrayList <>();
	private int uncoverPicturesCounter;
	
	
	protected NameChoiceWindow(Color c, int boardSize, int smallCounter, int uncoverPicturesCounter) {
		this.c = c;
		this.uncoverPicturesCounter = uncoverPicturesCounter;
		this.boardSize = boardSize;
		this.smallCounter = smallCounter;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createNameChoiceField();
				}
			});
		}
	
	private void createNameChoiceField() {
	
		setTitle("Choose Nickname");
		setSize(300,230);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout()); 
		getContentPane().setBackground(c);
			
		add(Box.createRigidArea(new Dimension(300,30)));
		add(Box.createRigidArea(new Dimension(30,50)));
			
		JLabel label = new JLabel("Choose Your Nickname:");
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		add(label);
		
		add(Box.createRigidArea(new Dimension(20,50)));
						
		JTextField jl = new JTextField();
		jl.setSize(new Dimension(100,20));
		jl.setPreferredSize(new Dimension(130,20));
		jl.setEditable(true);
		add(jl);
				
		add(Box.createRigidArea(new Dimension(300,10)));
				
		// "PLAY" button
		JButton play = new JButton("Save");
		play.setPreferredSize(new Dimension(120,25));
		play.addMouseListener(new MyMouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(isFit(jl.getText())==true){
					;
					scoreList.add((((double)(Math.round(((((double)smallCounter)
							/(boardSize*boardSize))*uncoverPicturesCounter)*1000)))/1000) 
							+ " " + smallCounter  + " " + boardSize + "x" + boardSize + " " + jl.getText());
					saveToFile();
					dispose();
				}
			}
		});
		add(play);		
		setVisible(true);
		loadFile(file);			
	}
	
	
	private boolean isFit(String str) {
	 Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
     Matcher matcher = pattern.matcher(str);
     	if(str.length() > 0 && str.length() <= 20 && isUnique(str)) {
     		if (matcher.find()) {
     			return true;
     		}
     		else
     			return false;	
     	}
     	else
     		return false;
	}
	
	private boolean isUnique(String str) {
		int repetitionsCounter= 0;
		for (String s : scoreList){
			String [] tab = s.split(" ");
			if(str.equals(tab[3])) {
				repetitionsCounter++;
			}
		}
			if(repetitionsCounter > 0)
				return false;
			else 
				return true;
	}
	
	
	private void loadFile(File file) {
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(in.hasNextLine() == true) {
			String sentence = in.nextLine();	  
			scoreList.add(sentence);
		}
	}
	
	private void saveToFile() {
		try {
			out = new PrintWriter ("wyniki.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (String s : scoreList)
			out.println(s);
		out.close();
	}
	
	public void dispose() {
		super.dispose();
	}
	
}

	