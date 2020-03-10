package plagueinc.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener
{
	private static Keyboard instance;
	
	public static Keyboard getInstance()
	{
		if(instance == null) instance = new Keyboard();
		return instance;
	}
	
	private boolean[] keys;
	
	public Keyboard()
	{
		keys = new boolean[256];
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
	public boolean isKeyDown(int keyCode)
	{
		return keys[keyCode];
	}
	
	public boolean isKeyHit(int keyCode)
	{
		if(keys[keyCode])
		{
			keys[keyCode] = false;
			return true;
		}
		return false;
	}
}
