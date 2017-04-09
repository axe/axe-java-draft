package com.axe.io.base;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.magnos.asset.Assets;

import com.axe.io.DataException;
import com.axe.io.DataFormat;
import com.axe.io.DataModel;
import com.axe.io.DataRegistry;
import com.axe.io.DualInputModel;
import com.axe.io.InputModel;
import com.axe.io.InputModelListener;
import com.axe.math.calc.Calculator;


public abstract class BaseInputModel<F extends DataFormat> implements InputModel
{
	
	public static String IMPORT_ATTRIBUTE = "import"; 
	
	public static final int DEFAULT_RADIX = 10;
	
	private static final Set<String> trues = new HashSet<String>(
	        Arrays.asList( "1", "t", "true", "y", "ya", "yes", "yessums" )
	        );
	
	private static final Set<String> falses = new HashSet<String>(
	        Arrays.asList( "0", "f", "false", "n", "no", "nope" )
	        );
	
	protected final String name;
	protected final F format;
	
	public BaseInputModel( F format, String name )
	{
		this.format = format;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public DataFormat getFormat()
	{
		return format;
	}
	
	@Override
	public double readDouble( String name )
	{
		return readDouble( name, null );
	}
	
	protected abstract String getAttribute( String name );
	
	@Override
	public double readDouble( String name, Double defaultValue )
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				return Double.parseDouble( getAttribute( name ) );
			}
			catch ( NumberFormatException ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, double type expected", name, getName() );
			}
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public float readFloat( String name )
	{
		return readFloat( name, null );
	}
	
	@Override
	public float readFloat( String name, Float defaultValue )
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				return Float.parseFloat( getAttribute( name ) );
			}
			catch ( NumberFormatException ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, float type expected", name, getName() );
			}
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public byte readByte( String name )
	{
		return readByte( name, null );
	}
	
	@Override
	public byte readByte( String name, Byte defaultValue )
	{
		return readByte( name, defaultValue, DEFAULT_RADIX );
	}
	
	@Override
	public byte readByte( String name, Byte defaultValue, int radix )
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				return Byte.parseByte( getAttribute( name ), radix );
			}
			catch ( NumberFormatException ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, byte type expected", name, getName() );
			}
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public short readShort( String name )
	{
		return readShort( name, null );
	}
	
	@Override
	public short readShort( String name, Short defaultValue )
	{
		return readShort( name, defaultValue, DEFAULT_RADIX );
	}
	
	@Override
	public short readShort( String name, Short defaultValue, int radix )
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				return Short.parseShort( getAttribute( name ), radix );
			}
			catch ( NumberFormatException ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, short type expected", name, getName() );
			}
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public int readInt( String name )
	{
		return readInt( name, null );
	}
	
	@Override
	public int readInt( String name, Integer defaultValue )
	{
		return readInt( name, defaultValue, DEFAULT_RADIX );
	}
	
	@Override
	public int readInt( String name, Integer defaultValue, int radix )
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				return Integer.parseInt( getAttribute( name ), radix );
			}
			catch ( NumberFormatException ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, int type expected", name, getName() );
			}
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public long readLong(String name) throws DataException
	{
		return readLong( name, null );
	}
	
	@Override
	public long readLong(String name, Long defaultValue) throws DataException
	{
		return readLong( name, defaultValue, DEFAULT_RADIX );
	}
	
	@Override
	public long readLong(String name, Long defaultValue, int radix) throws DataException
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				return Long.parseLong( getAttribute( name ), radix );
			}
			catch ( NumberFormatException ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, long type expected", name, getName() );
			}
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public boolean readBoolean( String name )
	{
		return readBoolean( name, null );
	}
	
	@Override
	public boolean readBoolean( String name, Boolean defaultValue )
	{
		if ( hasAttribute( name ) )
		{
			String value = getAttribute( name ).toLowerCase();
			
			if ( trues.contains( value ) )
			{
				return true;
			}
			
			if ( falses.contains( value ) )
			{
				return false;
			}
			
			throw new DataException( "Invalid attribute %s in input %s, boolean type expected", name, getName() );
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public char readChar( String name )
	{
		return readChar( name, null );
	}
	
	@Override
	public char readChar( String name, Character defaultValue )
	{
		if ( hasAttribute( name ) )
		{
			String value = getAttribute( name );
			
			if ( value.length() > 1 )
			{
				throw new DataException( "Invalid attribute %s in input %s, char type expected", name, getName() );
			}
			
			return value.charAt( 0 );
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public String readString( String name )
	{
		return readString( name, null );
	}
	
	@Override
	public String readString( String name, String defaultValue )
	{
		if ( hasAttribute( name ) )
		{
			return getAttribute( name );
		}
		else if ( defaultValue == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultValue;
	}
	
	@Override
	public String readNullableString( String name )
	{
		return (hasAttribute( name ) ? getAttribute( name ) : null);
	}
	
	@Override
	public <T> Class<T> readClass( String name )
	{
		return readClass( name, null );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<T> readClass( String name, Class<T> defaultClass )
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				String attribute = getAttribute( name );
				
				Class<T> clazz = DataRegistry.getClass( attribute );
				
				if ( clazz == null )
				{
					clazz = (Class<T>)Class.forName( attribute ); 
				}
				
				return clazz;
			}
			catch ( Exception ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, class type expected: %s", name, getName(), ex.getMessage() );
			}
		}
		else if ( defaultClass == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		
		return defaultClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T readInstance( String name )
	{
		if ( hasAttribute( name ) )
		{
			String attribute = getAttribute( name );
			
			T value = DataRegistry.get( attribute );
			
			if ( value == null )
			{
				value = DataRegistry.getInstance( attribute, false );
				
				if ( value == null )
				{
					try
					{
						value = (T)instantiate( Class.forName( attribute ) );	
					}
					catch (Exception ex)
					{
						throw new DataException( "Invalid attribute %s in input %s, was not found as an object in the registry, a class in the registry, or a path to a class: %s", name, getName(), ex.getMessage() );		
					}
				}
			}
			
			return value;
		}
		
		throw new DataException( "Expecting attribute %s in input %s", name, getName() );
	}
	
	@Override
	public <E extends Enum<E>> E readEnum( String name, Class<E> enumType )
	{
		return readEnum( name, enumType, null );
	}
	
	@Override
	public <E extends Enum<E>> E readEnum( String name, Class<E> enumType, E enumConstant )
	{
		if ( hasAttribute( name ) )
		{
			try
			{
				return Enum.valueOf( enumType, getAttribute( name ) );
			}
			catch ( Exception ex )
			{
				throw new DataException( "Invalid attribute %s in input %s, enum type expected: %s", name, getName(), ex.getMessage() );
			}
		}
		else if ( enumConstant == null )
		{
			throw new DataException( "Expecting attribute %s in input %s", name, getName() );
		}
		return enumConstant;
	}
	
	@Override
	public <T> T[] readDelimited( String name, char delimiter, Class<T> itemType )
	{
		return null;
	}
	
	public InputModel[] getChildren( String name )
	{
		List<InputModel> modelList = readModelList( name );
		
		return modelList.toArray( new InputModel[ modelList.size() ] );
	}
	
	@Override
	public <T extends DataModel> T[] readModelArray( String name, Class<T> itemType )
	{
		List<T> list = readModelList( name, itemType );
		
		@SuppressWarnings("unchecked")
		T[] array = (T[])Array.newInstance( itemType, list.size() );
		
		return list.toArray( array );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataModel> T[] readModelArrayQualified( String name, Class<T> itemType, String className ) throws DataException
	{
		List<T> list = readModelListQualified( name, className );
		
		return list.toArray( (T[])Array.newInstance( itemType, list.size() ) );
	}
	
	@Override
	public InputModel[] readModelArray( String name ) throws DataException
	{
		List<InputModel> list = readModelList( name );
		
		return list.toArray( new InputModel[ list.size() ] );
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DataModel> T[] readModelArrayDynamic( String name, String attributeName ) throws DataException
	{
		List<InputModel> children = readModelList( name );
		T[] models = (T[])new DataModel[ children.size() ];
		
		for ( int i = 0; i < children.size(); i++ )
		{
			InputModel child = children.get( i );
			
			models[i] = child.readInstance( attributeName );
			models[i].read( child );
		}
		
		return models;
	}
	
	@Override
	public <T extends DataModel> List<T> readModelList( String name, Class<T> itemType )
	{
		List<InputModel> modelList = readModelList( name );
		List<T> list = new ArrayList<T>( modelList.size() );
		
		for ( int i = 0; i < modelList.size(); i++ )
		{
			T instance = instantiate( itemType );
			instance.read( modelList.get( i ) );
			list.add( instance );
		}
		
		return list;
	}
	
	@Override
	public List<InputModel> readModelList( String name ) throws DataException
	{
		List<InputModel> list = new ArrayList<InputModel>();
		
		for ( InputModel c : this )
		{
			if ( name.equals( c.getName() ) )
			{
				list.add( c );
			}
		}
		
		return list;
	}
	
	@Override
	public InputModel readImportable( String name )
	{
		return readImportable( name, IMPORT_ATTRIBUTE );
	}

    @Override
	public InputModel readImportable( String name, String attributeName) throws DataException
	{
		InputModel input = readModel( name );

		return ( input == null ? null : ((BaseInputModel)input).resolveImport( attributeName ) );
	}

	@Override
	public <T extends DataModel> T readImportableModel( String name, String className ) throws DataException
	{
		try
		{
			InputModel input = readImportable( name );

			if ( input == null )
			{
				return null;
			}
			
			T model = input.readInstance( className );
			
			model.read( input );
			
			return model;
		}
		catch ( DataException e )
		{
			throw e;
		}
		catch ( Exception e )
		{
			throw new DataException( e );
		}
	}

	@Override
	public <T extends DataModel> T readImportableModel( String name, T model ) throws DataException
	{
		try
		{
			InputModel input = readImportable( name );
			
			if ( input == null )
			{
				return null;
			}
			
			model.read( input );
			
			return model;
		}
		catch ( DataException e )
		{
			throw e;
		}
		catch ( Exception e )
		{
			throw new DataException( e );
		}
	}

	@Override
	public InputModel resolveImport() throws DataException
	{
		return resolveImport( IMPORT_ATTRIBUTE );
	}

	
	@Override
	public InputModel resolveImport(String attributeName) throws DataException
	{
		if ( !hasAttribute( attributeName ) )
		{
			return this;
		}
		
		String attribute = readString( attributeName );
		
		InputModel external = Assets.load( attribute ); 
				
		if ( external == null )
		{
			throw new DataException( "Externalized input on attribue '%s' of '%s' with value '%s' does not exist", attributeName, name, attribute );
		}
		
		return new DualInputModel( format, name, this, external );
	}
	
	@Override
	public <T extends DataModel> T readModel( String name, String className ) throws DataException
	{
		try
		{
			InputModel model = readModel( name );
			T instance = model.readInstance( className );
			instance.read( model );
			
			return instance;
		}
		catch ( Exception e )
		{
			throw new DataException( e );
		}
	}
	
	@Override
	public <T extends DataModel> T readModel( String name, T model )
	{
		InputModel input = readModel( name );
		T read = null;
		
		if ( input != null )
		{
			model.read( input );
			read = model;
		}
		
		return read;
	}
	
	@Override
	public <T> T readModel(String name, T model, Calculator<T> calculator) throws DataException
	{
		InputModel input = readModel( name );
		
		return calculator.read( model, input );
	}
	
	@Override
	public InputModel readModel( String name )
	{
		List<InputModel> modelList = readModelList( name );
		
		if ( modelList == null || modelList.size() == 0 )
		{
			return null;
		}
		else if ( modelList.size() > 1 )
		{
			throw new DataException( "Expected only one input with name %s but found %d.", name, modelList.size() );
		}
		
		return modelList.get( 0 );
	}
	
	@Override
	public <T extends DataModel> T readModel( String name, String attributeName, boolean requires ) throws DataException
	{
		List<InputModel> modelList = readModelList( name );
		T result = null;
		
		if ( modelList.size() == 0 )
		{
			if ( requires )
			{
				throw new DataException( "Missing child %s", attributeName );	
			}
		}
		else
		{
			InputModel inputModel = modelList.get( 0 );
			
			result = inputModel.readInstance( attributeName );
			result.read( inputModel );	
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> readModels( String name, InputModelListener listener ) throws DataException
	{
		List<InputModel> modelList = readModelList( name );
		List<T> list = new ArrayList<T>();
		
		for ( int i = 0; i < modelList.size(); i++ )
		{
			list.add( (T)listener.onRead( this, modelList.get( i ) ) );
		}
		
		return list;
	}
	
	private <T> T instantiate( Class<T> type )
	{
		try
		{
			return type.newInstance();
		}
		catch ( Exception ex )
		{
			throw new DataException( ex );
		}
	}
	
	@Override
	public <T extends DataModel> T[] readModelArray( String name, String attributeName ) throws DataException
	{
		Class<T> type = readClass( attributeName );
		
		return readModelArray( name, type );
	}
	
	@Override
	public <T extends DataModel> List<T> readModelList( String name, String attributeName ) throws DataException
	{
		Class<T> type = readClass( attributeName );
		
		return readModelList( name, type );
	}
	
	@Override
	public <T extends DataModel> List<T> readModelListQualified( String name, String className ) throws DataException
	{
		List<InputModel> modelList = readModelList( name );
		List<T> list = new ArrayList<T>();
		
		try
		{
			for ( int i = 0; i < modelList.size(); i++ )
			{
				InputModel model = modelList.get( i );
				
				T instance = model.readInstance( className );
				instance.read( model );
				
				list.add( instance );
			}
		}
		catch ( DataException e )
		{
			throw e;
		}
		catch ( Exception e )
		{
			throw new DataException( e );
		}
		
		return list;
	}
	
	public boolean isLoaded()
	{
		return true;
	}
	
	public void load() throws Exception
	{
		
	}

	public boolean isActivated()
	{
		return true;
	}
	
	public void activate()
	{
		
	}

	public void delete()
	{
		
	}
	
}