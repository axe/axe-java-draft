package com.axe.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * YOU SHALL NOT CLOSE!!!
 * 
 * @author Philip Diffenderfer
 *
 */
public class OpenStream extends BufferedInputStream
{
	
	public OpenStream( InputStream source )
	{
		super( source );
	}
	
	@Override
	public void close() throws IOException
	{
		// nothing
	}
	
}
