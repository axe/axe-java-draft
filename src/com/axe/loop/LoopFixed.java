package com.axe.loop;

import java.util.concurrent.TimeUnit;

import com.axe.game.GameState;

public class LoopFixed implements Loop
{
	public long updateRate;
	public long drawRate;
	public int maxUpdates;
	public boolean sleep = false;
	private long updateTime;
	private long drawTime;
	
	public LoopFixed(int maxUpdates, double updateRate, double drawRate)
	{
		this.maxUpdates = maxUpdates;
		this.updateRate = (long)(updateRate * 1000000000L);
		this.drawRate = (long)(drawRate * 1000000000L);
	}
	
	public LoopFixed(int maxUpdates, long updateRate, long drawRate, TimeUnit timeUnit)
	{
		this.maxUpdates = maxUpdates;
		this.updateRate = timeUnit.toNanos( updateRate );
		this.drawRate = timeUnit.toNanos( drawRate );
	}
	
	@Override
	public void onStart( GameState state )
	{
		state.reset();
		state.setElapsed( updateRate );
	}

	@Override
	public void onLoop( GameState state )
	{
		long nanosElapsed = state.tick();
		
		updateTime += nanosElapsed;
		drawTime += nanosElapsed;

		state.updates = 0;
		state.draw = false;
		
		while (updateTime >= updateRate && state.updates < maxUpdates)
		{
			state.updates++;
			updateTime -= updateRate;
		}
		
		if (drawTime >= drawRate || state.updates > 0)
		{
			state.draw = true;
			drawTime -= drawRate;

			float delta = (float)((double)updateTime / (double)updateRate);
			
			state.interpolation = delta * state.seconds;
			state.reverse = state.seconds - state.interpolation;	
		}	
		
		if (sleep && !state.draw && state.updates == 0)
		{
			long sleep = (updateRate - updateTime) / 1000000L;
			
			if (sleep > 1)
			{
				try { Thread.sleep( sleep - 1 ); } catch (Exception e) { }
			}
		}
	}

}