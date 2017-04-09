package com.axe.loop;

import java.util.concurrent.TimeUnit;

import com.axe.game.GameState;

public class LoopInterpolated implements Loop
{
	public long frameRate;
	public int maxUpdates;
	public boolean yield = false;
	private long time;
	private int draws;
	
	public LoopInterpolated(int maxUpdates, double frameRate)
	{
		this.maxUpdates = maxUpdates;
		this.frameRate = (long)(frameRate * 1000000000L);
	}
	
	public LoopInterpolated(int maxUpdates, long frameRate, TimeUnit timeUnit)
	{
		this.maxUpdates = maxUpdates;
		this.frameRate = timeUnit.toNanos( frameRate );
	}
	
	@Override
	public void onStart( GameState state )
	{
		state.reset();
		state.setElapsed( frameRate );
	}

	@Override
	public void onLoop( GameState state )
	{
		long nanosElapsed = state.tick();
		
		time += nanosElapsed;
		
		state.updates = 0;
		
		while (time >= frameRate && state.updates < maxUpdates)
		{
			state.updates++;
			time -= frameRate;
			draws = 0;
		}
		draws++;
		
		if (yield && draws > 2 && state.updates == 0)
		{
			Thread.yield();	
			draws = 0;
		}
		
		float delta = (float)((double)time / (double)frameRate);
		
		state.interpolation = delta * state.seconds;
		state.reverse = state.seconds - state.interpolation;
	}

}