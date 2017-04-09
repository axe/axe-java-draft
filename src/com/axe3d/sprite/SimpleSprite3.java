package com.axe3d.sprite;

import com.axe.Axe;
import com.axe.game.GameState;
import com.axe.math.Vec3f;
import com.axe.sprite.SimpleSprite;

public class SimpleSprite3 extends SimpleSprite<Vec3f> 
{
	
	public static final int VIEW = Axe.renderers.create();
	
	public SimpleSprite3()
	{
		super( VIEW, Vec3f.FACTORY );
	}
	
	@Override
	public boolean getBounds(GameState state, Vec3f min, Vec3f max)
	{
		min.set( position );
		max.set( position );
		 
		// size is dependent on camera
		
		return false;
	}
	
	public SimpleSprite3 setPosition(float x, float y, float z)
	{
		this.position.set( x, y, z );
		
		return this;
	}
		
}
