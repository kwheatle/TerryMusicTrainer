import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Runner extends Canvas implements Runnable, MouseListener {
	/**
	 * 
	 */
	
	static int x, y;
	
	static Clip clip;
	private static final long serialVersionUID = 1L;

	JFrame frame = new JFrame("Music Tester");

	public static void main(String[] args) {
		Runner r = new Runner();
		r.start();
	}

	public void start() {
		Thread game = new Thread(this, "game");
		game.start();
	}
	static String file = "466" + ".164.wav";
	static String folder = "";
	public void randomSound() {
		if (clip != null) {
			clip.close();
		}
		folder = "";
		file = "466" + ".164.wav";
		Random rand = new Random();
		switch (rand.nextInt(3)) {
		case 0:
			folder = "B";
			break;
		case 1:
			String[] flat = { "456", "461", "463", "465" };
			file = flat[rand.nextInt(4)]  + ".164.wav";
			folder = "Flat";
			break;
		case 2:
			String[] sharp = { "467", "469", "471", "476" };
			file = sharp[rand.nextInt(4)]  + ".164.wav";
			folder = "Sharp";
			break;
		default:
			break;
		}
		playSound(folder, file);

	}
	
	String[] notes = {"Flat", "B", "Sharp"};

	public synchronized void playSound(final String url, final String file) {
		try {
			clip = AudioSystem.getClip();
			//AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/MusicTraining/" + url + "/" + file));
			
			InputStream audioSrc = getClass().getResourceAsStream("/MusicTraining/" + url + "/" + file);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	static final int boxsize = 100;
	static final int extendedHeight = 100;
	static String answer = "";
	public void paint(Graphics g) {
		//g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(Color.BLACK);
		for(int i = 0; i < frame.getWidth() / boxsize; i++) {
			for(int j = 0; j < (frame.getHeight() - extendedHeight) / boxsize; j++) {
				g.drawRect(i * boxsize, j*boxsize, boxsize - 1, boxsize - 1);
				g.drawString(notes[i], 33 + (i * boxsize), 50 + (j * boxsize));
			}
		}
		
		g.drawString(answer, 0, frame.getHeight() - (extendedHeight / 2));
	}

	public Runner() {
		requestFocus();
		frame.setPreferredSize(new Dimension(305, 108 + extendedHeight)); // These three lines make sure the canvas stays the same size

		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setLocation(500, 100);
		frame.setResizable(false); // I set it to true because it doesn't make a difference to the gameplay
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.addMouseListener(this);
		addMouseListener(this);
		frame.add(this, BorderLayout.CENTER);
		frame.setVisible(true); // ^ frame stuff
		frame.requestFocus();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x = ((e.getX() / boxsize) % 3);
		y = ((e.getY() / boxsize) % 3);
		
		if(folder.equalsIgnoreCase(notes[x])) {
			isCorrect = true;
		}
		
		if(isCorrect) {
			randomSound();
			answer = "Correct!";
			isCorrect = false;
		} else {
			answer = "Wrong!";
		}
		
		repaint();
		//System.out.println(notes[y][x] + ".164.wav");//*/
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	boolean isCorrect = false;
	@Override
	public void run() {
		long target = System.currentTimeMillis() + 2000;
		requestFocus();
		randomSound();
		while (true) {
			
			System.out.println(folder);//"Current: " + System.currentTimeMillis() + " | Target: " + target);
			if(!answer.equals("")) {
				 if(System.currentTimeMillis() > target) {
					 answer = "";
					 repaint();
				 }
			} else {
				target = System.currentTimeMillis() + 2000;
			}
		}
	}
}
