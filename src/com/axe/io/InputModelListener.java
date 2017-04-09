package com.axe.io;


public interface InputModelListener
{
	public Object onRead(InputModel parent, InputModel child) throws DataException;
}
