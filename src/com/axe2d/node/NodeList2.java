package com.axe2d.node;

import java.util.Comparator;

import com.axe.collect.List;
import com.axe.collect.ListArray;
import com.axe.collect.ListLinked;
import com.axe.collect.ListSorted;
import com.axe.math.Vec2f;
import com.axe.node.Node;
import com.axe.node.NodeList;

public class NodeList2<T extends Node<Vec2f>> extends NodeList<Vec2f, T> 
{

	public static <T extends Node<Vec2f>> NodeList2<T> create2(List<T> nodes)
	{
		return new NodeList2<T>( nodes );
	}
	
	public static <T extends Node<Vec2f>> NodeList2<T> linked2()
	{
		return new NodeList2<T>( ListLinked.create() );
	}
	
	public static <T extends Node<Vec2f>> NodeList2<T> array2(T ... nodes)
	{
		return new NodeList2<T>( ListArray.create( nodes ) );
	}
	
	public static <T extends Node<Vec2f>> NodeList2<T> sorted2(Comparator<T> comparator, T ... nodes)
	{
		return new NodeList2<T>( ListSorted.create( comparator, nodes ) );
	}
	
	protected NodeList2(List<T> nodes) 
	{
		super( nodes );
	}

}
