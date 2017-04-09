package com.axe.fx;

import com.axe.game.GameState;

public interface ParticleModifier<P>
{
	public void modify( P[] particles, int particleCount, Particle particle, GameState state );
}
