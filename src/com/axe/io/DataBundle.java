package com.axe.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class DataBundle implements DataModel
{

	private DataFormat format;
	private String name;
	private Map<String, DataModel> modelMap = new HashMap<String, DataModel>();
	
	public DataBundle( InputModel input )
	{
		read( input );
	}

	@Override
	public void read( InputModel input )
	{
		format = input.getFormat();
		name = input.getName();
		modelMap.clear();
		
		for (InputModel child : input)
		{
			String name = child.getName();
			String type = child.readString( "data-type" );
			Class<?> clazz = DataRegistry.getClass( type );
			
			DataModel model = instantiate( clazz ); 
			model.read( child );
			
			modelMap.put( name, model );
		}
	}
	
	@Override
	public void write( OutputModel output )
	{
		for ( Entry<String, DataModel> entry : modelMap.entrySet() )
		{
			DataModel model = entry.getValue();
			String name = entry.getKey();
			String type = DataRegistry.getClassName( model.getClass() );
			
			OutputModel out = output.writeModel( name, model );
			out.write( "data-type", type );
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T instantiate( Class<?> clazz ) throws DataException
	{
		try
		{
			return (T)clazz.newInstance();
		}
		catch (Exception ex)
		{
			throw new DataException( ex );
		}
	}
	
	public DataFormat getFormat()
	{
		return format;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void add(String name, DataModel model)
	{
		modelMap.put( name, model );
	}
	
	@SuppressWarnings("unchecked")
	public <T extends DataModel> T get( String name )
	{
		return (T)modelMap.get( name );
	}
	
}
