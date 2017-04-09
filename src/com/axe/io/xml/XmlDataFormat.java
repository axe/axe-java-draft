package com.axe.io.xml;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.axe.io.InputModel;
import com.axe.io.OpenStream;
import com.axe.io.OutputModel;
import com.axe.io.base.BaseDataFormat;


public class XmlDataFormat extends BaseDataFormat
{
	
	private static final XmlDataFormat instance = new XmlDataFormat();
	
	public static XmlDataFormat get()
	{
		return instance;
	}
	
	private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private static ThreadLocal<DocumentBuilder> documentBuilderLocal = new ThreadLocal<DocumentBuilder>();
	private static Transformer transformer;
	private static Charset encoding;
	
	static
	{
		try
		{
			transformerFactory.setAttribute( "indent-number", 2 );
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
			encoding = Charset.forName( "utf-8" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public OutputModel newOutput( String name, int scope )
	{
		return new XmlOutputModel( this, scope, getDocumentBuilder().newDocument(), name );
	}
	
	@Override
	public InputModel read( InputStream stream ) throws Exception
	{
		return read( new InputSource( new OpenStream( stream ) ) );
	}
	
	@Override
	public InputModel read( Reader reader ) throws Exception
	{
		return read( new InputSource( reader ) );
	}
	
	@Override
	public InputModel read( File file ) throws Exception
	{
		return read( new InputSource( new OpenStream( new FileInputStream( file ) ) ) );
	}
	
	@Override
	public InputModel read( String string ) throws Exception
	{
		return read( new InputSource( new StringReader( string ) ) );
	}
	
	private InputModel read( InputSource source ) throws Exception
	{
		Document doc = getDocumentBuilder().parse( source );
		
		return new XmlInputModel( this, doc.getDocumentElement() );
	}
	
	private DocumentBuilder getDocumentBuilder()
	{
		DocumentBuilder docBuilder = documentBuilderLocal.get();
		
		if (docBuilder == null)
		{
			try
			{
				docBuilder = documentBuilderFactory.newDocumentBuilder();
				
				documentBuilderLocal.set( docBuilder );
			}
			catch (ParserConfigurationException e)
			{
				throw new RuntimeException( e );
			}
		}
		
		return docBuilder;
	}
	
	protected String write( Node node ) throws Exception
	{
		StringWriter writer = new StringWriter();
		write( node, writer );
		return writer.toString();
	}
	
	protected void write( Node node, Writer writer ) throws Exception
	{
		final Source source = new DOMSource( node );
		final Result result = new StreamResult( writer );
		
		transformer.transform( source, result );
	}
	
	protected void write( Node node, OutputStream stream ) throws Exception
	{
		final Source source = new DOMSource( node );
		final Writer writer = new OutputStreamWriter( stream, encoding );
		final Result result = new StreamResult( writer );
		
		transformer.transform( source, result );
	}
	
	protected void write( Node node, File file ) throws Exception
	{
		final Source source = new DOMSource( node );
		final OutputStream stream = new FileOutputStream( file );
		final Writer writer = new OutputStreamWriter( stream, encoding );
		final Result result = new StreamResult( writer );
		
		transformer.transform( source, result );
	}
	
}