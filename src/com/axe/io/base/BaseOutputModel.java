package com.axe.io.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.axe.io.DataFormat;
import com.axe.io.DataModel;
import com.axe.io.OutputModel;
import com.axe.io.OutputModelListener;
import com.axe.io.DataRegistry;
import com.axe.math.calc.Calculator;


public abstract class BaseOutputModel<F extends DataFormat> implements OutputModel
{

	protected final String name;
	protected final F format;
	protected final int scope;
	
	public BaseOutputModel(F format, String name, int scope)
	{
		this.format = format;
		this.name = name;
		this.scope = scope;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public DataFormat getFormat()
	{
		return format;
	}
	
	@Override
	public int getScope()
	{
		return scope;
	}
	
	@Override
	public void writeClass( String name, Class<?> type )
	{
		String className = DataRegistry.getClassName( type );
		
		if ( className == null )
		{
			className = type.getName();
		}
		
		write( name, className );
	}

	@Override
	public void writeInstance( String name, OutputModel model )
	{
		
	}
	
	@Override
	public void writeInstance(String name, Object instance)
	{
		if ( instance == null )
		{
			return;
		}
		
		String value = DataRegistry.getName( instance );
		
		if ( value == null )
		{
			value = DataRegistry.getClassName( instance.getClass() );
			
			if ( value == null )
			{
				value = instance.getClass().getName();
			}
		}
		
		write( name, value );
	}
	
	@Override
	public void write(String name, long value, int radix)
	{
		write( name, Long.toString( value, radix ) );
	}

	@Override
	public <E extends Enum<E>> void writeEnum( String name, E enumConstant )
	{
		write( name, enumConstant.name() );
	}

	@Override
	public void writeDelimited( String name, char delimiter, Object[] array )
	{
		
	}

	@Override
	public <T extends DataModel> OutputModel[] writeModelArray( String name, T[] models )
	{
		OutputModel[] output = new OutputModel[ models.length ];
		
		for(int i = 0; i < models.length; i++)
		{
			if (models[i] != null)
			{
				models[i].write( output[i] = writeModel( name ) );	
			}
		}
		
		return output;
	}

	@Override
	public <T> OutputModel[] writeModelArray( String name, T[] models, String attributeName, Calculator<T> calculator )
	{
		writeClass( attributeName, models[0].getClass() );
		
		return writeModelArray( name, models, calculator );
	}

	@Override
	public <T> OutputModel[] writeModelArray( String name, T[] models, Calculator<T> calculator )
	{
		OutputModel[] output = new OutputModel[ models.length ];
		
		for(int i = 0; i < models.length; i++)
		{
			if (models[i] != null)
			{
				calculator.write( models[i], output[i] = writeModel( name ) );
			}
		}
		
		return output;
	}

	@Override
	public <T extends DataModel> OutputModel[] writeModelArray( String name, T[] models, String attributeName )
	{
		writeClass( attributeName, models[0].getClass() );
		
		return writeModelArray( name, models );
	}

	@Override
	public <T extends DataModel> OutputModel[] writeModelArrayQualified(String name, T[] models, String className)
	{
		OutputModel[] output = new OutputModel[ models.length ];
		
		for (int i = 0; i < models.length; i++)
		{
			T model = models[ i ];
			
			if (model != null)
			{
				output[i] = writeModel( name );
				model.write( output[i] );
				output[i].writeInstance( className, model );
			}
		}
		
		return output;
	}
	
	@Override
	public <T extends DataModel> OutputModel[] writeModelArrayDynamic(String name, T[] models,String attributeName )
	{
		OutputModel[] output = new OutputModel[ models.length ];
		
		for (int i = 0; i < models.length; i++)
		{
			if (models[i] != null)
			{
				output[i] = writeModel( name );
				output[i].writeInstance( attributeName, models[i] );
				models[i].write( output[i] );				
			}
		}
		
		return output;
	}
	
	@Override
	public <T extends DataModel> OutputModel[] writeModelListQualified(String name, List<T> models, String className)
	{
		OutputModel[] output = new OutputModel[ models.size() ];
		
		for (int i = 0; i < models.size(); i++)
		{
			T model = models.get( i );
			
			if (model != null)
			{
				output[i] = writeModel( name );
				model.write( output[i] );
				output[i].writeInstance( className, model );
			}
		}
		
		return output;
	}
	
	@Override
	public <T extends DataModel> OutputModel[] writeModelList( String name, List<T> models )
	{
		OutputModel[] output = new OutputModel[ models.size() ];
		
		for(int i = 0; i < models.size(); i++)
		{
			if (models.get(i) != null)
			{
				models.get(i).write( output[i] = writeModel( name ) );	
			}
		}
		
		return output;
	}
	
	@Override
	public OutputModel writeModel(String name, String className, DataModel model)
	{
		if (model == null)
		{
			return null;
		}
		
		OutputModel out = writeModel( name );
		
		out.write( className, model.getClass().getName() );
		
		model.write( out );
		
		return out;
	}

	@Override
	public OutputModel writeModel( String name, DataModel model )
	{
		if ( model == null )
		{
			return null;
		}
		
		OutputModel out = writeModel( name );
		
		model.write( out );
		
		return out;
	}

	@Override
	public OutputModel writeModel( String name, DataModel model, String attributeName )
	{
		if ( model == null )
		{
			return null;
		}
		
		OutputModel out = writeModel( name );
		
		out.writeClass( attributeName, model.getClass() );
		
		model.write( out );
		
		return out;
	}
	
	@Override
	public <T> OutputModel writeModel(String name, T model, Calculator<T> calculator )
	{
		if ( model == null )
		{
			return null;
		}
		
		OutputModel out = writeModel( name );
		
		calculator.write( model, out );
		
		return out;
	}
	
	@Override
	public <T> OutputModel writeModel(String name, T model, String attributeName, Calculator<T> calculator )
	{
		if ( model == null )
		{
			return null;
		}
		
		OutputModel out = writeModel( name );
		
		out.writeClass( attributeName, model.getClass() );
		
		calculator.write( model, out );
		
		return out;
	}


	@Override
	public <T> List<OutputModel> writeModels( String name, OutputModelListener<T> listener, T[] models )
	{
		List<OutputModel> list = new ArrayList<OutputModel>();
		
		for (T m : models)
		{
			OutputModel out = writeModel( name );
			
			listener.onWrite( this, out, m );
			
			list.add( out );
		}
		
		return list;
	}

	@Override
	public void output( final Writer writer ) throws Exception
	{
		output( new OutputStream()
		{
			@Override
			public void write( int b ) throws IOException
			{
				writer.write( b );
			}
		});
	}
	
	@Override
	public void output( File file ) throws Exception
	{
		output( new FileOutputStream( file ) );
	}
	
	@Override
	public String toString()
	{
		StringWriter writer = new StringWriter();
		try
		{
			output( writer );
		}
		catch (Exception e)
		{
			
		}
		return writer.toString();
	}

}