package plagueinc.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class AirPlane
{
	private Point position;
	private Point end;
	private int speed;
	private boolean dead;
	private Color color;
	
	public AirPlane(Point start, Point end, Color color)
	{
		position = start;
		this.end = end;
		speed = 1;
		
		this.color = color;
		
		dead = false;
	}
	
	public void tick(float dt)
	{
		if(position.x != end.x || position.y != end.y)
		{
			if(position.x > end.x) position.x -= speed;
			if(position.x < end.x) position.x += speed;
	
			if(position.y > end.y) position.y -= speed;
			if(position.y < end.y) position.y += speed;
		}
		else
		{
			dead = true;
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(color);
		if(!dead) g.fillRect(position.x, position.y, 20, 20);
	}
	
	public boolean isDead()
	{
		 return dead;
	}
}
