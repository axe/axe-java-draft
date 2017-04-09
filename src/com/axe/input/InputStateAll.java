package com.axe.input;

public class InputStateAll extends InputState 
{

	public InputState[] states;
	
	public InputStateAll(InputState ... states) 
	{
		super( states[0].input );
		
		this.states = states;
	}
	
	public void update()
	{
		down = true;
		frames = Integer.MAX_VALUE;
		time = Long.MAX_VALUE;
		seconds = Float.MAX_VALUE;
		presses = Integer.MAX_VALUE;
		press = true;
		changeTime = 0;
		
		for (int i = 0; i < states.length; i++)
		{
			InputState s = states[ i ];
			
			down = down && s.down;
			frames = Math.min( frames, s.frames );
			time = Math.min( time, s.time );
			seconds = Math.min( seconds, s.seconds );
			press = press && s.press;
			presses = Math.min( presses, s.presses );
			changeTime = Math.max( changeTime, s.changeTime );
		}
	}

}
