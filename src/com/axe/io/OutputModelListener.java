package com.axe.io;


public interface OutputModelListener<T>
{
	public void onWrite(OutputModel parent, OutputModel child, T value) throws DataException;
}
