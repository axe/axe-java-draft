package com.axe.io.xml;

import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.axe.io.InputModel;


public class XmlIterator implements Iterator<InputModel>, Iterable<InputModel>
{

	private final XmlDataFormat format;
	private final NodeList nodes;
	private int index = -1;
	
	public XmlIterator(XmlDataFormat format, NodeList nodes) 
	{
		this.format = format;
		this.nodes = nodes;
	}

	@Override
	public Iterator<InputModel> iterator()
	{
		return this;
	}
	
	@Override
	public boolean hasNext()
	{
		return (getNextNode( index ) > index);
	}

	@Override
	public InputModel next()
	{
		return new XmlInputModel( format, (Element)nodes.item( index = getNextNode( index ) ) );
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	private int getNextNode(int i)
	{
		while (++i < nodes.getLength()) 
		{
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) 
			{
				return i;
			}
		}
		return -1;
	}

}