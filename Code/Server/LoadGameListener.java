package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import game.ImagePanel;
import game.GameObject;
import game.GameStatus;

public class LoadGameListener implements ActionListener{

	public GameStatus gameStatus;
	public static String name;
	public GameObjectDB goDb;
	public GameObject go;
	public static int gameId;
	
	public LoadGameListener(GameStatus gameStatus, GameObject go){
		super();
		this.gameStatus=gameStatus;
		this.go=go;
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
	
	public List<SavedGames> getSavedGames(){
        String sql = "SELECT game_id,name,time FROM game";
        List<SavedGames> savedGames=new ArrayList<SavedGames>();
        try {
        	Connection connect = this.connect();
            Statement stmt  = connect.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
               while (rs.next()) {
            	   SavedGames sg=new SavedGames();
            	   sg.setGameId(rs.getInt("game_id"));
            	   sg.setName(rs.getString("name"));
            	   sg.setTime(rs.getString("time"));
            	   savedGames.add(sg);
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
        return savedGames;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		List<SavedGames> savedGames=getSavedGames();
		JFrame loadframe=new JFrame();
		loadframe.setTitle("Saved Games");
		JPanel loadpanel=new JPanel();
		loadpanel.setLayout(new BoxLayout(loadpanel, BoxLayout.Y_AXIS));
		loadpanel.setSize(new Dimension(300,500));
		for(SavedGames sg:savedGames) {
			JButton button = new JButton("Player:"+sg.getName()+" - Saved On:"+sg.getTime());
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameId=sg.getGameId();
					loadframe.dispatchEvent(new WindowEvent(loadframe, WindowEvent.WINDOW_CLOSING));
					getGamedata();
				}
			});
			loadpanel.add(button);
			loadpanel.add(Box.createRigidArea(new Dimension(0,10)));
		}
		
		JScrollPane scrollPane = new JScrollPane(loadpanel);
		scrollPane.setPreferredSize(new Dimension(250,80));
		loadframe.setSize(500, 400);
		loadframe.add(scrollPane,BorderLayout.CENTER);
		loadframe.setVisible(true);  
		
	}

		private void getGamedata() {
		String sql = "SELECT game_data FROM game where game_id=?";
        try {Connection connect = this.connect();
        		PreparedStatement pstmt = connect.prepareStatement(sql);
        		pstmt.setInt(1,gameId);
                ResultSet rs   = pstmt.executeQuery();
               while (rs.next()) {
            	   byte[] game = (byte[]) rs.getObject(1);
            	      ByteArrayInputStream baip = new ByteArrayInputStream(game);
            	      ObjectInputStream ois = new ObjectInputStream(baip);
            	      goDb = (GameObjectDB) ois.readObject();
            	      go.modifyGameState(goDb);
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }catch (IOException e) {
        	   System.out.println(e.getMessage());
        	}catch (ClassNotFoundException e) {
         	   System.out.println(e.getMessage());
         	}
	}

}
