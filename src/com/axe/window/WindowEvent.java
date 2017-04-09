package com.axe.window;

import com.axe.event.Event;

public class WindowEvent 
{
	
	public static final Event<WindowResizeEventListener> Resize = 
		new Event<>(0, WindowResizeEventListener.class);
	
	public static final Event<WindowEventListener> Focus = 
		new Event<>(1, WindowEventListener.class);
	
	public static final Event<WindowEventListener> Blur = 
		new Event<>(2, WindowEventListener.class);
	
	public static final Event<WindowEventListener> CloseRequest = 
		new Event<>(3, WindowEventListener.class);
	
	public static final Event<WindowEventListener> DrawStart = 
		new Event<>(4, WindowEventListener.class);
	
	public static final Event<WindowEventListener> DrawEnd = 
		new Event<>(5, WindowEventListener.class);
	
	public static final Event<WindowEventListener> Minimized = 
		new Event<>(6, WindowEventListener.class);
	
	public static final Event<WindowEventListener> Restored = 
		new Event<>(7, WindowEventListener.class);
	
	public static final Event<WindowEventListener> Maximized = 
		new Event<>(8, WindowEventListener.class);
	
	public static final Event<WindowEventListener> MouseLeave = 
		new Event<>(9, WindowEventListener.class);
	
	public static final Event<WindowEventListener> MouseEnter = 
		new Event<>(10, WindowEventListener.class);
	
}