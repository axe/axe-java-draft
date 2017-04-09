package com.axe.game;

import java.util.concurrent.TimeUnit;

import com.axe.Axe;
import com.axe.Scene;
import com.axe.collect.ListArray;
import com.axe.event.EventMap;
import com.axe.loop.Loop;
import com.axe.loop.LoopVariable;
import com.axe.math.Vec;
import com.axe.window.Window;
import com.axe2d.Scene2;
import com.axe3d.Scene3;

public abstract class AbstractGame implements Game 
{
	
	public EventMap events;
	public Loop loop;
	public GameState state;
	public ListArray<Scene> scenes;
	public ListArray<Window> windows;
	public Window focusedWindow;
	public boolean playing;
	public long hiddenSleepTime;
	public int liveWindows;
	public int hiddenWindows;
	public Object data;
	
	public AbstractGame()
	{
		this.events = new EventMap();
		this.loop = new LoopVariable(100, TimeUnit.MILLISECONDS);
		this.state = new GameState();
		this.scenes = ListArray.compacted();
		this.windows = ListArray.compacted();
		this.hiddenSleepTime = DEFAULT_HIDDEN_SLEEP_TIME;
	}
	
	@Override
	public EventMap getEvents() 
	{
		return events;
	}

	@Override
	public Loop getLoop() 
	{
		return loop;
	}

	@Override
	public Game setLoop(Loop loop) 
	{
		this.loop = loop;
		return this;
	}

	@Override
	public GameState getState() 
	{
		return state;
	}

	@Override
	public ListArray<Scene> getScenes() 
	{
		return scenes;
	}

	@Override
	public <T> T getData() 
	{
		return (T) data;
	}

	@Override
	public void setData(Object data) 
	{
		this.data = data;
	}

	@Override
	public <V extends Vec<V>, S extends Scene<V>> S newScene(int dimensions) 
	{
		Scene scene = null;
		
		switch (dimensions) {
		case 2: scene = new Scene2(); break;
		case 3: scene = new Scene3(); break;
		}
		
		if (scene == null)
		{
			throw new RuntimeException(dimensions + " is not a supported dimension");	
		}
		
		scenes.add( scene );
		
		return (S)scene;
	}

	@Override
	public ListArray<Window> getWindows() 
	{
		return windows;
	}
	
	@Override
	public boolean isPlaying()
	{
		return playing;
	}

	@Override
	public long getHiddenSleepTime()
	{
		return hiddenSleepTime;
	}

	@Override
	public void setHiddenSleepTime(long hiddenSleepTime)
	{
		this.hiddenSleepTime = hiddenSleepTime;
	}
	
	@Override
	public void stop()
	{
		playing = false;
	}
	
	@Override
	public Window getFocusedWindow()
	{
		return focusedWindow;
	}
	
	@Override
	public void show()
	{
		windows.forAll(window -> window.show());
	}
	
	@Override
	public void run()
	{
		show();
		
		listeners(GameEvent.Start).onEvent(this);
		
		loop.onStart(state);
		
		prepare();
		
		scenes.forAll( scene -> scene.activate() );
		
		playing = true;
		
		while (playing)
		{
			liveWindows = 0;
			hiddenWindows = 0;
			
			windows.forAll((window)-> 
			{				
				if ( window.isCloseRequest() ) 
				{
					window.close();
				}
				else 
				{
					liveWindows++;
					
					if ( window.isHidden() ) 
					{
						hiddenWindows++;
					}
					else if ( window.isFocused() )
					{
						focusedWindow = window;
					}
				}
			});
			
			if ( liveWindows == 0 )
			{
				break;
			}
			
			if ( hiddenWindows == liveWindows ) 
			{
				sleep();
				
				continue;
			}
			
			windows.forAll( window -> window.update() );
			
			processInput();
			
			loop.onLoop( state );
			
			if ( state.updates > 0 )
			{
				windows.forAll( window -> window.updateMouse() );
				
				for (int i = 0; i < state.updates; i++)
				{
					listeners( GameEvent.UpdateStart ).onEvent( this );
					
					scenes.forAll( scene -> scene.update( state ) );
					
					listeners( GameEvent.UpdateEnd ).onEvent( this );
				}

				flushInput();
			}
			
			if ( state.draw )
			{
				listeners( GameEvent.DrawStart ).onEvent( this );
				
				windows.forAll(window -> 
				{
					if ( !window.isHidden() ) 
					{
						window.draw( state );
					}
				});
				
				listeners( GameEvent.DrawEnd ).onEvent( this );
			}
		}
		
		listeners( GameEvent.Stop ).onEvent( this );
		
		destroy();
	}	
	
	protected void sleep()
	{
		try
		{
			Thread.sleep( hiddenSleepTime );
		}
		catch (InterruptedException e)
		{
			Axe.logger.log(e);
		}
	}
	
	protected abstract void prepare();
	
	protected abstract void processInput();
	
	protected abstract void flushInput();
	
	protected abstract void destroy();

}
