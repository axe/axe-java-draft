
package com.axe.io;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;


public interface DataFormat
{

	public OutputModel newOutput( String name );

	public OutputModel newOutput( String name, DataModel model );
	
	public OutputModel newOutput( String name, int scope );

	public OutputModel newOutput( String name, int scope, DataModel model );

	public OutputModel write( Writer writer, String name, DataModel model ) throws Exception;

	public OutputModel write( File file, String name, DataModel model ) throws Exception;

	public OutputModel write( OutputStream stream, String name, DataModel model ) throws Exception;
	
	public InputModel read( InputStream stream ) throws Exception;

	public InputModel read( Reader reader ) throws Exception;

	public InputModel read( File file ) throws Exception;

	public InputModel read( String string ) throws Exception;

	public <T extends DataModel> T read( InputStream stream, T model ) throws Exception;

	public <T extends DataModel> T  read( Reader reader, T model ) throws Exception;

	public <T extends DataModel> T  read( File file, T model ) throws Exception;

	public <T extends DataModel> T  read( String string, T model ) throws Exception;

}
