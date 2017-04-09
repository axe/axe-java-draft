package com.axe.game;

import com.axe.Scene;
import com.axe.collect.ListArray;
import com.axe.core.HasData;
import com.axe.event.HasEvents;
import com.axe.loop.Loop;
import com.axe.math.Vec;
import com.axe.window.Window;

public interface Game extends HasEvents, HasData
{
	
	public static final long DEFAULT_HIDDEN_SLEEP_TIME = 100;
	
	public Loop getLoop();

	public Game setLoop(Loop loop);

	public GameState getState();

	public ListArray<Scene> getScenes();

	public <V extends Vec<V>, S extends Scene<V>> S newScene(int dimensions);

	public ListArray<Window> getWindows();

	public Window newWindow();
	
	public Window getFocusedWindow();
	
	public boolean isPlaying();
	
	public long getHiddenSleepTime();
	
	public void setHiddenSleepTime(long hiddenSleepTime);

	public void show();
	
	public void run();

	public void stop();
	
}