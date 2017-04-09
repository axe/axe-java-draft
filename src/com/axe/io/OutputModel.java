package com.axe.io;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import com.axe.math.calc.Calculator;


public interface OutputModel
{
	public String getName();
	public int getScope();
	public DataFormat getFormat();
	public OutputModel writeModel( String name );
	
	// Attributes
	
	public void write(String name, Object value);
	public void write(String name, long value, int radix);
	public void writeClass(String name, Class<?> type);
	public void writeInstance(String name, Object value);
	public void writeInstance(String name, OutputModel model);
	public <E extends Enum<E>> void writeEnum(String name, E enumConstant);
	public void writeDelimited(String name, char delimiter, Object[] array);
	
	// Children

	public <T extends DataModel> OutputModel[] writeModelArray(String name, T[] models );
	public <T extends DataModel> OutputModel[] writeModelArray(String name, T[] models, String attributeName );

	public <T> OutputModel[] writeModelArray(String name, T[] models, Calculator<T> calculator );
	public <T> OutputModel[] writeModelArray(String name, T[] models, String attributeName, Calculator<T> calculator );
	
	public <T extends DataModel> OutputModel[] writeModelArrayQualified(String name, T[] models, String className);
	
	public <T extends DataModel> OutputModel[] writeModelArrayDynamic(String name, T[] models, String attributeName );
	
	public <T extends DataModel> OutputModel[] writeModelListQualified(String name, List<T> models, String className);
	public <T extends DataModel> OutputModel[] writeModelList(String name, List<T> models);
	
	public OutputModel writeModel(String name, String className, DataModel model);

	public OutputModel writeModel(String name, DataModel model);
	public OutputModel writeModel(String name, DataModel model, String attributeName );
	
	public <T> OutputModel writeModel(String name, T model, Calculator<T> calculator );
	public <T> OutputModel writeModel(String name, T model, String attributeName, Calculator<T> calculator );
	
	public <T> List<OutputModel> writeModels( String name, OutputModelListener<T> listener, T[] models );
	
	// Output
	
	public String toString();
	
	public void output( Writer writer ) throws Exception;
	public void output( File file ) throws Exception;
	public void output( OutputStream stream ) throws Exception;
}