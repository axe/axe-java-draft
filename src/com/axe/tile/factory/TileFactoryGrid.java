package com.axe.tile.factory;

import com.axe.gfx.Texture;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.tile.Tile;
import com.axe.tile.TileFactory;

/**
 * A factory that takes a texture and generates an array of immutable tiles
 * taken from the texture in a grid-like fashion.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TileFactoryGrid implements TileFactory 
{

	/**
	 * The offset (on the x-axis) of the grid on the texture in pixels
	 */
	public int x;
	
	/**
	 * The offset (on the y-axis) of the grid on the texture in pixels
	 */
	public int y;
	
	/**
	 * The number of columns on the grid.
	 */
	public int columns;
	
	/**
	 * The total number of frames on the grid.
	 */
	public int frames;
	
	/**
	 * The width of a tile on the grid in pixels.
	 */
	public int frameWidth;
	
	/**
	 * The height of a tile on the grid in pixels.
	 */
	public int frameHeight;
	
	/**
	 * Whether the tiles generated should lie on the pixel centers (false) or
	 * exactly on the pixel edge (true).
	 */
	public boolean exact = false;

	/**
	 * Instantiates a new TileFactoryGrid. 
	 */
	public TileFactoryGrid()
	{
		
	}

	/**
	 * Instantiates a new TileFactoryGrid.
	 * 
	 * @param x
	 * 		The offset (on the x-axis) of the grid on the texture in pixels
	 * @param y
	 * 		The offset (on the y-axis) of the grid on the texture in pixels
	 * @param columns
	 * 		The number of columns on the grid.
	 * @param frames
	 * 		The total number of frames on the grid.
	 * @param frameWidth
	 * 		The width of a tile on the grid in pixels.
	 * @param frameHeight
	 * 		The height of a tile on the grid in pixels.
	 */
	public TileFactoryGrid(int columns, int frames, int frameWidth, int frameHeight)
	{
		set(0, 0, columns, frames, frameWidth, frameHeight);
	}

	/**
	 * Instantiates a new TileFactoryGrid.
	 * 
	 * @param x
	 * 		The offset (on the x-axis) of the grid on the texture in pixels
	 * @param y
	 * 		The offset (on the y-axis) of the grid on the texture in pixels
	 * @param columns
	 * 		The number of columns on the grid.
	 * @param frames
	 * 		The total number of frames on the grid.
	 * @param frameWidth
	 * 		The width of a tile on the grid in pixels.
	 * @param frameHeight
	 * 		The height of a tile on the grid in pixels.
	 */
	public TileFactoryGrid(int x, int y, int columns, int frames, int frameWidth, int frameHeight)
	{
		set(x, y, columns, frames, frameWidth, frameHeight);
	}
	
	/**
	 * Sets the factories parameters.
	 * 
	 * @param x
	 * 		The offset (on the x-axis) of the grid on the texture in pixels
	 * @param y
	 * 		The offset (on the y-axis) of the grid on the texture in pixels
	 * @param columns
	 * 		The number of columns on the grid.
	 * @param frames
	 * 		The total number of frames on the grid.
	 * @param frameWidth
	 * 		The width of a tile on the grid in pixels.
	 * @param frameHeight
	 * 		The height of a tile on the grid in pixels.
	 */
	public void set(int x, int y, int columns, int frames, int frameWidth, int frameHeight)
	{
		this.x = x;
		this.y = y;
		this.columns = columns;
		this.frames = frames;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}
	
	/**
	 * Creates a new array of tiles given the source texture. If the number of
	 * columns, frames, or the frame dimensions are less then one then null is
	 * returned. The array of tiles returned will be translated from the grid
	 * where the upper left frame of the grid is the first tile and following 
	 * tiles are the following frames in the row, until a row is exhausted in 
	 * which case the row below is deemed the next row for traversal, until all 
	 * frames have been created. For example:
	 * <pre>
	 * TileFactory f = new TileFactoryGrid();
	 * f.x = 5;
	 * f.y = 2;
	 * f.frames = 13;
	 * f.columns = 5;
	 * f.frameWidth = 4;
	 * f.frameHeight = 3;
	 * 
	 * Texture:
	 * x-----------------------x
	 * |                       |
	 * |                       |
	 * |     +--+--+--+--+--+  |
	 * |     |0 |1 |2 |3 |4 |  |
	 * |     +--+--+--+--+--+  |
	 * |     |5 |6 |7 |8 |9 |  |
	 * |     +--+--+--+--+--+  |
	 * |     |10|11|12|        |
	 * |     +--+--+--+        |
	 * x-----------------------x
	 * </pre>
	 */
	@Override
	public Tile[] create(Texture... textures) 
	{
		// If the number of columns, frames, or frame dimensions is less then 1
		// then this factory does not have sufficient information.
		if (columns < 1 || frames < 1 || frameWidth < 1 || frameHeight  < 1)
			return null;
		
		// A tile for each frame
		Tile[] tiles = new Tile[frames];
		
		// Calculate the right side of the grid in pixels. 
		final int gridRight = (frameWidth * columns) + x;
		
		// Start at the 0th frame and with the given offset.
		int frame = 0;
		int frameX = x;
		int frameY = y;
		
		while (frame < frames)
		{
			// Create the tile based on the current frame
			if (exact)
			{
				tiles[frame++] = textures[0].tileExact(frameX, frameY, frameWidth, frameHeight);				
			}
			else
			{
				tiles[frame++] = textures[0].tile(frameX, frameY, frameWidth, frameHeight);
			}
			
			// Move the x-coordinate of the frame over by the width of a frame.
			frameX += frameWidth;
			
			// If the x-coordinate of the next frame is at the grid's right
			// coordinate then restart the x-coordinate and move down to the 
			// next row.
			if (frameX == gridRight)
			{
				frameX = x;
				frameY += frameHeight;
			}
		}
		
		return tiles;
	}

	@Override
	public void read( InputModel input )
	{
		x = input.readInt( "offset-x" );
		y = input.readInt( "offset-y" );
		columns = input.readInt( "columns" );
		frames = input.readInt( "frames" );
		frameWidth = input.readInt( "frame-width" );
		frameHeight = input.readInt( "frame-height" );
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "offset-x", x );
		output.write( "offset-y", y );
		output.write( "columns", columns );
		output.write( "frames", frames );
		output.write( "frame-width", frameWidth );
		output.write( "frame-height", frameHeight );
	}

}
