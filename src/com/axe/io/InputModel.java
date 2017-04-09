package com.axe.io;

import java.util.List;

import com.axe.math.calc.Calculator;

public interface InputModel extends Iterable<InputModel>
{
	public String getName();
	public DataFormat getFormat();
	
	public void copyTo( OutputModel out );
	
	public InputModel[] getChildren( String name );
	
	public boolean hasAttribute(String name);
	public boolean hasChild( String name );
	
	// Attributes
	
	public double readDouble(String name) throws DataException;
	public double readDouble(String name, Double defaultValue) throws DataException;
	public float readFloat(String name) throws DataException;
	public float readFloat(String name, Float defaultValue) throws DataException;
	public byte readByte(String name) throws DataException;
	public byte readByte(String name, Byte defaultValue) throws DataException;
	public byte readByte(String name, Byte defaultValue, int radix) throws DataException;
	public short readShort(String name) throws DataException;
	public short readShort(String name, Short defaultValue) throws DataException;
	public short readShort(String name, Short defaultValue, int radix) throws DataException;
	public int readInt(String name) throws DataException;
	public int readInt(String name, Integer defaultValue) throws DataException;
	public int readInt(String name, Integer defaultValue, int radix) throws DataException;
	public long readLong(String name) throws DataException;
	public long readLong(String name, Long defaultValue) throws DataException;
	public long readLong(String name, Long defaultValue, int radix) throws DataException;
	public boolean readBoolean(String name) throws DataException;
	public boolean readBoolean(String name, Boolean defaultValue) throws DataException;
	public char readChar(String name) throws DataException;
	public char readChar(String name, Character defaultValue) throws DataException;
	public String readString(String name) throws DataException;
	public String readString(String name, String defaultValue) throws DataException;
	public String readNullableString(String name) throws DataException;
	public <T> Class<T> readClass(String name) throws DataException;
	public <T> Class<T> readClass(String name, Class<T> defaultClass) throws DataException;
	public <T> T readInstance(String name) throws DataException;
	public <E extends Enum<E>> E readEnum(String name, Class<E> enumType) throws DataException;
	public <E extends Enum<E>> E readEnum(String name, Class<E> enumType, E enumConstant) throws DataException;
	public <T> T[] readDelimited(String name, char delimiter, Class<T> itemType) throws DataException;

	// Children
	
	public <T extends DataModel> T[] readModelArray(String name, Class<T> itemType) throws DataException;
	public <T extends DataModel> T[] readModelArray(String name, String attributeName) throws DataException;
	public InputModel[] readModelArray(String name) throws DataException;

	public <T extends DataModel> T[] readModelArrayQualified(String name, Class<T> itemType, String className) throws DataException;
	public <T extends DataModel> T[] readModelArrayDynamic(String name, String attributeName ) throws DataException;
	
	public <T extends DataModel> List<T> readModelList(String name, Class<T> itemType) throws DataException;
	public <T extends DataModel> List<T> readModelList(String name, String attributeName) throws DataException;
	public List<InputModel> readModelList(String name) throws DataException;
	public <T extends DataModel> List<T> readModelListQualified(String name, String className) throws DataException;

	// Child
	
	public InputModel readImportable( String name ) throws DataException;
	public InputModel readImportable( String name, String attributeName ) throws DataException;

	public <T extends DataModel> T readImportableModel( String name, String className ) throws DataException;
	public <T extends DataModel> T readImportableModel( String name, T model ) throws DataException;
	
	public InputModel resolveImport() throws DataException;
	public InputModel resolveImport(String attributeName) throws DataException;
	
	public InputModel readModel(String name) throws DataException;
	public <T extends DataModel> T readModel(String name, String className) throws DataException;
	public <T extends DataModel> T readModel(String name, T model) throws DataException;
	public <T extends DataModel> T readModel(String name, String attributeName, boolean requires) throws DataException;

	public <T> T readModel(String name, T model, Calculator<T> calculator) throws DataException;
	
	public <T> List<T> readModels(String name, InputModelListener listener) throws DataException;
}