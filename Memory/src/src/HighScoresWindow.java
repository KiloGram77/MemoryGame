package src;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


import javax.swing.*;

/*
 * This class represents window to present scores
 * It reads file, stores scores in an array
 * and before presenting them it sorts them and add some additional info
 */

public class HighScoresWindow extends JFrame
{
	
	File file = new File("wyniki.txt"); //this is file where are stored scores.
	ArrayList <String> scoreList = new ArrayList<>();
	Scanner in;
	Color c;
	
	//constructor
	public HighScoresWindow(Color c) {	
		this.c = c;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createHighScores(); 
				}
			});
		}

	//method for create a window that presents scores in a table
	private void createHighScores() {
	
		//setting values for the scores frame
		setTitle("High Scores");
		setSize(600,600);
		setLayout(new BorderLayout());
		getContentPane().setBackground(c);
		
		//read a file method
		readFile();
		//sort the scores method
		sortScores();
		
		//set properties of a text area to present scores
		JTextArea ja = new JTextArea();
		ja.setText(concatenat());
		ja.setForeground(c);
		ja.setFont(new Font("Helvetica",Font.ROMAN_BASELINE,20));
		ja.setEditable(false);
		JScrollPane jop = new JScrollPane(ja);
		add(jop);	
				
		//add last settings to frame
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	private void readFile() {
		String line;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(in.hasNextLine()) {
			line = in.nextLine();
			scoreList.add(line);
		}
	}
	
	private String concatenat() {
		String scores = "";
		String [] tab;
		for (int i = 0; i < scoreList.size(); i++) {
			tab = scoreList.get(i).split(" ");
			scores = scores + " " + (i+1) +  ". " + tab[3] + "    Wynik: " + tab[0] 
					+ "    Czas: " + tab[1] + "    Wymiary: " + tab[2] + "\n" + "\n";
		}
		return scores;
	}
	
	private void sortScores() {
		Collections.sort(scoreList,
			new Comparator <String>() {
			    public int compare (String o1, String o2) { 
			    	String [] s1 = o1.split(" ");
			    	String [] s2 = o2.split(" ");
			    	double l1 = Double.parseDouble((s1[0]));
			    	double l2 = Double.parseDouble((s2[0]));
			    	if (l1 < l2) {
			    		return -1;
			    	}
			    	if (l1 > l2) {
			    		return 1;
			    	}
			    	return 0;
			    }
		    });
	}	

}

	