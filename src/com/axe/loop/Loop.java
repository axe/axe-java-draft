package com.axe.loop;

import com.axe.game.GameState;

public interface Loop 
{
	
	public void onStart(GameState state);

	public void onLoop(GameState state);

}