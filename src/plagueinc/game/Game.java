package plagueinc.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import plagueinc.PlagueInc;
import plagueinc.Region;
import plagueinc.input.Keyboard;
import plagueinc.input.Mouse;

public class Game
{
	private Image world;
	private ArrayList<Region> regions;
	
	private Disease disease;
	private Cure cure;
	private Popup popup;
	private ArrayList<AirPlane> planes;
	
	private int points;
	
	public Game()
	{
		try
		{
			world = ImageIO.read(Class.class.getResourceAsStream("/world.jpg"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		regions = new ArrayList<>();
		regions.add(new Region("North America", new Rectangle(0, 0, 672, 465), 45358456));
		regions.add(new Region("South America", new Rectangle(400, 450, 199, 315), 6876456));
		regions.add(new Region("Europe", new Rectangle(670, 95, 377, 250), 58778));
		regions.add(new Region("Africa", new Rectangle(650, 340, 360, 320), 54765456));
		regions.add(new Region("Asia", new Rectangle(1040, 85, 540, 400), 1564578643));
		regions.add(new Region("Oceania", new Rectangle(1090, 490, 495, 250), 125337537));
		
		String name = JOptionPane.showInputDialog("Enter disease name: ");
		disease = new Disease(name);
		cure = new Cure(disease);
		
		popup = new Popup(new Point(0, 0), 50);
		planes = new ArrayList<>();
		
		points = 0;
	}
	
	public void tick(float dt)
	{
		popup.tick(dt);
		
		if(Math.random() < 0.2 && !popup.isUp())
		{
			popup.popUp();
			Region region = regions.get((int)(Math.random() * regions.size()));
			popup.setPoint(new Point(region.getRectangle().x + region.getRectangle().width/2, region.getRectangle().y + region.getRectangle().height/2));
		}

		if(Math.random() < 0.01)
		{
			int i = (int)(Math.random() * regions.size());
			int index = (int)(Math.random() * regions.size());
			if(regions.get(i).getPopulation() > 0) planes.add(new AirPlane(new Point(regions.get(i).getRectangle().x + regions.get(i).getRectangle().width/2, regions.get(i).getRectangle().y + regions.get(i).getRectangle().height/2), new Point(regions.get(index).getRectangle().x + regions.get(index).getRectangle().width/2, regions.get(index).getRectangle().y + regions.get(index).getRectangle().height/2), Color.BLUE));
		}
		
		for(int i = 0; i < regions.size(); i++)
		{
			if(Math.random() < ((float)disease.getInfectivity() / (float)10) && regions.get(i).getInfected() > 0)
				regions.get(i).incInfected((int) (regions.get(i).getPopulation()*0.01f));
			if(Math.random() < ((float)disease.getLethality() / (float)10) && regions.get(i).getInfected() > 0)
				regions.get(i).incDead(regions.get(i).getPopulation() > 99 ? (int) (regions.get(i).getPopulation()*0.01f) : 1);
			if(Math.random() < ((float)disease.getInfectivity() / (float)100) && regions.get(i).getInfected() > 0)
			{
				int index = i + (Math.random() < 0.5 ? 1 : -1);
				if(index < 0) index = regions.size()-1;
				if(index >= regions.size()) index = 0;
				regions.get(index).incInfected((int) (regions.get(i).getPopulation()*0.01f));
				planes.add(new AirPlane(new Point(regions.get(i).getRectangle().x + regions.get(i).getRectangle().width/2, regions.get(i).getRectangle().y + regions.get(i).getRectangle().height/2), new Point(regions.get(index).getRectangle().x + regions.get(index).getRectangle().width/2, regions.get(index).getRectangle().y + regions.get(index).getRectangle().height/2), Color.RED));
				cure.start();
			}
			if(regions.get(i).getRectangle().contains(Mouse.getInstance().getX(), Mouse.getInstance().getY()) && Mouse.getInstance().isClicked())
			{
				regions.get(i).incInfected(1);
			}
		}
		
		ArrayList<AirPlane> toRemove = new ArrayList<AirPlane>();
		for(AirPlane plane : planes)
		{
			plane.tick(dt);
			if(plane.isDead()) toRemove.add(plane);
		}
		
		for(AirPlane plane : toRemove)
			 planes.remove(plane);
		
		if(popup.isHit()) points++;
		
		if(Keyboard.getInstance().isKeyHit(KeyEvent.VK_I)) { if(points > 0) { disease.incInfectivity(); points--; }}
		if(Keyboard.getInstance().isKeyHit(KeyEvent.VK_L)) { if(points > 0) { disease.incLethality(); points--; }}
		if(Keyboard.getInstance().isKeyHit(KeyEvent.VK_C)) { if(points > 0) { disease.incCureResistance(); points--; }}
		
		cure.tick();
		
		if(cure.getProgress() >= 100)
			for(Region region : regions) if(Math.random() < 0.2) region.cure();
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, PlagueInc.WIDTH, PlagueInc.HEIGHT);
		
		g.drawImage(world, 0, 0, PlagueInc.WIDTH, PlagueInc.HEIGHT, null);
		for(Region region : regions)
			region.render(g);

		for(AirPlane plane : planes)
			plane.render(g);
		
		g.setColor(Color.RED);
		g.drawString(disease.getName() + " Points: " + points, 20, 20);
		g.drawString("I: " + disease.getInfectivity(), 20, 40);
		g.drawString("L: " + disease.getLethality(), 20, 60);
		g.drawString("C: " + disease.getCureResistance(), 20, 80);

		g.drawString("NA: " + regions.get(0).getDead() + "/" + regions.get(0).getInfected() + "/" + regions.get(0).getPopulation(), 80, 40);
		g.drawString("SA: " + regions.get(1).getDead() + "/" + regions.get(1).getInfected() + "/" + regions.get(1).getPopulation(), 80, 60);
		g.drawString("EU: " + regions.get(2).getDead() + "/" + regions.get(2).getInfected() + "/" + regions.get(2).getPopulation(), 80, 80);
		g.drawString("AF: " + regions.get(3).getDead() + "/" + regions.get(3).getInfected() + "/" + regions.get(3).getPopulation(), 80, 100);
		g.drawString("AS: " + regions.get(4).getDead() + "/" + regions.get(4).getInfected() + "/" + regions.get(4).getPopulation(), 80, 120);
		g.drawString("OC: " + regions.get(5).getDead() + "/" + regions.get(5).getInfected() + "/" + regions.get(5).getPopulation(), 80, 140);

		g.drawString("Cure progress: " + cure.getProgress(), 20, 160);
		
		popup.render(g);
	}
}
