package servlets;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import servlets.InitialPage.Action;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InitialPage {


	private JLabel lable;
	static FrameClass frame;
	static highScore hiScore;
	static int Height;
	static int Width;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitialPage window = new InitialPage();
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
	public InitialPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new FrameClass();
		Height = frame.getHeight();
		Width =frame.getWidth();
		frame.getContentPane().setForeground(new Color(0, 0, 205));
		frame.getContentPane().setFont(new Font("Lucida Grande", Font.BOLD, 50));
		frame.getContentPane().setBackground(Color.RED);
		//frame.setBackground(Color.RED);
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
		btnNewButton.setBounds(Width/2-60, Height/2-10, 120, 50);
		frame.getContentPane().add(btnNewButton);
		
		lable = new JLabel();
		lable.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lable.setText("   Your High Score : ");
		lable.setForeground(Color.YELLOW);
		lable.setBounds(Width/2-250, Height/2+155, 380, 50);
		frame.getContentPane().add(lable);
		//lable.setColumns(10);
		//frame.setBounds(100,100,Width,Height);
	}
static class Action implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("hello");
			frame.setVisible(false);
			Activity a = new Activity();
			a.ConnectActivity();
		}

	}
}
