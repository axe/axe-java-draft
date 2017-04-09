package com.axe2d.sprite;

import com.axe.Axe;
import com.axe.game.GameState;
import com.axe.math.Vec2f;
import com.axe.sprite.SimpleSprite;
import com.axe2d.node.Nodes2;

public class SimpleSprite2 extends SimpleSprite<Vec2f> 
{
	
	public static final int VIEW = Axe.renderers.create();
	public static final int VIEW_MANY = Axe.renderers.create();
	
	public static Nodes2<SimpleSprite2> many()
	{
		Nodes2<SimpleSprite2> sprites = new Nodes2<>();
		
		sprites.setRenderer( VIEW_MANY );
		
		return sprites;
	}
	
	public SimpleSprite2()
	{
		super( VIEW, Vec2f.FACTORY );
	}
	
	@Override
	public boolean getBounds(GameState state, Vec2f min, Vec2f max)
	{
		min.set( position );
		max.set( position );
		max.add( size );
		
		return true;
	}
	
	public SimpleSprite2 setPosition(float x, float y)
	{
		this.position.set( x, y );
		
		return this;
	}
	
}
