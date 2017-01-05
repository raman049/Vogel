package servlets;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class LastPage {
	static FrameClass frame1;
	static int Height;
	static int Width;
	static HighScore HIscore;
	static BufferedImage background;
	int score;
	static Clip clip;

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
		// frame.setBackground(Color.CYAN);
		frame1.getContentPane().setBackground(new Color(0, 102, 255));// background
		frame1.getContentPane().setLayout(null);
		// start background music

		try {
			clip = AudioSystem.getClip();
			URL url = getClass().getClassLoader().getResource("SoundClips/latinHorn.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			background = ImageIO.read(ResourceLoader.load("images/InjuredBird.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// High Score
		String HIscore = "";
		try {
			HIscore = HighScore.updateHiScore(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel txtHighScore = new JLabel();
		txtHighScore.setText("High Score: " + HIscore);
		txtHighScore.setForeground(new Color(255, 255, 0));// High Score Color
		txtHighScore.setFont(new Font("Comic Sans MS", Font.BOLD, Height / 15));
		txtHighScore.setBounds(Width / 2 - 150, Height / 2 - 200, 380, 50);
		frame1.getContentPane().add(txtHighScore);
		score = Activity.getFinalScore();
		JLabel txtYourScore = new JLabel();
		txtYourScore.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		txtYourScore.setText("Your Score:  " + score);
		txtYourScore.setForeground(Color.YELLOW);
		txtYourScore.setForeground(new Color(0, 0, 128));
		txtYourScore.setBounds(Width / 2 - (Width / 7), Height / 4 - (Height / 5), Width / 2, Height / 2);
		frame1.getContentPane().add(txtYourScore);
		// txtYourScore.setColumns(10);

		JButton btnNewButton = new JButton("REPLAY");
		btnNewButton.setForeground(new Color(230, 46, 0));// Replay button +
															// btnNewButton.setForeground(Color.BLUE);
															// Color +
															// btnNewButton.setFont(new
															// Font("Comic Sans
															// MS", Font.BOLD,
															// 24));
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, Height / 24));
		btnNewButton.setBounds(Width / 2 - 60, Height / 2 - 60, 120, 50);
		btnNewButton.setBounds(Width / 2 - (Width / 5), Height / 2 + (Height / 14), Width / 10, Height / 10);
		frame1.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new CustomActionListener());

		JButton btnNewButton_1 = new JButton("EXIT");
		btnNewButton_1.setForeground((new Color(230, 46, 0)));
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		btnNewButton_1.setBounds(Width / 2 + (Width / 10), Height / 2 + (Height / 14), Width / 10, Height / 10);
		frame1.getContentPane().add(btnNewButton_1);
		// frame1.setResizable(false);

		btnNewButton_1.addActionListener(new EndButtonAction());

		JLabel imagelabel = new JLabel(new ImageIcon(background));
		imagelabel.setBounds(Width / 2 - (Width / 4),Height / 5, Width/2, Height);
		frame1.getContentPane().add(imagelabel);
	}

	class CustomActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Start here
			clip.close();
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
			clip.close();
			System.exit(0);
		}

	}

	public void ConnectLastpage() {
		LastPage.frame1.setVisible(true);
	}

}
