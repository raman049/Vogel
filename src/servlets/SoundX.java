package servlets;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

@SuppressWarnings("serial")
public class SoundX extends Applet implements ActionListener {
	Button play, stop;
	AudioClip audioClip;

	public static Clip soundStarting(boolean play) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("SoundClips/latinHorn.wav")));
			if (play == true) {
				// clip.loop(Clip.LOOP_CONTINUOUSLY);
				clip.start();
			} else {
				clip.close();

			}
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
		return null;

	}

	public static void soundBird(boolean play) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("SoundClips/soundBird.wav")));
			// clip.loop(0);
			if (play == true) {
				// clip.getFrameLength();
				clip.setLoopPoints(15, 16);
				clip.start();
			} else {
				clip.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public static void soundBubble(boolean play) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("SoundClips/soundBubble.wav")));
			// clip.loop(0);
			if (play == true) {
				// clip.loop(9);
				clip.setLoopPoints(100, 110);
				FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(5);
				clip.start();
			} else {
				clip.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public static void soundElectric(boolean play) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("SoundClips/soundElectric.wav")));
			clip.loop(0);
			if (play == true) {
				clip.start();
			} else {
				clip.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public static void soundIsland(boolean play) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("SoundClips/soundIsland.wav")));
			clip.loop(0);
			if (play == true) {
				FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(-5);
				clip.start();
			} else {
				clip.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public static void main(String args[]) {
		new SoundX();
		// SoundX.soundStarting(true);
		SoundX.soundStarting(true);
		// SoundX.soundStarting(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}