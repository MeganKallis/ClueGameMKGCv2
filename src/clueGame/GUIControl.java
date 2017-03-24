package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIControl extends JPanel {
	private JTextField name;
	private JTextField turnBox;
	private JTextField roll;
	private JTextField guess;
	private JTextField response;


	public GUIControl()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = buttonRow();
		add(panel);
		panel = textBoxRow();
		add(panel);
	}

	 private JPanel textBoxRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		
		JPanel diePanel = new JPanel();
		diePanel.setLayout(new GridLayout(1,2));
		JLabel rollLabel = new JLabel("Roll");
		roll = new JTextField(3);
		roll.setEditable(false);
		diePanel.add(rollLabel);
		diePanel.add(roll);
		diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		panel.add(diePanel);
		
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2,1));
		JLabel guessLabel = new JLabel("Guess");
		guess = new JTextField(40);
		guess.setEditable(false);
		guessPanel.add(guessLabel);
		guessPanel.add(guess);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		panel.add(guessPanel);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,2));
		JLabel responseLabel = new JLabel("Resonse");
		response = new JTextField(20);
		response.setEditable(false);
		resultPanel.add(responseLabel);
		resultPanel.add(response);
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "GuessResult"));
		panel.add(resultPanel);
		
		return panel;
	}

	private JPanel buttonRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		JLabel turn = new JLabel("Whose turn?");
		turnPanel.add(turn);
		turnBox = new JTextField(40);
		turnBox.setEditable(false);
		turnPanel.add(turnBox);
		panel.add(turnPanel);
		
		JButton nextPlayer = new JButton("Next player");
		panel.add(nextPlayer);
		JButton makeAccusation = new JButton("Make an accusation");
		panel.add(makeAccusation);
		
		
		return panel;
	}

	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 200);	
		// Create the JPanel and add it to the JFrame
		GUIControl gui = new GUIControl();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}


}