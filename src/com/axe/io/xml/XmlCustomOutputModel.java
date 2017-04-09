package com.axe.io.xml;


import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.axe.io.OutputModel;
import com.axe.io.base.BaseOutputModel;


public class XmlCustomOutputModel extends BaseOutputModel<XmlDataFormat>
{
	
	private static final int[] ENTITIES = {
	        '<', '&', '"', '\n', '\r'
	};
	private static final boolean[] ENTITY_TABLE = new boolean[128];
	
	static
	{
		for ( int i = 0; i < ENTITIES.length; i++ )
		{
			ENTITY_TABLE[ENTITIES[i]] = true;
		}
	}
	
	private final int indent;
	private final int depth;
	private final StringBuilder attributes;
	private final ArrayList<XmlCustomOutputModel> children;
	
	public XmlCustomOutputModel( String name, int scope, int depth, int indent )
	{
		super( XmlDataFormat.get(), name, scope );
		
		this.depth = depth;
		this.indent = indent;
		this.attributes = new StringBuilder();
		this.children = new ArrayList<XmlCustomOutputModel>();
	}
	
	private XmlCustomOutputModel( String name, int scope, XmlCustomOutputModel parent )
	{
		super( XmlDataFormat.get(), name, scope );
		
		this.depth = parent.depth + 1;
		this.indent = parent.indent;
		this.attributes = new StringBuilder();
		this.children = new ArrayList<XmlCustomOutputModel>();
		
		parent.children.add( this );
	}
	
	@Override
	public OutputModel writeModel( String name )
	{
		return new XmlCustomOutputModel( name, scope, this );
	}
	
	@Override
	public void write( String name, Object value )
	{
		if ( value != null )
		{
			attributes.append( ' ' );
			attributes.append( name );
			attributes.append( '=' );
			attributes.append( '"' );
			
			char[] chars = value.toString().toCharArray();
			
			for ( int k = 0; k < chars.length; k++ )
			{
				char c = chars[k];
				
				if ( ENTITY_TABLE[c] )
				{
					attributes.append( "&#" );
					attributes.append( (int)c );
					attributes.append( ";" );
				}
				else
				{
					attributes.append( c );
				}
			}
			
			attributes.append( '"' );
		}
	}
	
	@Override
	public void output( Writer writer ) throws Exception
	{
		if ( depth == 0 )
		{
			writer.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" );
		}
		
		writeIndent( writer, depth * indent );
		writer.append( "<" );
		writer.append( name );
		writer.append( attributes );
		
		if ( children.size() == 0 )
		{
			writer.append( " />\n" );
		}
		else
		{
			writer.append( ">\n" );
			
			for ( XmlCustomOutputModel child : children )
			{
				child.output( writer );
			}
			
			writeIndent( writer, depth * indent );
			writer.append( "</" );
			writer.append( name );
			writer.append( ">\n" );
		}
	}
	
	private void writeIndent( Writer writer, int spaces ) throws Exception
	{
		while ( --spaces >= 0 )
		{
			writer.append( ' ' );
		}
	}
	
	@Override
	public void output( File file ) throws Exception
	{
		Writer writer = new FileWriter( file );
		
		try
		{
			output( writer );
		}
		finally
		{
			writer.close();
		}
	}
	
	@Override
	public void output( OutputStream stream ) throws Exception
	{
		Writer writer = new OutputStreamWriter( stream );
		
		try
		{
			output( writer );
		}
		finally
		{
			writer.close();
		}
	}
	
}