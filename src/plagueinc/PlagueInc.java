package plagueinc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import plagueinc.game.Game;
import plagueinc.input.Keyboard;
import plagueinc.input.Mouse;

public class PlagueInc extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	
	private boolean running;
	
	private Image image;
	private Graphics gfx;
	
	private float lastTime;
	private Game game;
	
	public PlagueInc()
	{
	}
	
	public void init()
	{
		image = createImage(WIDTH, HEIGHT);
		gfx = image.getGraphics();
		
		requestFocus();
		addKeyListener(Keyboard.getInstance());
		addMouseListener(Mouse.getInstance());
		addMouseMotionListener(Mouse.getInstance());
		
		game = new Game();
		
		running = true;
	}
	
	public void paint(Graphics g)
	{
		if(running)
		{
			// Tick
			float currentTime = System.nanoTime();
			float delta = currentTime - lastTime;
			lastTime = currentTime;
			game.tick(delta);
			
			// Render
			game.render(gfx);
		}
		
		if(image != null) g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
	}

	public void run()
	{
		init();
		
		while(running)
		{
			repaint();
		}
	}
	
	public static void main(String[] args)
	{
		PlagueInc plagueinc = new PlagueInc();
		
		JFrame frame = new JFrame();
		frame.add(plagueinc);
		frame.pack();
		
		frame.setTitle("Bad Plague Inc.");
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		frame.setSize(size);
		frame.setMinimumSize(size);
		frame.setMaximumSize(size);
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		Thread thread = new Thread(plagueinc);
		thread.run();
	}
}
