package com.axe.node;

import com.axe.math.Vec;

public abstract class AbstractNode<V extends Vec<V>> implements Node<V> 
{
	
	public Node<V> parent;

	@Override
	public Node<V> getParent() 
	{
		return parent;
	}

	@Override
	public void setParent(Node<V> parent) 
	{
		this.parent = parent;
	}

}
