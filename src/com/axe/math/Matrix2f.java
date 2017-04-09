package com.axe.math;

public class Matrix2f 
{
	public static final Matrix2f IDENTITY = new Matrix2f();
	
	public float transX; 
	public float transY; 
	public float shearX; 
	public float shearY; 
	public float scaleX; 
	public float scaleY; 

	public Matrix2f()
	{
		set(1, 1, 0, 0, 0, 0);
	}
	
	public Matrix2f(Matrix2f m)
	{
		set(m);
	}
	
	public Matrix2f(float scaleX, float scaleY, float shearX, float shearY, float transX, float transY)
	{
		set(scaleX, scaleY, shearX, shearY, transX, transY);
	}
	
	public void set(float scaleX, float scaleY, float shearX, float shearY, float transX, float transY)
	{
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.shearX = shearX;
		this.shearY = shearY;
		this.transX = transX;
		this.transY = transY;
	}
	
	public void set(Matrix2f m)
	{
		set(m.scaleX, m.scaleY, m.shearX, m.shearY, m.transX, m.transY);
	}
	
	public void setIdentity()
	{
		scaleX = scaleY = 1f;
		shearX = shearY = transX = transY = 0f;
	}
	
	public void setTranslation( Vec2f v )
	{
		setTranslation( v.x, v.y );
	}
	
	public void setTranslation(float x, float y)
	{
		scaleX = scaleY = 1f;
		shearX = shearY = 0f;
		transX = x;
		transY = y;
	}
	
	public void setScaling( Vec2f scale )
	{
		setScaling( scale.x, scale.y );
	}
	
	public void setScaling(float x, float y)
	{
		scaleX = x;
		scaleY = y;
		shearX = shearY = transX = transY = 0f;
	}
	
	public void setRadians( Scalarf radians )
	{
		setRadians( radians.v );
	}
	
	public void setRadians(float radians)
	{
		float cos = Numbers.cos( radians );
		float sin = Numbers.sin( radians );
		
		scaleX = scaleY = cos;
		shearX = -(shearY = sin);
		transX = transY = 0f;
	}
	
	public void setDegrees( Scalarf angle )
	{
		setDegrees( angle.v );
	}
	
	public void setDegrees(float angle)
	{
		setRadians( angle * Numbers.DEGREE_TO_RADIAN );
	}
	
	public void setShear( Vec2f shear )
	{
		setShear( shear.x, shear.y );
	}
	
	public void setShear(float x, float y)
	{
		scaleX = scaleY = 1f;
		shearX = x;
		shearY = y;
		transX = transY = 0f;
	}
	
	public void transform(Vec2f inout)
	{
		transform(inout.x, inout.y, inout);
	}
	
	public Vec2f transform(Vec2f in, Vec2f out)
	{
		transform(in.x, in.y, out);
		
		return out;
	}
	
	public void transform(float x, float y, Vec2f out)
	{
		out.x = (scaleX * x) + (shearX * y) + transX;
		out.y = (shearY * x) + (scaleY * y) + transY;
	}
	
	public void transform(Vec2f[] in, Vec2f[] out)
	{
		for (int i = in.length - 1; i >= 0; i--)
		{
			if (out[i] == null) {
				out[i] = new Vec2f();
			}
			transform( in[i].x, in[i].y, out[i] );
		}
	}
	
	public Vec2f[] transform(Vec2f[] in)
	{
		Vec2f[] out = new Vec2f[ in.length ];
		transform( in, out );
		return out;
	}
	
	public void mul(Matrix2f t)
	{
		float scX, scY, shX, shY, trX, trY;

		scX = (scaleX * t.scaleX) + (shearY * t.shearX);
		scY = (shearX * t.shearY) + (scaleY * t.scaleY);
		shX = (shearX * t.scaleX) + (scaleY * t.shearX);
		shY = (scaleX * t.shearY) + (shearY * t.scaleY);
		trX = (transX * t.scaleX) + (transY * t.transX) + t.transX;
		trY = (transX * t.shearY) + (transY * t.transY) + t.transY;

		set(scX, scY, shX, shY, trX, trY);
	}
	
	public void set(float angle, float scaleX, float scaleY, float translationX, float translationY)
	{
		if (angle != 0)
		{
			float cos = Numbers.cos(angle);
			float sin = Numbers.sin(angle);

			this.scaleX = scaleX * cos;
			this.scaleY = scaleY * cos;
			this.shearX = scaleY * sin;
			this.shearY = scaleX * sin;
			this.transX = translationX;
			this.transY = translationY;
		}
		else
		{
			this.scaleX = scaleX;
			this.scaleY = scaleY;
			this.shearX = this.shearY = 0f;
			this.transX = translationX;
			this.transY = translationY;
		}
	}
	
	public void set(float angle, Vec2f scale, Vec2f translation)
	{
		set(angle, scale.x, scale.y, translation.x, translation.y);
	}
	
	public void translate( Vec2f v )
	{
		translate( v.x, v.y );
	}
	
	public void translate(float x, float y)
	{
		transX += x;
		transY += y;
	}
	
	public void scale( Vec2f scale )
	{
		scale( scale.x, scale.y );
	}
	
	public void scale(float x, float y)
	{
		scaleX *= x;
		scaleY *= y;
		shearX *= x;
		shearY *= y;
		transX *= x;
		transY *= y;
	}

	public void shear(Vec2f shear)
	{
		shearX( shear.x );
		shearY( shear.y );
	}

	public void shearX( Scalarf x )
	{
		shearX( x.v );
	}
	
	public void shearX(float x)
	{
		scaleX += shearY * x;
		shearX += scaleY * x;
		transX += transY * x;
	}
	
	public void shearY( Scalarf y )
	{
		shearY( y.v );
	}
	
	public void shearY(float y)
	{
		shearY += scaleX * y;
		scaleY += shearX * y;
		transY += transX * y;
	}
	
	public void rotate( Scalarf angle )
	{
		rotate( angle.v );
	}
	
	public void rotate(float angle)
	{
		if (angle == 0)
			return;
		
		float cos = Numbers.cos(angle);
		float sin = Numbers.sin(angle);
		float scX, scY, shX, shY, trX, trY;

		scX = (scaleX * cos) - (shearY * sin);
		scY = (shearX * sin) + (scaleY * cos);
		shX = (shearX * cos) - (scaleY * sin);
		shY = (scaleX * sin) + (shearY * cos);
		trX = (transX * cos) - (transY * sin);
		trY = (transX * sin) + (transY * cos);
		
		set(scX, scY, shX, shY, trX, trY);
	}
	
	public void rotate( Scalarf angle, Vec2f origin )
	{
		rotate( angle.v, origin.x, origin.y );
	}
	
	public void rotate(float angle, Vec2f origin)
	{
		rotate( angle, origin.x, origin.y );
	}
	
	public void rotate(float angle, float originX, float originY)
	{
		if (angle != 0)
		{
			translate(-originX, -originY);
			rotate(angle);
			translate(originX, originY);	
		}
	}
	
	public void invert()
	{
		float det = determinant();
		
		if (det == 0f)
			return;
		
		float detInv = 1f / det;
		float scX, scY, shX, shY, trX, trY;
		                          
		scX = scaleY * detInv; 
		scY = scaleX * detInv;
		shX = -shearX * detInv;
		shY = -shearY * detInv;
		trX = (shearX * transY - scaleY * transX) * detInv;
		trY = (shearY * transX - scaleX * transY) * detInv; 

		set(scX, scY, shX, shY, trX, trY);
	}
	
	public float determinant()
	{
		return (scaleX * scaleY - shearY * shearX);
	}
	
}
