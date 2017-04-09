package com.axe.input;

public class KeyState extends InputState 
{
	
	public Key key;
	public char character;
	
	public KeyState(int input) 
	{
		super(input);
		
		this.key = Key.values()[ input ];
	}
	
	public KeyState(Key key) 
	{
		super( key.ordinal() );
		
		this.key = key;
	}
	
}