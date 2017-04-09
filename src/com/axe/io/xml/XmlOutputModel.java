package com.axe.io.xml;


import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.axe.io.OutputModel;
import com.axe.io.base.BaseOutputModel;


public class XmlOutputModel extends BaseOutputModel<XmlDataFormat>
{
	
	private final Document document;
	private final Element e;
	
	public XmlOutputModel( XmlDataFormat format, int scope, Document document, String rootName )
	{
		this( format, scope, document, (Element)document.appendChild( document.createElement( rootName ) ) );
	}
	
	public XmlOutputModel( XmlDataFormat format, int scope, Element e )
	{
		this( format, scope, e.getOwnerDocument(), e );
	}
	
	public XmlOutputModel( XmlDataFormat format, int scope, Document document, Element parent, String tagName )
	{
		this( format, scope, document, document.createElement( tagName ) );
		
		parent.appendChild( e );
	}
	
	private XmlOutputModel( XmlDataFormat format, int scope, Document document, Element e )
	{
		super( format, e.getNodeName(), scope );
		
		this.document = document;
		this.e = e;
	}
	
	@Override
	public OutputModel writeModel( String name )
	{
		return new XmlOutputModel( format, scope, document, e, name );
	}
	
	@Override
	public void write( String name, Object value )
	{
		if ( value != null )
		{
			e.setAttribute( name, value.toString() );
		}
	}
	
	@Override
	public void output( OutputStream stream ) throws Exception
	{
		format.write( e, stream );
	}
	
	@Override
	public void output( Writer writer ) throws Exception
	{
		format.write( e, writer );
	}
	
	@Override
	public void output( File file ) throws Exception
	{
		format.write( e, file );
	}
	
}