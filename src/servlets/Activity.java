package servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Activity implements ActionListener, MouseListener, KeyListener {
	public static Activity activity;
	public ArrayList<Rectangle> jet, cloud, shipArray;
	static int WIDTH, HEIGHT;
	public Render render;
	public Rectangle bird;
	public Random random;
	public int ticks, yMotion, score;
	public boolean gameOver, started, cloudIntersects, tapped;
	FrameClass jframe;
	static int highScore1;
	public BufferedImage plane2Image, cloudImage, birdImage1, birdImage2, birdImageCombine, lightning, wave, shark,
			ship, sun;
	public boolean gameOverApproved, close2water;
	static int count = 0;
	private static int finalScore;
	static Clip clip, clip2, clip3, clip4;

	public void ActivityMethod() {
		jframe = new FrameClass();
		HEIGHT = jframe.getHeight();
		WIDTH = jframe.getWidth();
		Timer timer = new Timer(20, this);
		bird = new Rectangle(jframe.getWidth() / 3, jframe.getHeight() / 2 - 10, 50, 30);
		render = new Render();
		random = new Random();
		jframe.add(render);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setVisible(true);
		jet = new ArrayList<Rectangle>();
		cloud = new ArrayList<Rectangle>();
		shipArray = new ArrayList<Rectangle>();
		jframe.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F2) {
					// close frame one.
				}
			}
		});
		addjet(true);
		addjet(true);
		addCloud(true);
		addCloud(true);
		addShip(true);
		// SoundX.soundIsland(true);
		timer.start();
		// HIGH SCORE
		String hs = "";
		try {
			// HIGH SCORE
			hs = HighScore.updateHiScore(0).toString();
			highScore1 = Integer.valueOf(hs);
			// for background image
			plane2Image = ImageIO.read(ResourceLoader.load("images/plane2.png"));
			cloudImage = ImageIO.read(ResourceLoader.load("images/Cloud.png"));
			lightning = ImageIO.read(ResourceLoader.load("images/lightning.png"));
			shark = ImageIO.read(ResourceLoader.load("images/shark.png"));
			birdImage1 = ImageIO.read(ResourceLoader.load("images/bird1.png"));
			birdImage2 = ImageIO.read(ResourceLoader.load("images/bird2.png"));
			wave = ImageIO.read(ResourceLoader.load("images/wave2.png"));
			ship = ImageIO.read(ResourceLoader.load("images/ship.png"));
			sun = ImageIO.read(ResourceLoader.load("images/sun.png"));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // add background music
		try {
			clip = AudioSystem.getClip();
			URL url = getClass().getClassLoader().getResource("SoundClips/soundIsland.wav");
			clip.open(AudioSystem.getAudioInputStream(url));
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-5);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void addjet(boolean start) {
		int width = 75;
		int height = 30;
		if (start) {
			// position of jets
			jet.add(new Rectangle(WIDTH + random.nextInt(100) + width + jet.size() * 300, 100 + random.nextInt(250),
					width, height));
		} else {
			jet.add(new Rectangle(jet.get(jet.size() - 1).x + 600, 100 + random.nextInt(250), width, height));
		}
	}

	public void addCloud(boolean start) {
		int width = 150;
		int height = 50;
		if (start) {
			// position of cloud
			cloud.add(new Rectangle(random.nextInt(100) + width - cloud.size() * 300, 20 + random.nextInt(HEIGHT /6),
					width, height));
		} else {
			cloud.add(
					new Rectangle(random.nextInt(200) + cloud.get(cloud.size() - 1).x - 600, 1 + random.nextInt(HEIGHT /6), width, height));

		}
	}

	public void addShip(boolean start) {
		int width = 200;
		int height = 100;
		if (start) {
			// position of cloud
			shipArray.add(new Rectangle(random.nextInt(100) - width - shipArray.size() * 600, HEIGHT - 150, width, height));
		} else {
			 shipArray.add(
			 new Rectangle(random.nextInt(100)-shipArray.get(shipArray.size() - 1).x - 600, 1 +
			 random.nextInt(HEIGHT / 16), width, height));

		}
	}

	public void Jet(Graphics g, Rectangle column) {
		g.setColor(Color.black);
		g.fillRect(column.x, column.y, column.width, column.height);
		g.drawImage(plane2Image, column.x - plane2Image.getWidth() / 9 , column.y -plane2Image.getHeight() /10 ,
				plane2Image.getWidth() / 3, plane2Image.getHeight() / 3, null);
	}

	public void Cloud(Graphics g, Rectangle column2) {
		g.setColor(Color.black);
		
		g.drawImage(cloudImage, column2.x - cloudImage.getWidth() / 13, column2.y - cloudImage.getHeight() / 12,
				WIDTH / 5, HEIGHT / 7, null);
		g.fillRect(column2.x, column2.y, column2.width, column2.height);
	}

	public void Ship(Graphics g, Rectangle column3) {
		g.setColor(Color.black);
		g.drawImage(ship, column3.x - ship.getWidth() / 10, column3.y - ship.getHeight() / 6, ship.getWidth() / 2,
				ship.getHeight() / 3, null);
		g.fillRect(column3.x, column3.y, column3.width, column3.height);
	}

	public void Fly() {
		if (gameOver) {
			yMotion = 0;
			score = 0;
			gameOver = false;
		}
		if (!started) {
			started = true;

		} else if (!gameOver) {
			score = 1 + score;
			if (yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= 5;
		}
		if (highScore1 < score) {
			highScore1 = score;
			try {
				HighScore.updateHiScore(score);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		finalScore = score;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// int speed = 5;
		ticks++;
		if (started) {
			// motion of jet
			for (int i = 0; i < jet.size(); i++) {
				Rectangle column = jet.get(i);
				column.x -= 9;
			} // motion of cloud
			for (int i = 0; i < cloud.size(); i++) {
				Rectangle column2 = cloud.get(i);
				column2.x += 4;
			} // motion of ship
			for (int i = 0; i < shipArray.size(); i++) {
				Rectangle column3 = shipArray.get(i);
				column3.x += 4;
			}
			// motion of bird
			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		bird.y += yMotion;

		// condition for game over
		if (bird.y > HEIGHT - 120) {
			close2water = true; // close to water
		}
		// condition for game over
		if (bird.y < HEIGHT / 90) {
			cloudIntersects = true;// touches the sky
		}
		for (Rectangle column : jet) {
			if (bird.intersects(column)) {
				gameOver = true; // touches jets
			}
		}
		for (Rectangle column2 : cloud) {
			if (bird.intersects(column2)) {
				cloudIntersects = true; // touches cloud
			}
		}
		for (Rectangle column3 : shipArray) {
			if (bird.intersects(column3)) {
				gameOver = true; // touches ship
			}
		}
		render.repaint();
	}

	public void Repaint(Graphics g) {
		g.setColor(new Color(153, 204, 255)); // background color
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(new Color(153, 204, 255)); // sky color
		g.fillRect(0, 0, WIDTH, HEIGHT / 6);
		g.drawImage(sun, WIDTH - 105, 60, 50, 50, null); // sun
		g.setColor(Color.white); // text color
		g.setFont(new Font("Arial", 1, 100)); // text property for first page
		// wave color
		g.drawImage(wave, 0, HEIGHT - HEIGHT / 5, wave.getWidth() / 2, wave.getHeight() / 2, null);
		g.drawImage(wave, wave.getWidth() / 2 - 5, HEIGHT - HEIGHT / 5, wave.getWidth() / 2, wave.getHeight() / 2,
				null);
		g.drawImage(wave, wave.getWidth() - 10, HEIGHT - HEIGHT / 5, wave.getWidth() / 2, wave.getHeight() / 2, null);
		g.drawImage(wave, wave.getWidth() - 15 + wave.getWidth() / 2, HEIGHT - HEIGHT / 5, wave.getWidth() / 2,
				wave.getHeight() / 2, null);
		g.drawImage(wave, wave.getWidth() * 2 - 20, HEIGHT - HEIGHT / 5, wave.getWidth() / 2, wave.getHeight() / 2,
				null);
		//add bird
		g.drawImage(birdImageCombine, bird.x, bird.y, birdImage1.getWidth()/4, birdImage1.getHeight()/4, null); // bird image
		
		if (!started) {
			g.setFont(new Font("Comic Sans MS", 1, 20));
			g.setColor(new Color(255, 0, 0));
			g.drawString("High Score: " + String.valueOf(highScore1), WIDTH / 2 - 100, HEIGHT / 5 - 10);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 75));
			g.setColor(new Color(255, 255, 0));
			g.drawString("Tap to Start", WIDTH / 2 - 250, HEIGHT / 2 - 50);
		}

		if (!gameOver && started) {
			addjet(true);
			addCloud(true);
			addShip(true);
			for (Rectangle column : jet) {
				Jet(g, column);
			}
			for (Rectangle column2 : cloud) {
				Cloud(g, column2);
			}
			for (Rectangle column3 : shipArray) {
				
				Ship(g, column3);
			}

			g.setColor(new Color(255, 0, 0));
			g.setFont(new Font("Georgia-Italic", 1, 20)); // text property
			g.drawString("High Score: " + String.valueOf(highScore1), WIDTH / 90, HEIGHT - HEIGHT / 9); // high
			// score
			g.drawString("Score: " + String.valueOf(score), WIDTH - WIDTH / 6, HEIGHT - HEIGHT / 9); // your
			// condition for 4sec pause in beginning // score
			if (!tapped) {
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tapped = true;
			}
		}

		if (cloudIntersects == true) { // draw lightning
			g.drawImage(lightning, bird.x, bird.y - 20, lightning.getWidth() / 3, lightning.getHeight() / 3, null);
			try {
				clip2 = AudioSystem.getClip();
				URL url = getClass().getClassLoader().getResource("SoundClips/soundElectric.wav");
				clip2.open(AudioSystem.getAudioInputStream(url));
				clip2.start();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
		}
		if (close2water == true) { // draw shark
			try {
				clip3 = AudioSystem.getClip();
				URL url = getClass().getClassLoader().getResource("SoundClips/soundBubble.wav");
				clip3.open(AudioSystem.getAudioInputStream(url));
				clip3.start();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(shark, bird.x - shark.getWidth() / 3 + count * 2, bird.y - count * 2, shark.getWidth() / 2,
					shark.getHeight() / 2, null);
			count++;
		}
		if (count == 18) {
			gameOver = true;
			count = 0;
		}
		
	}

	public class Render extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Activity.activity.Repaint(g);
			if (gameOver == true) {
				try {
					clip.close();
					Thread.sleep(1100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jframe.setVisible(false);
				LastPage lp = new LastPage();
				lp.ConnectLastpage();
				gameOverApproved = false;
				// render.notify();

			}
		}

	}

	// public static void main(String arg[]) {
	// activity = new Activity();
	// activity.ConnectActivity();
	//
	// }

	public void ConnectActivity() {
		activity = new Activity();
		activity.ActivityMethod();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Fly();
		birdImageCombine = birdImage1;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		birdImageCombine = birdImage2;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//birdImageCombine = birdImage2;
		try {
			clip4 = AudioSystem.getClip();
			URL url = getClass().getClassLoader().getResource("SoundClips/soundBird.wav");
			clip4.open(AudioSystem.getAudioInputStream(url));
			clip4.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		birdImageCombine = birdImage2;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		birdImageCombine = birdImage2;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Fly();
		}

	}

	public FrameClass getJframe() {
		return jframe;
	}

	public void setJframe(FrameClass jframe) {
		this.jframe = jframe;
	}

	public static int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		Activity.finalScore = finalScore;
	}

}
