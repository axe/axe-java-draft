package com.axe.input;

public class InputStateAny extends InputState 
{

	public InputState[] states;
	
	public InputStateAny(InputState ... states) 
	{
		super( states[0].input );
		
		this.states = states;
	}
	
	public void update()
	{
		down = false;
		frames = 0;
		time = 0;
		seconds = 0;
		presses = 0;
		press = false;
		changeTime = Long.MAX_VALUE;
		
		for (int i = 0; i < states.length; i++)
		{
			InputState s = states[ i ];
			
			down = down || s.down;
			frames = Math.max( frames, s.frames );
			time = Math.max( time, s.time );
			seconds = Math.max( seconds, s.seconds );
			press = press || s.press;
			presses = Math.max( presses, s.presses );
			changeTime = Math.min( changeTime, s.changeTime );
		}
	}

}
