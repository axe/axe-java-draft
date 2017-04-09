package com.axe.fx;

import com.axe.math.Vec;
import com.axe.node.Node;
import com.axe.node.Nodes;

public class Particles<V extends Vec<V>, N extends Node<V>> extends Nodes<V, N> 
{

	public ParticlesTemplate<N> template;
	public ParticleListener<N>[] initializers;
	public ParticleListener<N>[] finalizers;
	
	@Override
	protected void destroyNode(N node)
	{
		template.memory.free( node );
	}
	
}
