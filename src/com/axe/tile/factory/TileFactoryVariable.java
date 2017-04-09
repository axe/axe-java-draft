package com.axe.tile.factory;

import java.util.ArrayList;
import java.util.List;

import com.axe.gfx.Texture;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Rect2i;
import com.axe.math.Vec2i;
import com.axe.tile.Tile;
import com.axe.tile.TileFactory;



/**
 * TODO
 * 
 * @author Philip Diffenderfer
 *
 */
public class TileFactoryVariable implements TileFactory
{

	// The list of frames.
	private List<Rect2i> frames = new ArrayList<Rect2i>();
	
	/**
	 * Creates a new array of tiles given the source texture. If the number of 
	 * offsets or the frame dimensions is less then one then null is returned.
	 * The tiles returned will all have the same dimensions but different
	 * locations according the the offsets given (in the same order added).
	 * For example:
	 * <pre>
	 * TileFactory f = new TileFactorySizeVariable();
	 * f.addFrame(0, 0, 3, 3);
	 * f.addFrame(6, 0, 6, 3);
	 * f.addFrame(2, 5, 4, 3);
	 * f.addFrame(5, 3, 4, 4);
	 * 
	 * Texture:
	 * x--------------x
	 * |+-+   +----+  |
	 * ||0|   |1   |  |
	 * |+-+   +----+  |
	 * |     +--+     |
	 * |     |3 |     |
	 * |  +--+  |     |
	 * |  |2 +--+     |
	 * |  +--+        |
	 * x--------------x
	 * 
	 * </pre>
	 */
	public Tile[] create(Texture ... textures) 
	{
		// If the number of offsets or the frame dimensions is less then 1
		// then this factory does not have sufficient information.
		if (frames.size() < 1)
			return null;
		
		int frameCount = frames.size();

		// A tile for each offset
		Tile[] tiles = new Tile[frameCount];
		
		// Start at the 0th frame.
		int frame = 0;
		Rect2i f;
		
		while (frame < frameCount)
		{
			f = frames.get( frame );
			
			// Create the tile based on the current offset
			tiles[frame] = textures[0].tile(f.x, f.y, f.x, f.y);
			
			// Increment the number of frames.
			frame++;
		}
		
		return tiles;
	}
	
	/**
	 * Clears all existing frame offsets from the factory.
	 */
	public void clear()
	{
		frames.clear();
	}
	
	/**
	 * Adds the offset of the frame to the factory list.
	 */
	public void addFrame(Vec2i offset, Vec2i size)
	{
		frames.add( new Rect2i(offset, size) );
	}
	
	/**
	 * Adds the offset of the frame to the factory list.
	 */
	public void addFrame(Vec2i offset, int width, int height)
	{
		frames.add( new Rect2i( offset.x, offset.y, width, height ) );
	}
	
	/**
	 * Adds the offset of the frame to the factory list.
	 */
	public void addFrame(int x, int y, int width, int height)
	{
		frames.add( new Rect2i( x, y, width, height) );
	}

	@Override
	public void read( InputModel input )
	{
		frames = input.readModelList( "frame", Rect2i.class );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModelList( "frame", frames );
	}

}
