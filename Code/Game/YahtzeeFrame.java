package game;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import server.LoadGameListener;
import server.CheckpointListener;

public class YahtzeeFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	public static GameObject go;
	public static GameStatus gameStatus;
	public static final int ROUNDS=13;
	public static final int ROLLS=3;
	public static ArrayList<JLabel> labels;
	private GridBagLayout gridBagLayout;
	private GridBagConstraints mainFrameConstraints;
	private static JMenuItem loadGame;
	private static JMenuItem saveGame;
	private static JMenuItem exit;
	
	public YahtzeeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,700);
		go=new GameObject();
		gridBagLayout = new GridBagLayout();
		mainFrameConstraints = new GridBagConstraints();
	    mainFrameConstraints.weightx = 0.5;
	    mainFrameConstraints.weighty = 0.5;
		
		setTitle("Yahtzee");
		setLayout(gridBagLayout);
		
		for(int i=0;i<5;i++) {
			go.stopDice.add(0);
			go.dieSequence.add(1);
		}
		for(int i=0;i<14;i++) {
			go.categorySelected.add(0);
		}
		
		gameStatus=go.gameStatus;
		
		//*******************************MENU BAR START********************************
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Game");
		loadGame = new JMenuItem("Load Game");
		saveGame = new JMenuItem("Save Game");
		exit = new JMenuItem("Exit");
		menu.add(loadGame);
		menu.add(saveGame);
		menu.add(exit);
		setJMenuBar(mb); 
		mb.add(menu);	
		//--------------------------------MENU BAR END-------------------
		
	    mainFrameConstraints.fill = GridBagConstraints.HORIZONTAL;
	    mainFrameConstraints.anchor = GridBagConstraints.NORTHWEST;
		
		//*****************************PLAYER NAME PANEL START******************************
		JPanel player=new JPanel();
		player.setLayout(new GridBagLayout());
        GridBagConstraints playerConstraint = new GridBagConstraints(); 
        playerConstraint.insets = new Insets(5, 5, 5, 5); 
        playerConstraint.fill = GridBagConstraints.HORIZONTAL;
        playerConstraint.weightx = 1;
        playerConstraint.weighty = 1;
        playerConstraint.gridwidth = 2;
		JLabel pName=new JLabel("Player Name: ");
		player.add(pName);
		go.playerName=new JTextField(10);
		player.add(go.playerName, playerConstraint);
		addGB(player, 0, 0);
		//--------------------------------PLAYER NAME PANEL END-------------------
		
	    mainFrameConstraints.fill = GridBagConstraints.BOTH;
	    mainFrameConstraints.weightx = 3;
	    mainFrameConstraints.weighty = 3;
		
		//*******************************UPPER SECTION PANEL START****************************
		JPanel upper = new JPanel();
		Border upperLbl = BorderFactory.createTitledBorder("Upper Section");
		upper.setLayout(new GridBagLayout());
        GridBagConstraints upperConstraint = new GridBagConstraints(); 
        upperConstraint.insets = new Insets(5, 5, 5, 5); 
        upperConstraint.fill = GridBagConstraints.BOTH;
        upperConstraint.weightx = 1;
        upperConstraint.weighty = 1;
        
		for(int i=0;i<9;i++) {
			go.textFields.add(new JTextField(10));
		}
		go.textFields.get(6).setText("0");
		go.textFields.get(7).setText("0");
		go.textFields.get(8).setText("0");
		
		labels=new ArrayList<JLabel>();
		labels.add(new JLabel("Sub Total"));
		labels.add(new JLabel("Bonus"));
		labels.add(new JLabel("Grand Total"));
		
		go.buttonsList.add(new JButton("Aces"));
		go.buttonsList.add(new JButton("Twos"));
		go.buttonsList.add(new JButton("Threes"));
		go.buttonsList.add(new JButton("Fours"));
		go.buttonsList.add(new JButton("Fives"));
		go.buttonsList.add(new JButton("Sixes"));
		
		for(int i=0;i<6;i++) {
			upperConstraint.gridx = 0;
			upperConstraint.gridy = i;
			upper.add(go.buttonsList.get(i), upperConstraint);
			upperConstraint.gridx = 1;
			upper.add(go.textFields.get(i), upperConstraint);
		}
		
		for(int i=6; i<9; i++) {
			upperConstraint.gridx = 0;
			upperConstraint.gridy = i;
			upper.add(labels.get(i-6), upperConstraint);
			upperConstraint.gridx = 1;
			upper.add(go.textFields.get(i), upperConstraint);
		}
		
		upper.setBorder(upperLbl);
		upper.setSize(400,200);
		addGB(upper, 0, 1);
		//--------------------------------UPPER SECTION PANEL END-------------------
		
		//********************************LOWER SECTION PANEL START*****************************
		JPanel lower = new JPanel();
		Border lowerLbl = BorderFactory.createTitledBorder("Lower Section");
		lower.setLayout(new GridBagLayout());
        GridBagConstraints lowerConstraint = new GridBagConstraints(); 
        lowerConstraint.insets = new Insets(5, 5, 5, 5); 
        lowerConstraint.fill = GridBagConstraints.BOTH;
        lowerConstraint.weightx = 1;
        lowerConstraint.weighty = 1;
        
		for(int i=9;i<19;i++) {
			go.textFields.add(new JTextField(10));
		}
		go.textFields.get(16).setText("0");
		go.textFields.get(17).setText("0");
		go.textFields.get(18).setText("0");
		
		labels.add(new JLabel("Yahtzee Bonus"));
		labels.add(new JLabel("Total Of Lower Section"));
		labels.add(new JLabel("Grand Total"));
		
		go.buttonsList.add(new JButton("3 of a Kind"));
		go.buttonsList.add(new JButton("4 of a Kind"));
		go.buttonsList.add(new JButton("Full House"));
		go.buttonsList.add(new JButton("Small Straight"));
		go.buttonsList.add(new JButton("Large Straight"));
		go.buttonsList.add(new JButton("Yahtzee"));
		go.buttonsList.add(new JButton("Chance"));
		
		for(int i=6;i<13;i++) {
			lowerConstraint.gridx = 0;
			lowerConstraint.gridy = i;
			lower.add(go.buttonsList.get(i), lowerConstraint);
			lowerConstraint.gridx = 1;
			lower.add(go.textFields.get(i+3), lowerConstraint);
		}
		
		for(int i=16; i<19; i++) {
			lowerConstraint.gridx = 0;
			lowerConstraint.gridy = i;
			lower.add(labels.get(i-13), lowerConstraint);
			lowerConstraint.gridx = 1;
			lower.add(go.textFields.get(i), lowerConstraint);
		}
		
		lower.setBorder(lowerLbl);
		lower.setSize(400,200);
		addGB(lower, 0, 2);
		//--------------------------------UPPER SECTION PANEL END-------------------
		
		mainFrameConstraints.gridheight = GridBagConstraints.REMAINDER;
		
		//********************************DICE PANEL START*****************************
		JPanel images=new JPanel();
		images.setSize(300,500);
		images.setLayout(new GridBagLayout());
        GridBagConstraints diceConstraint = new GridBagConstraints(); 
        diceConstraint.insets = new Insets(5, 5, 5, 5); 
        diceConstraint.weighty = 1;
        diceConstraint.weightx = 1;
        diceConstraint.gridx = 0;
        diceConstraint.fill = GridBagConstraints.BOTH;
		
		for(int i=0;i<5;i++) {
			go.checkBoxList.add(new JCheckBox("Keep"));
			go.checkBoxList.get(i).addItemListener(new DiceKeepListener(i,go));
		}	
		
		for(int k=0;k<5;k++) {
			go.diceImages.add(new ImagePanel(new ImageIcon("die1.png").getImage()));
			go.diceImages.get(k).scaleImage(0.35);
			images.add(go.diceImages.get(k), diceConstraint);
			images.add(go.checkBoxList.get(k), diceConstraint);
		}
		
		JButton roll=new JButton("Roll");
		images.add(roll, diceConstraint);
		go.buttonsList.add(roll);
		addGB(images, 1, 1);
		//--------------------------------DICE PANEL END-------------------	
		
		// set frame visible
		pack();
		setVisible(true);
		
		roll.addActionListener(new DiceRollListener(gameStatus,go));
		
		for(int i=1;i<=13;i++) {
			go.buttonsList.get(i-1).addActionListener(new CategoryListener(i,gameStatus,go));
		}
		
		saveGame.addActionListener(new CheckpointListener(gameStatus,go,this));
		loadGame.addActionListener(new LoadGameListener(gameStatus,go));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispatchEvent(new WindowEvent(new YahtzeeFrame(), WindowEvent.WINDOW_CLOSING));
			}
		});
		 
	}
	
	private void addGB(Component component, int x, int y) {
		mainFrameConstraints.gridx = x;
		mainFrameConstraints.gridy = y;
		gridBagLayout.setConstraints(component, mainFrameConstraints);
	    add(component);
	  }
	
	public static void main(String args[]) {
		YahtzeeFrame yahtzee = new YahtzeeFrame();
	}
	
}
