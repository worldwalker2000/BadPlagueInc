package plagueinc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Region
{
	private String name;
	private Rectangle rectangle;
	private int population;
	private int infected;
	private int dead;
	
	public Region(String name, Rectangle rectangle, int population)
	{
		this.name = name;
		this.rectangle = rectangle;
		this.population = population;
		infected = 0;
		dead = 0;
	}
	
	public void render(Graphics g)
	{
		if(population > 0)
		{
			g.setColor(Color.RED);
			g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
	}

	public void incInfected(int amt)
	{
		if(infected + amt > population)
		{
			infected = population;
			return;
		}
		infected += amt;
	}
	
	public void incDead(int amt)
	{
		dead += amt;
		population -= amt;
		infected -= amt;
	}
	
	public void cure()
	{
		infected = 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Rectangle getRectangle()
	{
		return rectangle;
	}

	public int getPopulation()
	{
		return population;
	}

	public int getInfected()
	{
		return infected;
	}

	public int getDead()
	{
		return dead;
	}
}
