package game;

import java.io.Serializable;

public class GameStatus implements Serializable {
	private int roundNumber;
	private int rollNumber;
	public GameStatus() {
		this.rollNumber=0;
		this.roundNumber=0;
	}
	public int getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	public int getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}
	
}
