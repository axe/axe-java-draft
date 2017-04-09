package com.axe.integrate;

import com.axe.core.Attribute;
import com.axe.game.GameState;

public interface Integrator<T extends Attribute<T>>
{
	
	public T position();
	
	public T acceleration();
	
	public void update(GameState state);
	
}
