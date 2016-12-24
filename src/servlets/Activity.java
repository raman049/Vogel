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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Activity implements ActionListener, MouseListener, KeyListener {
	public static Activity activity;
	public ArrayList<Rectangle> jet, cloud;
	static int WIDTH, HEIGHT;
	public Render render;
	public Rectangle bird;
	public Random random;
	public int ticks, yMotion, score;
	public boolean gameOver, started, cloudIntersects, tapped;
	FrameClass jframe;
	static int highScore1;
	public BufferedImage plane2Image, cloudImage, birdImage1, birdImage2, birdImageCombine, lightning, wave, shark;
	public boolean gameOverApproved, close2water;
	static int count = 0;
	private static int finalScore;

	public void ActivityMethod() {
		jframe = new FrameClass();
		HEIGHT = jframe.getHeight();
		WIDTH = jframe.getWidth();
		Timer timer = new Timer(20, this);
		bird = new Rectangle(jframe.getWidth() / 2 - 10, jframe.getHeight() / 2 - 10, 50, 30);
		render = new Render();
		random = new Random();
		jframe.add(render);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setVisible(true);
		jet = new ArrayList<Rectangle>();
		cloud = new ArrayList<Rectangle>();
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
		timer.start();
		// HIGH SCORE
		String hs = "";
		try {
			// HIGH SCORE
			hs = highScore.updateHiScore(0).toString();
			highScore1 = Integer.valueOf(hs);
			// for background image
			plane2Image = ImageIO.read(new File("plane2.png"));
			cloudImage = ImageIO.read(new File("Cloud.png"));
			lightning = ImageIO.read(new File("lightning.png"));
			shark = ImageIO.read(new File("shark.png"));
			birdImage1 = ImageIO.read(new File("bird1_sm.png"));
			birdImage2 = ImageIO.read(new File("bird2_sm.png"));
			wave = ImageIO.read(new File("wave.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void addjet(boolean start) {
		int width = 75;
		int height = 30;
		if (start) {
			// position of jets
			jet.add(new Rectangle(WIDTH + random.nextInt(100) + width + jet.size() * 300, 30 + random.nextInt(250),
					width, height));
		} else {
			jet.add(new Rectangle(jet.get(jet.size() - 1).x + 600, HEIGHT - height - 120, width, height));
		}
	}

	public void addCloud(boolean start) {
		int width = 75;
		int height = 30;
		if (start) {
			// position of cloud
			cloud.add(new Rectangle(random.nextInt(100) + width - cloud.size() * 300, 20 + random.nextInt(HEIGHT / 16),
					width, height));
		} else {
			cloud.add(
					new Rectangle(cloud.get(cloud.size() - 1).x - 600, 1 + random.nextInt(HEIGHT / 16), width, height));

		}
	}

	public void Jet(Graphics g, Rectangle column) {
		g.setColor(Color.black);
		// g.fillRect(column.x, column.y, column.width, column.height);
		g.drawImage(plane2Image, column.x - plane2Image.getWidth() / 6, column.y - plane2Image.getHeight() / 6,
				plane2Image.getWidth() / 2, plane2Image.getHeight() / 2, null);
	}

	public void Cloud(Graphics g, Rectangle column2) {
		g.setColor(Color.black);
		g.fillRect(column2.x, column2.y, column2.width, column2.height);
		g.drawImage(cloudImage, column2.x - cloudImage.getWidth() / 6, column2.y - cloudImage.getHeight() / 6,
				cloudImage.getWidth() / 2, cloudImage.getHeight() / 3, null);
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
				highScore.updateHiScore(score);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		finalScore = score;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = 5;
		ticks++;
		if (started) {
			// motion of jet
			for (int i = 0; i < jet.size(); i++) {
				Rectangle column = jet.get(i);
				column.x -= speed;
			} // motion of cloud
			for (int i = 0; i < cloud.size(); i++) {
				Rectangle column2 = cloud.get(i);
				column2.x += speed;
			} // motion of bird
			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += 1;
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
		render.repaint();
	}

	public void Repaint(Graphics g) {
		g.setColor(new Color(153,204,255)); // background color
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(new Color(0, 220, 255)); // sky color
		g.fillRect(0, 0, WIDTH, HEIGHT / 6);
		// wave color
		g.drawImage(wave, 0, HEIGHT - 120, wave.getWidth(), wave.getHeight() + 30, null);
		g.drawImage(wave, wave.getWidth(), HEIGHT - 120, wave.getWidth(), wave.getHeight() + 30, null);
		g.drawImage(wave, wave.getWidth() * 2, HEIGHT - 120, wave.getWidth(), wave.getHeight() + 30, null);
		g.drawImage(wave, wave.getWidth() * 3, HEIGHT - 120, wave.getWidth(), wave.getHeight() + 30, null);
		g.drawImage(wave, wave.getWidth() * 4, HEIGHT - 120, wave.getWidth(), wave.getHeight() + 30, null);
		g.setColor(Color.white); // text color
		g.setFont(new Font("Arial", 1, 100)); // text property for first page
		if (!started) {
			g.setFont(new Font("Comic Sans MS", 1, 25));
			g.setColor(new Color(255, 0, 0));
			g.drawString("High Score: " + String.valueOf(highScore1), WIDTH / 2 - 100, HEIGHT / 10);
			g.setFont(new Font("Comic Sans MS", 2, 100));
			g.setColor(new Color(255, 255, 0));
			g.drawString("Tap to Start", WIDTH / 2 - 275, HEIGHT / 2 - 50);
		}

		if (!gameOver && started) {
			addjet(true);
			addCloud(true);
			for (Rectangle column : jet) {
				Jet(g, column);
			}
			for (Rectangle column2 : cloud) {
				Cloud(g, column2);
			}
			g.setColor(new Color(255, 0, 0));
			g.setFont(new Font("Arial", 1, 25)); // text property
			g.drawString("High Score: " + String.valueOf(highScore1), WIDTH / 90, HEIGHT - HEIGHT / 9); // high
			// score
			g.drawString("Score:  " + String.valueOf(score), WIDTH - WIDTH / 6, HEIGHT - HEIGHT / 9); // your
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
		// g.drawImage(lightning, bird.x, bird.y - 20, null);

		if (cloudIntersects == true) { // draw lightning
			g.drawImage(lightning, bird.x, bird.y - 20, lightning.getWidth() / 3, lightning.getHeight() / 3, null);
			count++;
		}
		if (close2water == true) { // draw shark
			g.drawImage(shark, bird.x - shark.getWidth() / 3 + count * 2, bird.y - count * 2, shark.getWidth() / 2,
					shark.getHeight() / 2, null);
			count++;
		}
		if (count == 18) {
			gameOver = true;
			count = 0;
		}
		g.drawImage(birdImageCombine, bird.x, bird.y, null); // bird image
	}

	public class Render extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Activity.activity.Repaint(g);
			if (gameOver == true) {
				try {
					new SoundX().sound1();
					Thread.sleep(300);
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
		birdImageCombine = birdImage2;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
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
