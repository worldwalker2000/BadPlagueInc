package plagueinc.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import plagueinc.input.Mouse;

public class Popup
{
	private Point point;
	private int radius;
	
	private boolean up;
	private boolean hit;
	
	public Popup(Point point, int radius)
	{
		this.point = point;
		this.radius = radius;
		
		up = hit = false;
	}

	public void tick(float dt)
	{
		hit = false;
		
		float mx = Mouse.getInstance().getX();
		float my = Mouse.getInstance().getY();
		float dist = (float) Math.sqrt(Math.pow(mx-point.x, 2) + Math.pow(my-point.y, 2));
		
		if(dist <= radius && up && Mouse.getInstance().isClicked())
		{
			up = false;
			hit = true;
		}
	}
	
	public void render(Graphics g)
	{
		if(up)
		{
			g.setColor(Color.GREEN);
			int xx = point.x-(radius/2);
			int yy = point.y-(radius/2);
			g.fillOval(xx, yy, radius, radius);
		}
	}
	
	public void popUp()
	{
		up = true;
	}
	
	public boolean isUp()
	{
		return up;
	}
	
	public boolean isHit()
	{
		return hit;
	}
	
	public Point getPoint()
	{
		return point;
	}

	public int getRadius()
	{
		return radius;
	}
	
	public void setPoint(Point point)
	{
		this.point = point;
	}
}
