package plagueinc.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener
{
	private static Mouse instance;
	
	private boolean clicked;
	private int x, y;
	
	public static Mouse getInstance()
	{
		if(instance == null) instance = new Mouse();
		return instance;
	}
	
	public boolean isClicked()
	{
		return clicked;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		clicked = true;
	}

	public void mouseReleased(MouseEvent e)
	{
		clicked = false;
	}

	public void mouseDragged(MouseEvent e)
	{
	}

	public void mouseMoved(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
	}
}
