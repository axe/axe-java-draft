package com.axe3d;

import com.axe.AbstractView;
import com.axe.Camera;
import com.axe.Scene;
import com.axe.gfx.ProjectionOutside;
import com.axe.math.Bound2i;
import com.axe.math.Numbers;
import com.axe.math.Scalarf;
import com.axe.math.Vec2i;
import com.axe.math.Vec3f;
import com.axe.window.Window;
import com.axe3d.math.Matrix3;

public abstract class View3 extends AbstractView<Vec3f>
{

	protected static final Bound2i BOUNDS = new Bound2i(); 
	
	public Scene3 scene;
	public Camera3 camera;
	public Matrix3 projection;
	public Matrix3 view;
	public Matrix3 combined;
	public Matrix3 inverted;
	
	public View3(Window window, Scene3 scene)
	{
		super( window );
		
		this.scene = scene;
		this.camera = new Camera3();
		this.projection = new Matrix3();
		this.view = new Matrix3();
		this.combined = new Matrix3();
	}
	
	@Override
	public Matrix3 getProjectionMatrix()
	{
		return projection;
	}
	
	@Override
	public Matrix3 getViewMatrix()
	{
		return view;
	}
	
	@Override
	public Matrix3 getCombinedMatrix()
	{
		return combined;
	}
	
	@Override
	public void update()
	{
		final float parentWidth = window.getWidth();
		final float parentHeight = window.getHeight();
		final float viewWidth = placement.getWidth( parentWidth );
		final float viewHeight = placement.getHeight( parentHeight );
		final Vec3f position = camera.position;
		final Vec3f forward = camera.forward;
		final Vec3f right = camera.right;
		final Vec3f up = camera.up;
		final Scalarf near = camera.near;
		final Scalarf far = camera.far;
		final Scalarf fov = camera.fov;
		
		projection.perspective( fov.v, viewWidth / viewHeight, near.v, far.v );
		
		view.lookAt( position, forward, up, right );
		
		combined.set( projection );
		combined.multiplyi( view );
		combined.invert( inverted );
	}

	@Override
	public Vec3f project(Vec2i mouse, ProjectionOutside outside, Vec3f out)
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
		
		// TODO
		
		return out;
	}
	
	@Override
	public Vec3f unproject(Vec3f point, ProjectionOutside outside, Vec3f out)
	{
		final int windowWidth = window.getWidth();
		final int windowHeight = window.getHeight();
		final Bound2i view = placement.getBounds( windowWidth, windowHeight, BOUNDS );
		
		// TODO
		
		if (!inProjection(out, outside))
		{
			return null;
		}
		
		out.x = ((out.x * view.width()) + view.l);
		out.y = windowHeight - (out.y * view.height() + view.b);
		
		return out;
	}
	
	protected boolean inProjection(Vec3f out, ProjectionOutside outside)
	{
		if (out.x < 0 || out.y < 0 || out.x >= 1 || out.y >= 1 || out.z < 0 || out.z >= 1)
		{
			switch (outside) {
			case Ignore:
				return false;
			case Clamp:
				out.x = Numbers.clamp( out.x, 0, 1 );
				out.y = Numbers.clamp( out.y, 0, 1 );
				out.z = Numbers.clamp( out.z, 0, 1 );
				break;
			case Relative:
				// No action necessary
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public <S extends Scene<Vec3f>> S getScene() 
	{
		return (S)scene;
	}

	@Override
	public <C extends Camera<Vec3f>> C getCamera() 
	{
		return (C)camera;
	}


}
