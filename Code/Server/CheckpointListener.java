package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import game.GameObject;
import game.GameStatus;
import game.YahtzeeFrame;

public class CheckpointListener implements ActionListener{

	public static GameStatus gameStatus;
	public static String name;
	public static int gameId;
	public GameObject go;
	public static YahtzeeFrame f;

	public CheckpointListener(GameStatus gameStatus, GameObject go, YahtzeeFrame f) { 
		super();
		this.go=go;
		this.gameStatus=gameStatus;
		this.f= f;
	}

	private Connection connect() {
		String url = "jdbc:sqlite:yahtzee.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public void saveGameData() {
        String sql = "INSERT INTO game(name,time,game_data) VALUES(?,?,?)";
        try {
        	System.out.print("insert data");
        	System.out.println("Player Name: "+go.playerName.getText());
        	Connection connect = this.connect();
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1,name);
            Date date = new Date();  
            GameObjectDB goDb=new GameObjectDB(go);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(goDb);
            byte[] gameAsBytes = baos.toByteArray();
           
            ByteArrayInputStream bais = new ByteArrayInputStream(gameAsBytes);
            
            DateFormat dateFrmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");  
            String dateStr = dateFrmt.format(date);  
            pstmt.setString(2, dateStr);
            pstmt.setBinaryStream(3, bais, gameAsBytes.length);
            
            int rows=pstmt.executeUpdate();
            System.out.print(rows+"<-rowCount");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
	
		public void generateTables() {
        String sql = "CREATE TABLE IF NOT EXISTS game("  
                + " game_id integer PRIMARY KEY AUTOINCREMENT,"
                + " name text," 
                + " time text,"
                + " game_data blob" 
                + ");";

        try {
        	Connection connect = this.connect();
        	Statement stmt = connect.createStatement();  
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	
	@Override
	public void actionPerformed(ActionEvent e) {
		name=go.playerName.getText();
		if(name!=null&&name.length()>0) {
			generateTables();
			System.out.println("Player Name: "+go.playerName.getText());
			saveGameData();
			System.out.println("Player Name: "+go.playerName.getText());
			
			String[] options = {"Continue", "New Game", "Exit"};
			int response = JOptionPane.showOptionDialog(f, "Game Saved. Select any of the below options", "Message", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if(response == 0) {
				System.out.print("Player Name: "+go.playerName.getText()+ " continues the game");
			}
			else if(response == 1) {
				System.out.print("Player Name: "+go.playerName.getText()+ " starts new game");
				f.setVisible(false);
				new YahtzeeFrame();
			}
			else if(response == 2) {
				System.out.print("Player Name: "+go.playerName.getText()+ " exits new game");
				f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Name should not be empty");	
		}
	}
	
}
