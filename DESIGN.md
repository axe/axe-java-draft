# Design 2.0

## Concepts

- Node = a container or entity
- Scenes = contains a root node to draw & update
- Camera = a perspective in a scene
- View = a rectangular section on a window to render a scene from a camera's perspective
- Window = a place where one or more views are drawn
- Renderer = handles drawing a node
- Game = one or more windows

## Generic Types

- V = Vector
- A = Attribute
- T = any generic type
- E = enum
- I = input
- L = listener
- C = camera
- S = scene

## Structure

- Game
  - Scenes
  - Windows
    - Views
      - Camera
      - Scene
	
## Design

- Game
  - loop (the logic which defines how often updates & renders are performed)
  - state (time data since last update, updated by loop)
  - listeners (start, draw start, draw end, update start, update end, stop)
  - settings (ie. opengl settings)
  - input (key, mouse, controllers)
  - scenes
  - windows
  - `start()`
  - `stop()`
- Scene
  - root node
  - `draw(state, camera, view)`
  - `update(state, mouse, key)`
- Window
  - listeners (resize, focus, blur, close request, draw start, draw end)
  - state (active, hidden, request close)
  - state methods (stop, hide, show, minimize, maximize, focus)
  - title
  - fullscreen
  - position
  - dimension (pixel vs framebuffer)
  - resizable
  - vsync
  - frame rate
  - resolution (framebuffer dimensions / pixel)
  
## Implementation

```
start_game() {
	trigger game start events
	initialize loop algorithm
	initialize windows
	while (playing) {
		handle window events (request close, hiding)
		if all windows are closed, no longer playing
		if all windows hidden, pause X millis and continue
		handle window events (blur, focus)
		process input events
		update input systems
		run loop algorithm and determine how many updates are needed and if a draw is needed
		for each update needed {
			trigger game update start event
			update scenes (state, input)
			trigger game update end event
		}
		if updates needed {
			clear input systems
		}
		if draw needed {
			trigger game draw start event
			for each window {
				clear window buffer
				trigger window draw start event
				for each view {
					draw scene on view with camera's perspective
				}
				trigger window draw end event
				render window buffer
			}
			trigger game draw end event
		}
	}
	trigger game stop event
	destroy input
	destroy windows
	destroy game
}
```

## Example

```java
AxeLWJGL.init();

GameSettings gameSettings = new GameSettingsLWJGL(2, 1);
Game game = Axe.game.create( gameSettings );

Scene3 scene3 = new Scene3();
// populate scene3
game.addScene( scene3 );

Scene scene2 = new Scene2();
// populate scene2
game.addScene( scene2 );

WindowSettings windowSettings = new WindowSettings(640, 480);
Window window = game.newWindow( windowSettings );

Camera2 camera2 = new Camera2();
View view2 = window.newView(scene2, camera2);
view2.maximize();

Camera3 camera3 = new Camera3();
View view3 = window.newView(scene3, camera3);
view3.maximize();

```

### Ideas

- You can have objects which draw more frequently then they update - send interpolation data to draw method
- Handling Input
  - Every frame - accumulate input into input state.
  - Input is applied at a frequency (every update, or at X FPS)
  - Apply the input to the state then clear the input state
- InputState modifies next EntityState which affects DrawState
  
  
### Todo

- Fix mouse projection
- Finish calculator
- Modify Attribute by adding getCalculator and all default methods
X Implement default methods in Texture
X Implement TextureLWJGL
- Copy over Audio classes (implement AudioLWJGL)
- Copy over Font classes (implement FontLWJGL)
X Create Shader interface
X Create VertexBuffer interface
X Create ShaderLWJGL
X Create VertexBufferLWJGL Axe.graphics.newVertexBuffer(format, size)
	- formats will have an ID so vertex buffers can be reused
- Add Version class
- Files
- Net (http)
- Application (os, version, preferences, clipboard, 


- 2d hash scales = 1, 46340
- 3d hash scales = 1, 1290, 1664100
- 4d hash scales = 1, 215, 46225, 9938375
- 5d hash scales = 1, 73, 5404, 397336, 29210829
- 6d hash scales = 1, 36, 1296, 46656, 1679616, 60466176
- 8d hash scales = 1, 14, 196, 2744, 38416, 537824, 7529536, 105413504
- 9d hash scales = 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 