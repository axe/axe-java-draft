package com.axe.sprite;

import com.axe.core.Factory;
import com.axe.gfx.Texture;
import com.axe.math.Vec2f;
import com.axe.math.Vec;
import com.axe.node.RenderedNode;
import com.axe.tile.Tile;

public class SimpleSprite<V extends Vec<V>> extends RenderedNode<V> 
{
	
	public final V position;
	public final Vec2f size;
	public final Tile tile;
	
	protected boolean expired;
	
	public SimpleSprite(int id, Factory<V> factory)
	{
		super( id );
		
		this.position = factory.create();
		this.size = new Vec2f();
		this.tile = new Tile();
	}
	
	@Override
	public boolean isActivated()
	{
		assert tile.texture != null;
		
		return super.isActivated() && tile.texture.isActivated();
	}
	
	@Override
	public void activate()
	{
		if (!super.isActivated())
		{
			super.activate();
		}
		
		if (!tile.texture.isActivated())
		{
			tile.texture.activate();
		}
	}
	
	@Override
	public boolean isExpired()
	{
		return expired;
	}
	
	@Override
	public void expire()
	{
		expired = true;
	}
	
	public SimpleSprite<V> setPosition(V position)
	{
		this.position.set( position );
		
		return this;
	}
	
	public SimpleSprite<V> setSize(Vec2f size)
	{
		this.size.set( size );
		
		return this;
	}
	
	public SimpleSprite<V> setSize(float x, float y)
	{
		this.size.set( x, y );
		
		return this;
	}
	
	public SimpleSprite<V> setTile(Tile tile)
	{
		this.tile.set( tile );
		
		if ( this.size.isZero() )
		{
			this.size.x = this.tile.texture.getImageWidth();
			this.size.y = this.tile.texture.getImageHeight();
		}
		
		return this;
	}
	
	public SimpleSprite<V> setTexture(Texture texture)
	{
		return setTile( texture.tile() );
	}
	
	@Override
	public int hashCode()
	{
		return position.hashCode() ^ size.hashCode() ^ tile.hashCode();
	}
	
}