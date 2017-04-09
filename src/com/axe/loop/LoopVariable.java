package com.axe.loop;

import java.util.concurrent.TimeUnit;

import com.axe.game.GameState;

public class LoopVariable implements Loop
{
	public long maximumElapsed;

	public LoopVariable(double maximumElapsedSeconds)
	{
		this.maximumElapsed = (long)(maximumElapsedSeconds * 1000000000L);
	}
	
	public LoopVariable(long maximumElapsed, TimeUnit timeUnit)
	{
		this.maximumElapsed = timeUnit.toNanos( maximumElapsed );
	}
	
	@Override
	public void onStart( GameState state )
	{
		state.reset();
	}

	@Override
	public void onLoop( GameState state )
	{
		state.setElapsed( Math.min( maximumElapsed, state.tick() ) );
	}

}