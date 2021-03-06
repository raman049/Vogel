package servlets;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class LastPage {
static highScore HIscore;
	public class Action {

	}

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LastPage window = new LastPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LastPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(Color.RED);
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// High Score
		String HIscore ="";
		try {
			HIscore = highScore.updateHiScore(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTextField txtHighScore = new JTextField();
		txtHighScore.setText("HIGH SCORE: "+HIscore);
		txtHighScore.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		txtHighScore.setBounds(293, 201, 234, 39);
		frame.getContentPane().add(txtHighScore);
		txtHighScore.setColumns(10);

		JTextField txtYourScore = new JTextField();
		txtYourScore.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		txtYourScore.setText("YOUR SCORE");
		txtYourScore.setForeground(new Color(0, 0, 128));
		txtYourScore.setBounds(293, 275, 234, 39);
		frame.getContentPane().add(txtYourScore);
		txtYourScore.setColumns(10);

		JButton btnNewButton = new JButton("START");
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		btnNewButton.setBounds(354, 410, 117, 61);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new CustomActionListener());

		JButton btnNewButton_1 = new JButton("EXIT");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		btnNewButton_1.setBounds(354, 563, 117, 61);
		frame.getContentPane().add(btnNewButton_1);
		frame.setResizable(false);

		btnNewButton_1.addActionListener(new EndButtonAction());

	}

	class CustomActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Start here
			frame.setVisible(false);
			System.out.println("hello");
			// new Activity();
			Activity a = new Activity();
			a.ConnectActivity();

		}

	}

	class EndButtonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO exit here
			System.exit(0);
		}

	}

	public void ConnectLastpage() {
		LastPage lastPage = new LastPage();
		lastPage.frame.setVisible(true);
	}

}