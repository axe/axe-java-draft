
package com.axe.io.xml;

import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.io.base.BaseInputModel;


public class XmlInputModel extends BaseInputModel<XmlDataFormat>
{

	private final Element e;
	
	public XmlInputModel( XmlDataFormat format, Element e )
	{
		super( format, e.getTagName() );

		this.e = e;
	}

	@Override
	public void copyTo( OutputModel out )
	{		
		NamedNodeMap map = e.getAttributes();
		
		for ( int i = 0; i < map.getLength(); i++ )
		{
			Node node = map.item( i );
			
			out.write( node.getNodeName(), node.getNodeValue() );
		}
		
		for (InputModel child : this)
		{
			OutputModel childModel = out.writeModel( child.getName() );
			
			child.copyTo( childModel );
		}
	}
	
	@Override
	public Iterator<InputModel> iterator()
	{
		return new XmlIterator( format, e.getChildNodes() );
	}

	@Override
	public boolean hasAttribute( String name )
	{
		return e.hasAttribute( name );
	}

	@Override
	protected String getAttribute( String name )
	{
		return e.getAttribute( name );
	}

	@Override
	public boolean hasChild( String name )
	{
		return e.getElementsByTagName( name ).getLength() > 0;
	}
	
}
