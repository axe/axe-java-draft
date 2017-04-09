package com.axe3d.node;

import java.util.Arrays;

import com.axe.math.Vec3f;
import com.axe.node.Node;
import com.axe.node.Nodes;

public class Nodes3<N extends Node<Vec3f>> extends Nodes<Vec3f, N> 
{

	public Nodes3(N ... initial)
	{
		this( DEFAULT_CAPACITY, DEFAULT_INCREASE, DEFAULT_EXPIRE, initial );
	}
	
	public Nodes3(int capacity, N ... initial)
	{
		this( capacity, DEFAULT_INCREASE, DEFAULT_EXPIRE, initial );
	}
	
	public Nodes3(int capacity, boolean expireOnEmpty, N ... initial)
	{
		this( capacity, DEFAULT_INCREASE, expireOnEmpty, initial );
	}
	
	public Nodes3(boolean expireOnEmpty, N ... initial)
	{
		this( DEFAULT_CAPACITY, DEFAULT_INCREASE, expireOnEmpty, initial );
	}
	
	public Nodes3(int capacity, int increase, boolean expireOnEmpty, N ... initial)
	{
		this.nodes = Arrays.copyOf( initial, Math.max( capacity, initial.length ) );
		this.size = initial.length;
		this.increase = increase;
		this.expireOnEmpty = expireOnEmpty;
	}
	
}
