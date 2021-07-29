package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

public class DiceRollListener implements ActionListener {

	private static GameObject go;
	private static GameStatus gameStatus;
	public static final int ROUNDS=13;
	public static final int ROLLS=3;

	public DiceRollListener(GameStatus gameStatus, GameObject go) {
		super();
		this.gameStatus=gameStatus;
		this.go=go;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int selected=0;
		for(int k=0;k<5;k++) {
			if(go.stopDice.get(k)==1) {
					selected++;
				}
			}
		if(selected<5) {
		Map<Integer,Integer> diceMap=new HashMap<Integer,Integer>();
		int initialRollFlag=1;
		for(int i=0;i<5;i++) {
			diceMap.put(i,go.dieSequence.get(i)-1);
		}
		if(gameStatus.getRollNumber()==0) {
			for(int k=0;k<5;k++) {
				if(go.stopDice.get(k)==1) {
					go.checkBoxList.get(k).setSelected(false);
					go.stopDice.set(k,0);
					initialRollFlag=0;
				}
			}
			if(initialRollFlag==0) {	
				JOptionPane.showMessageDialog(null, "Should Not Keep Dice in First Roll");
			}
		}
		if(gameStatus.getRoundNumber()<13&&gameStatus.getRollNumber()<3) {			
			for(int i=0;i<5;i++) {	
			}	
			for(int k=1;k<=5;k++) {
					if(go.stopDice.get(k-1)==0) {
						diceMap.put(k,((int)(Math.random()*43))%6);
					}
				}
				String seq="";
				for(int i=0;i<5;i++) {	
					if(go.stopDice.get(i)==0) {
					go.diceImages.get(i).setImage(new ImageIcon("die"+(diceMap.get(i+1)+1)+".png").getImage());
					go.dieSequence.set(i, diceMap.get(i+1)+1);
					seq+="die"+(diceMap.get(i+1)+1)+".png******";
					}
				}
				gameStatus.setRollNumber(gameStatus.getRollNumber()+1);
			}
		else
		if(gameStatus.getRollNumber()<13&&gameStatus.getRollNumber()==3) {
			JOptionPane.showMessageDialog(null, "Cannot roll more than 3 times in a round");
		}
		else {
			JOptionPane.showMessageDialog(null, "Game Over!");
		}
			
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Must Roll Atleast 1 Die Please Uncheck Any Checkbox");
		}
	}
	
	
	}


