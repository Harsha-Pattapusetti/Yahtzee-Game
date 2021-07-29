package server;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JTextField;

import game.GameObject;
import game.GameStatus;

public class GameObjectDB implements Serializable {
	
	
	private ArrayList<Integer> dieSequence;
	private ArrayList<Integer> stopDice;
	private ArrayList<Integer> categorySelected;
	private GameStatus gameStatus;
	private String playerName;
	private ArrayList<String> textContent;
	
	public GameObjectDB(GameObject go) {
		this.dieSequence=go.dieSequence;
		this.stopDice=go.stopDice;
		this.categorySelected=go.categorySelected;
		this.gameStatus=go.gameStatus;
		this.playerName=go.playerName.getText();
		this.textContent=new ArrayList<String>();
		for(JTextField jt:go.textFields) {
			this.textContent.add(jt.getText());
		}
	}
	public ArrayList<Integer> getDieSequence() {
		return dieSequence;
	}
	public void setDieSequence(ArrayList<Integer> dieSequence) {
		this.dieSequence = dieSequence;
	}
	public ArrayList<Integer> getStopDice() {
		return stopDice;
	}
	public void setStopDice(ArrayList<Integer> stopDice) {
		this.stopDice = stopDice;
	}
	public ArrayList<Integer> getCategorySelected() {
		return categorySelected;
	}
	public void setCategorySelected(ArrayList<Integer> categorySelected) {
		this.categorySelected = categorySelected;
	}
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public ArrayList<String> getTextContent() {
		return textContent;
	}
	public void setTextContent(ArrayList<String> textContent) {
		this.textContent = textContent;
	}
	
}
