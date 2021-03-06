package core;

/**
 * The core game loop of Chess Chaos that starts 
 * the display initialization and runs the game on a System timer at 30TPS.
 * 
 * @author Drew Grubb
 */
public class GameLoop
{
	private static Display display;
	private static boolean isRunning;
	
	/** 
	 * Begins program via start function
	 * @param args
	 */
	public static void main(String args[])
	{
		start();
	}
	
	/**
	 * Initiates Game Loop and starts the display
	 * If the game is already running for some reason, returns immediately.
	 */
	private static void start()
	{
		if(isRunning)
			return;
		
		isRunning = true;
		
		display = new Display();
		run();
	}

	/**
	 * Algorithm that works to run the game at 30 Ticks per Second
	 * by using real world time intervals.
	 * 
	 * -Update Game Logic and Objects
	 * -Render Game Objects
	 */
	public static void run()
	{
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 30D;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0; 

		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			while(delta >= 1)
			{
				display.update();
				display.render();
				delta -= 1;
			}
			
			if(System.currentTimeMillis() - lastTimer > 1000)
			{
				lastTimer += 1000;
			}
		}
	}
}
