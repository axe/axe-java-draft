
package com.axe.net;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.UUID;

import com.axe.core.Bufferable;
import com.axe.util.Reflections;


public class ReflectionMessage<M> implements Message<M>
{

	public final int id;
	public final Class<M> type;
	public final Field[] fields;

	public ReflectionMessage(int id, Class<M> type)
	{
		this.id = id;
		this.type = type;
		this.fields = Reflections.getFieldArray( type, Modifier.STATIC | Modifier.TRANSIENT );
	}
	
	@Override
	public M read( ByteBuffer in )
	{
		try
		{
			M data = type.newInstance();

			for (int i = 0; i < fields.length; i++)
			{
				Field f = fields[i];

				f.set( data, read( in, f.getType() ) );
			}

			return data;	
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	public static Object read( ByteBuffer in, Class<?> fieldType ) throws Exception
	{
		if (fieldType == byte.class) 	return in.get();
		if (fieldType == Byte.class) 	return readBoolean( in ) ? null : in.get();
		if (fieldType == short.class) return in.getShort();
		if (fieldType == Short.class) return readBoolean( in ) ? null : in.getShort();
		if (fieldType == int.class) return in.getInt();
		if (fieldType == Integer.class) return readBoolean( in ) ? null : in.getInt();
		if (fieldType == long.class) return in.getLong();
		if (fieldType == Long.class) return readBoolean( in ) ? null : in.getLong();
		if (fieldType == float.class) return in.getFloat();
		if (fieldType == Float.class) return readBoolean( in ) ? null : in.getFloat();
		if (fieldType == double.class) return in.getDouble();
		if (fieldType == Double.class) return readBoolean( in ) ? null : in.getDouble();
		if (fieldType == boolean.class) return readBoolean( in );
		if (fieldType == Boolean.class) return readBoolean( in ) ? null : readBoolean( in );
		if (fieldType == char.class) return in.getChar();
		if (fieldType == Character.class) return readBoolean( in ) ? null : in.getChar();
		if (fieldType == UUID.class) return readBoolean( in ) ? null : new UUID( in.getLong(), in.getLong() );
		if (fieldType.isEnum()) return readBoolean( in ) ? null : fieldType.getEnumConstants()[in.get() & 0xFF];
		
		if (fieldType == String.class)
		{
			if (readBoolean( in ))
			{
				return null;
			}
			
			int stringLength = in.getShort() & 0xFFFF;
			byte[] stringBytes = new byte[stringLength];
			in.get( stringBytes );
			return new String( stringBytes );
		}
		
		if (Bufferable.class.isAssignableFrom( fieldType ))
		{
			if (readBoolean( in ))
			{
				return null;
			}
			
			Bufferable b = (Bufferable)fieldType.newInstance();
			b.read( in );
			return b;
		}
		
		throw new RuntimeException( "No reading logic for type " + fieldType );
	}
	
	public static boolean readBoolean( ByteBuffer in )
	{
		return (in.get() != 0 ? true : false);
	}

	@Override
	public void write( ByteBuffer out, M m )
	{
		try
		{
			for (int i = 0; i < fields.length; i++)
			{
				Field f = fields[i];

				write( out, f.getType(), f.get( m ) );
			}	
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	public static void write( ByteBuffer out, Class<?> fieldType, Object fieldValue ) throws Exception
	{
		if (fieldType == byte.class) { out.put( (Byte)fieldValue ); }
		else if (fieldType == Byte.class) { if (writeNonNull( out, fieldValue )) out.put( (Byte)fieldValue ); }
		else if (fieldType == short.class) { out.putShort( (Short)fieldValue ); }
		else if (fieldType == Short.class) { if (writeNonNull( out, fieldValue )) out.putShort( (Short)fieldValue ); }
		else if (fieldType == int.class) { out.putInt( (Integer)fieldValue ); }
		else if (fieldType == Integer.class) { if (writeNonNull( out, fieldValue )) out.putInt( (Integer)fieldValue ); }
		else if (fieldType == long.class) { out.putLong( (Long)fieldValue ); }
		else if (fieldType == Long.class) { if (writeNonNull( out, fieldValue )) out.putLong( (Long)fieldValue ); }
		else if (fieldType == float.class) { out.putFloat( (Float)fieldValue ); }
		else if (fieldType == Float.class) { if (writeNonNull( out, fieldValue )) out.putFloat( (Float)fieldValue ); }
		else if (fieldType == double.class) { out.putDouble( (Double)fieldValue ); }
		else if (fieldType == Double.class) { if (writeNonNull( out, fieldValue )) out.putDouble( (Double)fieldValue ); }
		else if (fieldType == boolean.class) { out.put( (Boolean)fieldValue ? (byte)1 : (byte)0 ); }
		else if (fieldType == Boolean.class) { if (writeNonNull( out, fieldValue )) out.put( (Boolean)fieldValue ? (byte)1 : (byte)0 ); }
		else if (fieldType == char.class) { out.putChar( (Character)fieldValue ); }
		else if (fieldType == Character.class) { if (writeNonNull( out, fieldValue )) out.putChar( (Character)fieldValue ); }
		else if (fieldType == UUID.class) { if (writeNonNull( out, fieldValue )) { out.putLong( ((UUID)fieldValue).getMostSignificantBits() ); out.putLong( ((UUID)fieldValue).getLeastSignificantBits() ); } }
		else if (fieldType.isEnum()) { if (writeNonNull( out, fieldValue )) out.put( (byte)((Enum<?>)fieldValue).ordinal() ); }
		
		else if (fieldType == String.class)
		{
			if (writeNonNull( out, fieldValue )) 
			{
				String s = (String)fieldValue;
				out.putShort( (short)s.length() );
				out.put( s.getBytes() );	
			}
		}
		
		else if (Bufferable.class.isAssignableFrom( fieldType ))
		{
			if (writeNonNull(out, fieldValue)) 
			{
				((Bufferable)fieldValue).write( out );
			}
		}
		
		else
		{
			throw new RuntimeException( "No writing logic for type " + fieldType );
		}
	}
	
	public static boolean writeNonNull(ByteBuffer out, Object o)
	{
		out.put( (byte)(o == null ? 1 : 0) );
		
		return (o != null);
	}
	
	@Override
	public int sizeOf( M m )
	{
		int size = 0;
		
		try
		{
			for (int i = 0; i < fields.length; i++)
			{
				Field f = fields[i];

				size += sizeOf( f.getType(), f.get( m ) );
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
		
		return size;
	}

	public static int sizeOf( Class<?> fieldType, Object fieldValue ) throws Exception
	{
		if (fieldType == byte.class) return 1;
		if (fieldType == Byte.class) return sizeOfNullable(fieldValue, 1);
		if (fieldType == short.class) return 2;
		if (fieldType == Short.class) return sizeOfNullable(fieldValue, 2);
		if (fieldType == int.class) return 4;
		if (fieldType == Integer.class) return sizeOfNullable(fieldValue, 4);
		if (fieldType == long.class) return 8;
		if (fieldType == Long.class) return sizeOfNullable(fieldValue, 8);
		if (fieldType == float.class) return 4;
		if (fieldType == Float.class) return sizeOfNullable(fieldValue, 4);
		if (fieldType == double.class) return 8;
		if (fieldType == Double.class) return sizeOfNullable(fieldValue, 8);
		if (fieldType == boolean.class) return 1;
		if (fieldType == Boolean.class) return sizeOfNullable(fieldValue, 1);
		if (fieldType == char.class) return 2;
		if (fieldType == Character.class) return sizeOfNullable(fieldValue, 2);
		if (fieldType == UUID.class) return sizeOfNullable(fieldValue, 16);
		if (fieldType.isEnum()) return sizeOfNullable(fieldValue, 1);
		if (fieldType == String.class) return fieldValue == null ? 1 : 3 + ((String)fieldValue).length(); 
		if (Bufferable.class.isAssignableFrom( fieldType )) return fieldValue == null ? 1 : 1 + ((Bufferable)fieldValue).bytes();
		
		throw new RuntimeException( "No sizeOf logic for type " + fieldType );
	}
	
	public static int sizeOfNullable(Object value, int normalSize) 
	{
		return (value == null ? 1 : 1 + normalSize);
	}
	
	@Override
	public int id()
	{
		return id;
	}

	@Override
	public Class<M> type()
	{
		return type;
	}

}
