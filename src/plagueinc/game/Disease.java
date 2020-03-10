package plagueinc.game;

public class Disease
{
	private String name;
	private int infectivity;
	private int lethality;
	private int cureResistance;
	
	public Disease(String name)
	{
		this.name = name;
		this.infectivity = 1;
		this.lethality = 0;
		this.cureResistance = 0;
	}
	
	public void incInfectivity()
	{
		infectivity++;
	}
	
	public void incLethality()
	{
		lethality++;
	}

	public void incCureResistance()
	{
		cureResistance++;
	}
	
	public String getName()
	{
		return name;
	}

	public int getInfectivity()
	{
		return infectivity;
	}

	public int getLethality()
	{
		return lethality;
	}

	public int getCureResistance()
	{
		return cureResistance;
	}
}
