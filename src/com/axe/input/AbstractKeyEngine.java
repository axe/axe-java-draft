package com.axe.input;

import java.util.ArrayDeque;
import java.util.Queue;

import com.axe.window.Window;

public abstract class AbstractKeyEngine implements KeyEngine 
{
	
	public KeyState[] states;
	public InputStateAny shift;
	public KeyState leftShift;
	public KeyState rightShift;
	public InputStateAny control;
	public InputStateAny meta;
	public InputStateAny command;
	public InputStateAny alt;
	public Queue<KeyState> queue;
	public float pressDelay;
	public float pressInterval;
	public char[][] keyChars;
	
	public AbstractKeyEngine()
	{
		int n = Key.values().length;
		
		this.states = new KeyState[ n ];
		
		while (--n >= 0)
		{
			this.states[n] = new KeyState(n);
		}

		this.leftShift = getKey( Key.LEFT_SHIFT );
		this.rightShift = getKey( Key.RIGHT_SHIFT );
		this.shift = new InputStateAny( 
			this.leftShift, 
			this.rightShift
		);
		 
		this.control = new InputStateAny( 
			getKey( Key.LEFT_CONTROL ), 
			getKey( Key.RIGHT_CONTROL ) 
		);
		
		this.command = new InputStateAny( 
			getKey( Key.LEFT_COMMAND ), 
			getKey( Key.RIGHT_COMMAND ) 
		);
		
		this.alt = new InputStateAny( 
			getKey( Key.LEFT_ALT ), 
			getKey( Key.RIGHT_ALT ) 
		);
		
		this.meta = new InputStateAny( 
			getKey( Key.LEFT_COMMAND ), 
			getKey( Key.RIGHT_COMMAND ), 
			getKey( Key.LEFT_CONTROL ), 
			getKey( Key.RIGHT_CONTROL ) 
		);
		
		this.queue = new ArrayDeque<>();
		
		this.pressDelay = DEFAULT_PRESS_DELAY;
		this.pressInterval = DEFAULT_PRESS_INTERVAL;
		
		this.keyChars = new char[Key.values().length][2];
		this.loadChars();
	}
	
	public void init(long startTime)
	{
		for (int i = 0; i < states.length; i++)
		{
			states[ i ].changeTime = startTime;
		}
	}
	
	public void tick(long currentTime)
	{
		for (int i = 0; i < states.length; i++)
		{
			states[ i ].tick( currentTime, pressDelay, pressInterval );
		}
		
		shift.update();
		control.update();
		command.update();
		alt.update();
		meta.update();
		
		int charIndex = shift.down ? 1 : 0;
		
		for (int i = 0; i < states.length; i++)
		{
			states[ i ].character = keyChars[ i ][ charIndex ];
		}
	}
	
	public void queueKeyState(Key key, boolean down, long inputTime, Window inputWindow)
	{		
		int charIndex = leftShift.down || rightShift.down ? 1 : 0;
		
		KeyState state = new KeyState( key );
		
		state.setDown( down, inputTime, inputWindow );
		state.character = keyChars[ state.input ][ charIndex ];
		
		queue.offer( state );
	}
	
	public void loadChars()
	{
		keyChars[ Key.N1.ordinal() ] = new char[] {'1', '!'};
		keyChars[ Key.N2.ordinal() ] = new char[] {'2', '@'};
		keyChars[ Key.N3.ordinal() ] = new char[] {'3', '#'};
		keyChars[ Key.N4.ordinal() ] = new char[] {'4', '$'};
		keyChars[ Key.N5.ordinal() ] = new char[] {'5', '%'};
		keyChars[ Key.N6.ordinal() ] = new char[] {'6', '^'};
		keyChars[ Key.N7.ordinal() ] = new char[] {'7', '&'};
		keyChars[ Key.N8.ordinal() ] = new char[] {'8', '*'};
		keyChars[ Key.N9.ordinal() ] = new char[] {'9', '('};
		keyChars[ Key.N0.ordinal() ] = new char[] {'0', ')'};
		keyChars[ Key.MINUS.ordinal() ] = new char[] {'-', '_'};
		keyChars[ Key.EQUALS.ordinal() ] = new char[] {'=', '+'};
		keyChars[ Key.BACK.ordinal() ] = new char[] {'\b', '\b'};
		keyChars[ Key.TAB.ordinal() ] = new char[] {'\t', '\t'};
		keyChars[ Key.Q.ordinal() ] = new char[] {'q', 'Q'};
		keyChars[ Key.W.ordinal() ] = new char[] {'w', 'W'};
		keyChars[ Key.E.ordinal() ] = new char[] {'e', 'E'};
		keyChars[ Key.R.ordinal() ] = new char[] {'r', 'R'};
		keyChars[ Key.T.ordinal() ] = new char[] {'t', 'T'};
		keyChars[ Key.Y.ordinal() ] = new char[] {'y', 'Y'};
		keyChars[ Key.U.ordinal() ] = new char[] {'u', 'U'};
		keyChars[ Key.I.ordinal() ] = new char[] {'i', 'I'};
		keyChars[ Key.O.ordinal() ] = new char[] {'o', 'O'};
		keyChars[ Key.P.ordinal() ] = new char[] {'p', 'P'};
		keyChars[ Key.LEFT_BRACKET.ordinal() ] = new char[] {'[', '{'};
		keyChars[ Key.RIGHT_BRACKET.ordinal() ] = new char[] {']', '}'};
		keyChars[ Key.RETURN.ordinal() ] = new char[] {'\n', '\n'};
		keyChars[ Key.A.ordinal() ] = new char[] {'a', 'A'};
		keyChars[ Key.S.ordinal() ] = new char[] {'s', 'S'};
		keyChars[ Key.D.ordinal() ] = new char[] {'d', 'D'};
		keyChars[ Key.F.ordinal() ] = new char[] {'f', 'F'};
		keyChars[ Key.G.ordinal() ] = new char[] {'g', 'G'};
		keyChars[ Key.H.ordinal() ] = new char[] {'h', 'H'};
		keyChars[ Key.J.ordinal() ] = new char[] {'j', 'J'};
		keyChars[ Key.K.ordinal() ] = new char[] {'k', 'K'};
		keyChars[ Key.L.ordinal() ] = new char[] {'l', 'L'};
		keyChars[ Key.SEMICOLON.ordinal() ] = new char[] {';', ':'};
		keyChars[ Key.APOSTROPHE.ordinal() ] = new char[] {'\'', '"'};
		keyChars[ Key.BACKSLASH.ordinal() ] = new char[] {'\\', '|'};
		keyChars[ Key.Z.ordinal() ] = new char[] {'z', 'Z'};
		keyChars[ Key.X.ordinal() ] = new char[] {'x', 'X'};
		keyChars[ Key.C.ordinal() ] = new char[] {'c', 'C'};
		keyChars[ Key.V.ordinal() ] = new char[] {'v', 'V'};
		keyChars[ Key.B.ordinal() ] = new char[] {'b', 'B'};
		keyChars[ Key.N.ordinal() ] = new char[] {'n', 'N'};
		keyChars[ Key.M.ordinal() ] = new char[] {'m', 'M'};
		keyChars[ Key.COMMA.ordinal() ] = new char[] {',', '<'};
		keyChars[ Key.PERIOD.ordinal() ] = new char[] {'.', '>'};
		keyChars[ Key.SLASH.ordinal() ] = new char[] {'/', '?'};
		keyChars[ Key.MULTIPLY.ordinal() ] = new char[] {'*', '*'};
		keyChars[ Key.SPACE.ordinal() ] = new char[] {' ', ' '};
		keyChars[ Key.NUMPAD7.ordinal() ] = new char[] {'7', '7'};
		keyChars[ Key.NUMPAD8.ordinal() ] = new char[] {'8', '8'};
		keyChars[ Key.NUMPAD9.ordinal() ] = new char[] {'9', '9'};
		keyChars[ Key.SUBTRACT.ordinal() ] = new char[] {'-', '-'};
		keyChars[ Key.NUMPAD4.ordinal() ] = new char[] {'4', '4'};
		keyChars[ Key.NUMPAD5.ordinal() ] = new char[] {'5', '5'};
		keyChars[ Key.NUMPAD6.ordinal() ] = new char[] {'6', '6'};
		keyChars[ Key.ADD.ordinal() ] = new char[] {'+', '+'};
		keyChars[ Key.NUMPAD1.ordinal() ] = new char[] {'1', '1'};
		keyChars[ Key.NUMPAD2.ordinal() ] = new char[] {'2', '2'};
		keyChars[ Key.NUMPAD3.ordinal() ] = new char[] {'3', '3'};
		keyChars[ Key.NUMPAD0.ordinal() ] = new char[] {'0', '0'};
		keyChars[ Key.DECIMAL.ordinal() ] = new char[] {'.', '.'};
		keyChars[ Key.NUMPADEQUALS.ordinal() ] = new char[] {'=', '='};
		keyChars[ Key.AT.ordinal() ] = new char[] {'@', '@'};
		keyChars[ Key.COLON.ordinal() ] = new char[] {':', ':'};
		keyChars[ Key.UNDERLINE.ordinal() ] = new char[] {'_', '_'};
		keyChars[ Key.NUMPADENTER.ordinal() ] = new char[] {'\n', '\n'};
		keyChars[ Key.NUMPADCOMMA.ordinal() ] = new char[] {',', ','};
		keyChars[ Key.DIVIDE.ordinal() ] = new char[] {'/', '/'};
	}
	
	@Override
	public KeyState getKey(Key key) 
	{
		return states[ key.ordinal() ];
	}

	@Override
	public InputState getShift() 
	{
		return shift;
	}

	@Override
	public InputState getControl() 
	{
		return control;
	}

	@Override
	public InputState getMeta() 
	{
		return meta;
	}

	@Override
	public InputState getCommand() 
	{
		return command;
	}

	@Override
	public InputState getAlt() 
	{
		return alt;
	}
	
	@Override
	public Queue<KeyState> getQueue()
	{
		return queue;
	}

	@Override
	public void setPressInterval(float pressInterval) 
	{
		this.pressInterval = pressInterval;
	}

	@Override
	public float getPressInterval() 
	{		
		return pressInterval;
	}

	@Override
	public void setPressDelay(float pressDelay) 
	{
		this.pressDelay = pressDelay;
	}

	@Override
	public float getPressDelay() 
	{
		return pressDelay;
	}

}
