package com.axe2d.sprite;

import com.axe.Axe;
import com.axe.game.GameState;
import com.axe.math.Numbers;
import com.axe.math.Vec2f;
import com.axe.sprite.Sprite;
import com.axe2d.node.Nodes2;

public class Sprite2 extends Sprite<Vec2f> 
{
	
	public static final int VIEW = Axe.renderers.create();
	public static final int VIEW_MANY = Axe.renderers.create();
	
	public static Nodes2<Sprite2> many()
	{
		Nodes2<Sprite2> sprites = new Nodes2<>();
		
		sprites.setRenderer( VIEW_MANY );
		
		return sprites;
	}
	
	public Sprite2()
	{
		super( VIEW, Vec2f.FACTORY );
	}
	
	@Override
	public boolean getBounds(GameState state, Vec2f min, Vec2f max)
	{
		float hw = size.x * scale.x;
		float hh = size.y * scale.y;
		
		min.x = -hw * anchor.x;
		min.y = -hh * anchor.y;
		max.x = hw * (1 - anchor.x);
		max.y = hh * (1 - anchor.y);
		
		if ( rotation.v != 0 )
		{
			float vx = Math.abs( Numbers.cos( rotation.v ) );
			float vy = Math.abs( Numbers.sin( rotation.v ) );
			float rw = Math.abs( vx * hw + vy * hh ) - hw;
			float rh = Math.abs( vx * hh + vy * hw ) - hh;

			min.x -= rw;
			min.y -= rh;
			max.x += rw;
			max.y += rh;
		}
		
		min.add( position );
		max.add( position );
		
		return true;
	}
		
}
