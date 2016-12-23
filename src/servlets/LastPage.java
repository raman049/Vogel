package servlets;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class LastPage {
	static FrameClass frame1;
	static int Height;
	static int Width;
	static highScore HIscore;
	static BufferedImage background;
	int score;

	public class Action {

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LastPage.frame1.setVisible(true);
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

		frame1 = new FrameClass();
		Height = frame1.getHeight();
		Width = frame1.getWidth();
		frame1.getContentPane().setForeground(new Color(0, 0, 205));
		frame1.getContentPane().setFont(new Font("Lucida Grande", Font.BOLD, 50));
		frame1.getContentPane().setBackground(Color.CYAN);
		// frame.setBackground(Color.CYAN);
		frame1.getContentPane().setLayout(null);

		try {
			background = ImageIO.read(new File("InjuredBird.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// High Score
		String HIscore = "";
		try {
			HIscore = highScore.updateHiScore(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel txtHighScore = new JLabel();
		txtHighScore.setText("High Score: " + HIscore);
		txtHighScore.setForeground(Color.MAGENTA);
		txtHighScore.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		txtHighScore.setBounds(Width / 2 - 150, Height / 2 - 200, 380, 50);
		frame1.getContentPane().add(txtHighScore);
		score = Activity.getFinalScore();
		JLabel txtYourScore = new JLabel();
		txtYourScore.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		txtYourScore.setText("Your Score:  " +score);
		txtYourScore.setForeground(Color.YELLOW);
		txtYourScore.setForeground(new Color(0, 0, 128));
		txtYourScore.setBounds(Width / 2 - 150, Height / 2 - 100, 380, 50);
		frame1.getContentPane().add(txtYourScore);
		// txtYourScore.setColumns(10);

		JButton btnNewButton = new JButton("REPLAY");
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		btnNewButton.setBounds(Width / 2 - 60, Height / 2 - 60, 120, 50);
		frame1.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new CustomActionListener());

		JButton btnNewButton_1 = new JButton("EXIT");
		btnNewButton_1.setForeground(Color.BLUE);
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		btnNewButton_1.setBounds(Width / 2 - 60, Height / 2 + 130, 120, 50);
		frame1.getContentPane().add(btnNewButton_1);
		// frame1.setResizable(false);

		btnNewButton_1.addActionListener(new EndButtonAction());

		JLabel imagelabel = new JLabel(new ImageIcon(background));
		imagelabel.setBounds(Width / 2 - 250, Height / 2 - 250, 500, 500);
		frame1.getContentPane().add(imagelabel);
	}

	class CustomActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Start here
			frame1.setVisible(false);
			// System.out.println("hello");
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
		LastPage.frame1.setVisible(true);
	}

}
