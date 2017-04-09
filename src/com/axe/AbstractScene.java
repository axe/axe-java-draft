package com.axe;

import com.axe.collect.ListItem;
import com.axe.math.Vec;
import com.axe.node.Node;

public class AbstractScene<V extends Vec<V>> implements Scene<V> 
{

	public ListItem listItem;
	public Object data;
	public Node<V> root;
	public View<V> view;
	
	@Override
	public void setListItem(ListItem listItem) 
	{
		this.listItem = listItem;
	}

	@Override
	public <T> T getData() 
	{
		return (T) data;
	}

	@Override
	public void setData(Object data) 
	{
		this.data = data;
	}

	@Override
	public Node<V> getRoot() 
	{
		return root;
	}

	@Override
	public void setRoot(Node<V> root) 
	{
		this.root = root;
	}

	@Override
	public View<V> getView() 
	{
		return view;
	}

	@Override
	public void setView(View<V> view) 
	{
		this.view = view;
	}

}
