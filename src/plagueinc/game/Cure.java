package plagueinc.game;

public class Cure
{
	private Disease disease;
	private float progress;
	private boolean started;
	
	public Cure(Disease disease)
	{
		this.disease = disease;
	}
	
	public void start()
	{
		started = true;
	}
	
	public void tick()
	{
		if(started)
		{
			int factor = 3;
			progress += 0.1 - (1 - (1 / (disease.getCureResistance()-factor > 0 ? disease.getCureResistance()-factor : 1)));
		}
		
		if(progress < 0) progress = 0;
		if(progress > 100) progress = 100;
	}
	
	public float getProgress()
	{
		return progress;
	}
}
