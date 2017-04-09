package com.axe.game;

public class GameState 
{
	
	public float seconds;
	public long millis;
	public long nanos;
	public long micros;
	public long startTime;
	public long lastTime;
	public long currentTime;
	public float interpolation = 0;
	public float reverse = 0;
	public int updates = 1;
	public boolean draw = true;
	
	public long reset()
	{
		return ( startTime = lastTime = currentTime = System.nanoTime() );
	}
	
	public long tick()
	{
		lastTime = currentTime;
		currentTime = System.nanoTime();
		return (currentTime - lastTime);
	}
	
	public void setElapsed(long nanosElapsed)
	{
		nanos = nanosElapsed;
		micros = nanosElapsed / 1000L;
		millis = nanosElapsed / 1000000L;
		seconds = (float)(nanosElapsed * 0.000000001);
	}
	
}