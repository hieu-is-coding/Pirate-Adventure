package ranking;

import javax.swing.*;
import java.awt.event.*;

public class ScoreBoard extends JFrame implements ActionListener{
	private JTextField textBox;
    private JButton enterButton;
    private JLabel nameLabel;
    private JPanel panel;
    private int playedTime;
    private String playerName;

    public ScoreBoard(int playedTime){
    	this.playedTime = playedTime;
    	
	    //create a label and button
	    nameLabel = new JLabel("Your name, sir ?");
	    textBox = new JTextField(20);
	    enterButton = new JButton("Enter");
	    enterButton.addActionListener(this);
	
	    //create a panel and add components to it
	    panel = new JPanel();
	    panel.add(nameLabel);
	    panel.add(textBox);
	    panel.add(enterButton);
	    add(panel);
	
	    //set frame
	    setTitle("CONGRATULATION! YOU WIN");
	    setSize(400, 100);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
	     playerName = textBox.getText();
	     setVisible(false);
	     update();
	}
	private void update() {
		 Database db = new Database("Scoreboard");
//	     db.createTable();
	     db.insertData(playerName, playedTime);    
	     db.displayData();
	}

}
