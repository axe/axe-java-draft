package com.axe.io.base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.axe.io.DataFormat;
import com.axe.io.DataModel;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;



public abstract class BaseDataFormat implements DataFormat
{
	
	public static final int DEFAULT_SCOPE = 0;
	
	@Override
	public OutputModel newOutput( String name, int scope, DataModel model )
	{
		OutputModel output = newOutput( name, scope );
		model.write( output );
		return output;
	}
	
	@Override
	public OutputModel newOutput( String name, DataModel model )
	{
		OutputModel output = newOutput( name, DEFAULT_SCOPE );
		model.write( output );
		return output;
	}
	
	@Override
	public OutputModel newOutput( String name )
	{
		return newOutput( name, DEFAULT_SCOPE );
	}
	
	@Override
	public InputModel read( final Reader reader ) throws Exception
	{
		return read( new InputStream()
		{
			@Override
			public int read() throws IOException
			{
				return reader.read();
			}
		});
	}
	
	@Override
	public InputModel read( File file ) throws Exception
	{
		return read( new FileInputStream( file ) );
	}
	
	@Override
	public InputModel read( String string ) throws Exception
	{
		return read( new ByteArrayInputStream( string.getBytes() ) );
	}
	
	@Override
	public <T extends DataModel> T read( InputStream stream, T model ) throws Exception
	{
		model.read( read( stream ) );
		return model;
	}
	
	@Override
	public <T extends DataModel> T  read( Reader reader, T model ) throws Exception
	{
		model.read( read( reader ) );
		return model;
	}

	@Override
	public <T extends DataModel> T  read( File file, T model ) throws Exception
	{
		model.read( read( file ) );
		return model;
	}

	@Override
	public <T extends DataModel> T  read( String string, T model ) throws Exception
	{
		model.read( read( string ) );
		return model;
	}
	
	@Override
	public OutputModel write( Writer writer, String name, DataModel model ) throws Exception
	{
		OutputModel output = newOutput( name );
		
		model.write( output );
		
		output.output( writer );
		
		return output;
	}

	@Override
	public OutputModel write( File file, String name, DataModel model ) throws Exception
	{
		OutputModel output = newOutput( name );
		
		model.write( output );
		
		output.output( file );
		
		return output;
	}
	
	@Override
	public OutputModel write( OutputStream stream, String name, DataModel model ) throws Exception
	{
		OutputModel output = newOutput( name );
		
		model.write( output );
		
		output.output( stream );
		
		return output;
	}
	
}
