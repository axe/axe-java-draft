package com.axe3d.node;

import java.util.Comparator;

import com.axe.collect.List;
import com.axe.collect.ListArray;
import com.axe.collect.ListLinked;
import com.axe.collect.ListSorted;
import com.axe.math.Vec3f;
import com.axe.node.Node;
import com.axe.node.NodeList;

public class NodeList3<T extends Node<Vec3f>> extends NodeList<Vec3f, T> 
{

	public static <T extends Node<Vec3f>> NodeList3<T> create3(List<T> nodes)
	{
		return new NodeList3<T>( nodes );
	}
	
	public static <T extends Node<Vec3f>> NodeList3<T> linked3()
	{
		return new NodeList3<T>( ListLinked.create() );
	}
	
	public static <T extends Node<Vec3f>> NodeList3<T> array3(T ... nodes)
	{
		return new NodeList3<T>( ListArray.create( nodes ) );
	}
	
	public static <T extends Node<Vec3f>> NodeList3<T> sorted3(Comparator<T> comparator, T ... nodes)
	{
		return new NodeList3<T>( ListSorted.create( comparator, nodes ) );
	}
	
	protected NodeList3(List<T> nodes) 
	{
		super( nodes );
	}

}
