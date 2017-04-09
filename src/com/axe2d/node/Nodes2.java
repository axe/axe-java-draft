package com.axe2d.node;

import java.util.Arrays;

import com.axe.math.Vec2f;
import com.axe.node.Node;
import com.axe.node.Nodes;

public class Nodes2<N extends Node<Vec2f>> extends Nodes<Vec2f, N> 
{

	public Nodes2(N ... initial)
	{
		this( DEFAULT_CAPACITY, DEFAULT_INCREASE, DEFAULT_EXPIRE, initial );
	}
	
	public Nodes2(int capacity, N ... initial)
	{
		this( capacity, DEFAULT_INCREASE, DEFAULT_EXPIRE, initial );
	}
	
	public Nodes2(int capacity, boolean expireOnEmpty, N ... initial)
	{
		this( capacity, DEFAULT_INCREASE, expireOnEmpty, initial );
	}
	
	public Nodes2(boolean expireOnEmpty, N ... initial)
	{
		this( DEFAULT_CAPACITY, DEFAULT_INCREASE, expireOnEmpty, initial );
	}
	
	public Nodes2(int capacity, int increase, boolean expireOnEmpty, N ... initial)
	{
		this.nodes = Arrays.copyOf( initial, Math.max( capacity, initial.length ) );
		this.size = initial.length;
		this.increase = increase;
		this.expireOnEmpty = expireOnEmpty;
	}
	
}
