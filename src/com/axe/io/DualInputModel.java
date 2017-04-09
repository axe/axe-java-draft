package com.axe.io;

import java.util.Iterator;

import com.axe.io.base.BaseInputModel;

public class DualInputModel extends BaseInputModel<DataFormat>
{
	
	private final InputModel first;
	private final InputModel second;
	private final DualIterator iterator;

	public DualInputModel( DataFormat format, String name, InputModel first, InputModel second )
	{
		super( format, name );
		
		this.first = first;
		this.second = second;
		this.iterator = new DualIterator();
	}
	
	@Override
	public void copyTo( OutputModel out )
	{
		second.copyTo( out );
		first.copyTo( out );
	}

	@Override
	public boolean hasAttribute( String name )
	{
		return first.hasAttribute( name ) || second.hasAttribute( name );
	}

	@Override
	public boolean hasChild( String name )
	{
		return first.hasChild( name ) || second.hasChild( name );
	}

	@Override
	public Iterator<InputModel> iterator()
	{
		return iterator.take();
	}

	@Override
	protected String getAttribute( String name )
	{
		if ( first.hasAttribute( name ) )
		{
			return first.readString( name );
		}
		
		return second.readString( name );
	}
	
	@Override
	public InputModel readModel( String name )
	{
		InputModel model = first.readModel( name );
		
		if ( model == null )
		{
			model = second.readModel( name );
		}
		
		return model;
	}
	
	@Override
	public <T extends DataModel> T readModel( String name, String attributeName, boolean requires ) throws DataException
	{
		T result = first.readModel( name, attributeName, false );
		
		if ( result == null )
		{
			result = second.readModel( name, attributeName, requires ); 
		}
		
		return result;
	}
	
	
	private class DualIterator implements Iterator<InputModel> 
	{
		public Iterator<InputModel> s, f;
		public boolean nextSecond = true;
		
		public DualIterator()
		{
			s = second.iterator();
			f = first.iterator();
		}
		
		public Iterator<InputModel> take()
		{
			if ( hasNext() )
			{
				return new DualIterator();
			}
			
			s = second.iterator();
			f = first.iterator();
			
			nextSecond = s.hasNext();
			
			return this;
		}

		@Override
		public boolean hasNext()
		{
			return s.hasNext() || f.hasNext();
		}

		@Override
		public InputModel next()
		{
			return ( ( nextSecond = s.hasNext() ) ? s.next() : f.next() );
		}

		@Override
		public void remove()
		{
			if ( nextSecond )
			{
				s.remove();
			}
			else
			{
				f.remove();
			}
		}
		
	}

}
