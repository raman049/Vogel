
package servlets;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InitialPage {

	private JLabel lable;
	static FrameClass frame;
	static HighScore hiScore;
	static int Height;
	static int Width;
	static Boolean playClip = true;
	static Clip clip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					InitialPage window = new InitialPage();
					window.frame.setVisible(true);
				clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File("SoundClips/latinHorn.wav")));
					clip.loop(clip.LOOP_CONTINUOUSLY);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public InitialPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new FrameClass();
		Height = frame.getHeight();
		Width = frame.getWidth();
		frame.getContentPane().setForeground(new Color(0, 0, 205));
		frame.getContentPane().setFont(new Font("Lucida Grande", Font.BOLD, 50));
		frame.getContentPane().setBackground(Color.RED);
		// frame.setBackground(Color.RED);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JLabel lblNewLabel = new JLabel("VOGEL");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 150));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(243, 32, 500, 166);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("PLAY");
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.addActionListener(new Action());
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
		btnNewButton.setBounds(Width / 2 - 60, Height / 2 - 10, 120, 50);
		frame.getContentPane().add(btnNewButton);
		// High Score
		String HIscore = "";
		try {
			HIscore = HighScore.updateHiScore(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lable = new JLabel();
		lable.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lable.setText("   Your High Score : " + HIscore);
		lable.setForeground(Color.YELLOW);
		lable.setBounds(Width / 2 - 250, Height / 2 + 155, 380, 50);
		frame.getContentPane().add(lable);
	}

	static class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				clip.close();// close music
				Thread.sleep(500);// pause for 900 msec
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.setVisible(false);
			Activity a = new Activity();
			a.ConnectActivity();
		}

	}
}
