package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CategoryListener implements ActionListener {

	
	private int button;
	GameObject go;
	private GameStatus gameStatus;
	
	public CategoryListener(int button, GameStatus gameStatus, GameObject go) {
		super();
		this.button=button;
		this.gameStatus=gameStatus;
		this.go=go;
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int roundsFinished=0;
		for(int i=0;i<13;i++) {
			if(go.categorySelected.get(i)==1)
				roundsFinished++;
		}
		if(roundsFinished==gameStatus.getRoundNumber()&& gameStatus.getRollNumber()!=0 &&go.categorySelected.get(button-1)==0) {		
		if(button<=6) {
			go.categorySelected.set(button-1,1);
			for(JCheckBox cb:go.checkBoxList) {
				cb.setSelected(false);
			}
			for(int i=0;i<5;i++) {
				go.stopDice.set(i,0);
			}
			int score=0;
			for(int i=0;i<5;i++) {
				if(go.dieSequence.get(i)==button)
					score+=go.dieSequence.get(i);
			}
			int subTotal=Integer.parseInt(go.textFields.get(6).getText())+score;
			int bonus=subTotal>=63?35:0;
			go.textFields.get(button-1).setText(score+"");
			go.textFields.get(6).setText((subTotal)+"");
			go.textFields.get(7).setText(bonus+"");
			go.textFields.get(8).setText((subTotal+bonus)+"");
			
			gameStatus.setRoundNumber(gameStatus.getRoundNumber()+1);
			gameStatus.setRollNumber(0);
			
		}
		if(button>6) {
		int canSelectLowerFlag=1;
		for(int i:go.dieSequence) {
			if(go.categorySelected.get(i-1)!=1){
				canSelectLowerFlag=0;
				break;
			}
		}
		
		if(canSelectLowerFlag==1) {
		
		for(JCheckBox cb:go.checkBoxList) {
			cb.setSelected(false);
		}
		for(int i=0;i<5;i++) {
			go.stopDice.set(i,0);
		}
		int score=0;
		Map<Integer,Integer> dieCount=new HashMap<Integer,Integer>();
		int yahtzeeFlag=0;
		for(int i:go.dieSequence) {
			if(dieCount.get(i)==null) {
				dieCount.put(i,0);
			}
			int sameDice=dieCount.get(i)+1;
			yahtzeeFlag=sameDice==5?1:0;
			dieCount.put(i,sameDice);
		}
		Set<Integer> keys=dieCount.keySet();
		int sumOfDice=0;
		for(int i:go.dieSequence) {
			sumOfDice+=i;
		}
		
		if(button==7) {
			for(int i:keys) {
				if(dieCount.get(i)>=3) {
					score+=sumOfDice;
				}
			}
		}
		else
		if(button==8) {
			for(int i:keys) {
				if(dieCount.get(i)>=4) {
					score+=sumOfDice;
				}
			}
		}
		else
		if(button==9) {
			int found2Flag=0;
			int found3Flag=0;
			for(int i:keys) {
				if(dieCount.get(i)==3) {
					found3Flag=1;
				}
				if(dieCount.get(i)==2) {
					found2Flag=1;
				}
			}	
			if(found2Flag==1 &&found3Flag==1) {
					score+=25;
			}
			else
			{
				if(yahtzeeFlag==1)
					score+=25;
			}
		}
		else
			if(button==10) {
				int straightCount=0;
				for(int i=1;i<4;i++) {
					int start=i;
					straightCount=0;
					for(int j=0;j<4;j++) {
						if(go.dieSequence.contains(start++)) {
							straightCount++;
						}
					}
					if(straightCount==4) {
						score+=30;
						break;
					}	
				}
				if(straightCount!=4)
				{
					if(yahtzeeFlag==1) {
						score+=30;
					}
				}
			}
			else
			if(button==11) {
				int straightCount=0;
				for(int i=1;i<3;i++) {
					int start=i;
					for(int j=0;j<5;j++) {
						if(go.dieSequence.contains(start++)) {
							straightCount++;
						}
					}
					if(straightCount==5) {
						score+=40;
						break;
					}
				}
				if(straightCount!=5)
				{
					if(yahtzeeFlag==1) {
						score+=30;
					}
				}
			}
			else
			if(button==12) {
				if(yahtzeeFlag==1) {
					score+=50;
				}
			}
			else
			if(button==13) {
					score+=sumOfDice;
				
			}	
		
		int bonus=Integer.parseInt(go.textFields.get(16).getText());
		if(go.categorySelected.get(11)==1) {
			if(yahtzeeFlag==1) {
				bonus+=100;
			}
		}
		
		go.categorySelected.set(button-1,1);
		
		int subTotal=Integer.parseInt(go.textFields.get(17).getText())+score;
		go.textFields.get(button+2).setText(score+"");
		go.textFields.get(16).setText(bonus+"");
		go.textFields.get(17).setText(subTotal+"");
		go.textFields.get(18).setText((subTotal+bonus)+"");
		
		gameStatus.setRoundNumber(gameStatus.getRoundNumber()+1);
		gameStatus.setRollNumber(0);
		
		}
		else
		if(canSelectLowerFlag==0) {
			JOptionPane.showMessageDialog(null, "The Corresponding Upper Section box is Unused");
		}
		
		}
	}
		else
		if(roundsFinished>=gameStatus.getRoundNumber()&&gameStatus.getRollNumber()==0) {
			JOptionPane.showMessageDialog(null, "Must Roll Dice atleast Once per Round");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The Category Has Been Selected Already");
		}
		
	}

}
