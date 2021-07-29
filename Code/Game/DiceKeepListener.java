package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class DiceKeepListener implements ItemListener {
	private static GameObject go;
	private int boxNo;
	public DiceKeepListener(int boxNo,GameObject go) {
		this.boxNo=boxNo;
		this.go=go;
	}
	
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			this.go.stopDice.set(boxNo,1);
		}
		else
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			this.go.stopDice.set(boxNo,0);
		}
		
	}
}
