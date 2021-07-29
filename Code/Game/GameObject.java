package game;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import server.GameObjectDB;

public class GameObject implements Serializable {

	public ArrayList<ImagePanel> diceImages;
	public ArrayList<Integer> dieSequence;
	public ArrayList<Integer> stopDice; 
	public ArrayList<JTextField> textFields;
	public ArrayList<Integer> categorySelected;
	public ArrayList<JCheckBox> checkBoxList;
	public ArrayList<JButton> buttonsList;
	public JTextField playerName;
	public GameStatus gameStatus;
	
	public GameObject() {
		this.diceImages=new ArrayList<ImagePanel>();
		this.dieSequence=new ArrayList<Integer>();
		this.stopDice=new ArrayList<Integer>();
		this.textFields=new ArrayList<JTextField>();
		this.categorySelected=new  ArrayList<Integer>();
		this.checkBoxList=new ArrayList<JCheckBox>();
		this.buttonsList=new ArrayList<JButton>();
		this.gameStatus=new GameStatus();
	}

	public void modifyGameState(GameObjectDB goDb) {
		this.dieSequence=goDb.getDieSequence();
		this.stopDice=goDb.getStopDice();
		this.categorySelected=goDb.getCategorySelected();
		this.gameStatus.setRollNumber(goDb.getGameStatus().getRollNumber());
		this.gameStatus.setRoundNumber(goDb.getGameStatus().getRoundNumber());
		int i=0;
		for(String st:goDb.getTextContent()) {
			this.textFields.get(i++).setText(st);
		}
		for(int j=0;j<5;j++)
		{
			if(this.stopDice.get(j)==0)
			this.checkBoxList.get(j).setSelected(false);
			if(this.stopDice.get(j)==1)
			this.checkBoxList.get(j).setSelected(true);
		}
		for(i=0;i<5;i++) {	
			this.diceImages.get(i).setImage(new ImageIcon("die"+this.dieSequence.get(i)+".png").getImage());
		}
		this.playerName.setText(goDb.getPlayerName());
	}
}
