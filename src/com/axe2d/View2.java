package com.axe2d;

import com.axe.AbstractView;
import com.axe.Camera;
import com.axe.Scene;
import com.axe.gfx.ProjectionOutside;
import com.axe.math.Bound2i;
import com.axe.math.Numbers;
import com.axe.math.Scalarf;
import com.axe.math.Vec2f;
import com.axe.math.Vec2i;
import com.axe.window.Window;
import com.axe2d.math.Matrix2;

public abstract class View2 extends AbstractView<Vec2f>
{
	
	protected static final Bound2i BOUNDS = new Bound2i(); 
	protected static final Vec2f HALF = new Vec2f( 0.5f );

	public Scene2 scene;
	public Camera2 camera;
	public Matrix2 projection;
	public Matrix2 view;
	public Matrix2 combined;
	public Matrix2 inverted;
	
	public View2(Window window, Scene2 scene)
	{
		super( window );
		
		this.scene = scene;
		this.projection = new Matrix2();
		this.view = new Matrix2();
		this.combined = new Matrix2();
		this.inverted = new Matrix2();
		this.camera = new Camera2();
		this.camera.size.set( 
			this.placement.getWidth( window.getWidth() ), 
			this.placement.getHeight( window.getHeight() ) 
		);
	}
	
	@Override
	public Matrix2 getProjectionMatrix()
	{
		return projection;
	}
	
	@Override
	public Matrix2 getViewMatrix()
	{
		return view;
	}
	
	@Override
	public Matrix2 getCombinedMatrix()
	{
		return combined;
	}
	
	@Override
	public void update()
	{
		final Vec2f size = camera.size;
		final Vec2f halfSizeScaled = camera.halfSizeScaled;
		final Vec2f scale = camera.scale;
		final Vec2f center = camera.center;
		final Scalarf roll = camera.roll;
		
		projection.ortho( 0, 0, size.x, size.y );
		
		view.identity();
		view.translatei( halfSizeScaled );
		view.rotatei( roll.v );
		view.scalei( scale );
		view.translatei( -center.x, -center.y );
		
		combined.set( projection );
		combined.multiplyi( view );
		combined.invert( inverted );
	}
	

	@Override
	public Vec2f project(Vec2i mouse, ProjectionOutside outside, Vec2f out)
	{
		final int windowWidth = window.getWidth();
		final int windowHeight = window.getHeight();
		final Bound2i view = placement.getBounds( windowWidth, windowHeight, BOUNDS );
		
		out.x = (float)(mouse.x - view.l) / view.width();
		out.y = (float)((windowHeight - mouse.y) - view.b) / view.height();
		
		if (!inProjection(out, outside))
		{
			return null;
		}
		
		out.sub( HALF );
		out.div( HALF );
		out.mul( camera.halfSizeScaled );
		out.rotate( camera.right );
		out.add( camera.center );
		
		return out;
	}
	
	@Override
	public Vec2f unproject(Vec2f point, ProjectionOutside outside, Vec2f out)
	{
		final int windowWidth = window.getWidth();
		final int windowHeight = window.getHeight();
		final Bound2i view = placement.getBounds( windowWidth, windowHeight, BOUNDS );
		
		out.set( point );
		out.sub( camera.center );
		out.rotater( camera.right );
		out.div( camera.halfSizeScaled );
		out.mul( HALF );
		out.add( HALF );
		
		if (!inProjection(out, outside))
		{
			return null;
		}
		
		out.x = ((out.x * view.width()) + view.l);
		out.y = windowHeight - (out.y * view.height() + view.b);
		
		return out;
	}
	
	protected boolean inProjection(Vec2f out, ProjectionOutside outside)
	{
		if (out.x < 0 || out.y < 0 || out.x >= 1 || out.y >= 1)
		{
			switch (outside) {
			case Ignore:
				return false;
			case Clamp:
				out.x = Numbers.clamp( out.x, 0, 1 );
				out.y = Numbers.clamp( out.y, 0, 1 );
				break;
			case Relative:
				// No action necessary
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public <S extends Scene<Vec2f>> S getScene() 
	{
		return (S)scene;
	}

	@Override
	public <C extends Camera<Vec2f>> C getCamera() 
	{
		return (C)camera;
	}


}
