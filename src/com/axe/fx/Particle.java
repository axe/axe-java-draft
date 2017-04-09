package com.axe.fx;

import com.axe.math.Vec;
import com.axe.node.Node;

public interface Particle<V extends Vec<V>> extends Node<V>
{
	
	public void resetParticle();
	
	public <C> C get(int component);
	
	public float getDelta();
	
}
