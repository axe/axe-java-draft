package com.axe.math;

import java.util.List;
import java.util.Random;


public class Numbers
{

	public static float EPSILON = 0.00001f;
	
	public static final Random random = new Random();

	public static final float PI = 3.14159265358979323846f;
	public static final float PI2 = PI * 2;
	public static final float PIHALF = PI * 0.5f;
	public static final float DEGREE_TO_RADIAN = PI / 180;
	public static final float RADIAN_TO_DEGREE = 180 / PI;
	
	public static final float[] RADIAN;
	public static final float[] COS;
	public static final float[] SIN;

	static 
	{
		RADIAN = new float[ 361 ];
		COS = new float[ 361 ];
		SIN = new float[ 361 ];
		for (int i = 0; i < 361; i++ ) {
			RADIAN[i] = i * DEGREE_TO_RADIAN;
			COS[i] = (float)StrictMath.cos( RADIAN[i] );
			SIN[i] = (float)StrictMath.sin( RADIAN[i] );
		}
	}
	
	public static float divide(float dividend, float divisor)
	{
		return divisor == 0 ? 0 : dividend / divisor;
	}
	
	public static int divide(int dividend, int divisor)
	{
		return divisor == 0 ? 0 : dividend / divisor;
	}
	
	public static float truncate(float value, float divisor)
	{
		return value - value % divisor;
	}
	
	public static int truncate(int value, int divisor)
	{
		return value - value % divisor;
	}
	
	public static float clamp(float value, float min, float max)
	{
		return value < min ? min : (value > max ? max : value);
	}
	
	public static int clamp(int value, int min, int max)
	{
		return value < min ? min : (value > max ? max : value);
	}
	
	public static void setRandomSeed( long randomSeed )
	{
		random.setSeed( randomSeed );
	}

	public static float randomFloat()
	{
		return random.nextFloat();
	}
	
	public static float randomFloat( float x )
	{
		return random.nextFloat() * x;
	}
	
	public static float randomFloat( float min, float max )
	{
		return random.nextFloat() * (max - min) + min;
	}
	
	public static int randomSign()
	{
		return (random.nextInt( 2 ) << 1) - 1;
	}
	
	public static int randomInt()
	{
		return random.nextInt();
	}
	
	public static int randomInt( int x )
	{
		return random.nextInt( x );
	}
	
	public static int randomInt( int min, int max )
	{
		return random.nextInt( max - min + 1 ) + min;
	}
	
	public static long randomLong()
	{
		return random.nextLong();
	}
	
	public static boolean randomBoolean()
	{
		return random.nextBoolean();
	}
	
	public static <E extends Enum<E>> E random( Class<E> enumClass )
	{
		return random( enumClass.getEnumConstants() );
	}
	
	public static <T> T random( T[] elements )
	{
		return elements[ random.nextInt( elements.length) ];
	}
	
	public static <T> T random( T[] elements, T returnOnNull )
	{
		return ( elements == null || elements.length == 0 ? returnOnNull : elements[ random.nextInt( elements.length) ] );
	}
	
	public static <T> T random( T[] elements, int min, int max, T returnOnNull )
	{
		return ( elements == null || elements.length == 0  ? returnOnNull : elements[ random.nextInt( max - min + 1) + min ] );
	}
	
	public static <T> T random( List<T> elements )
	{
		return elements.get( random.nextInt( elements.size() ) );
	}
	
	public static <T> T random( List<T> elements, int min, int max, T returnOnNull )
	{
		return ( elements == null || elements.size() == 0  ? returnOnNull : elements.get( random.nextInt( max - min + 1) + min ) );
	}
	
	public static float sqrt( float x )
	{
		return (float)StrictMath.sqrt( x );
	}
	
	public static float cos( float r ) 
	{
		return (float)StrictMath.cos( r );
	}
	
	public static float cos( float r, float scale ) 
	{
		return (float)StrictMath.cos( r ) * scale;
	}
	
	public static float sin( float r ) 
	{
		return (float)StrictMath.sin( r );
	}
	
	public static float acos( float r )
	{
		return (float)StrictMath.acos( r );
	}
	
	public static float sin( float r, float scale ) 
	{
		return (float)StrictMath.sin( r ) * scale;
	}
	
	public static float floor( float x )
	{
		return (float)StrictMath.floor( x );
	}
	
	public static float ceil( float x )
	{
		return (float)StrictMath.ceil( x );
	}
	
	public static boolean equals( float a, float b )
	{
		return equals( a, b, EPSILON );
	}
	
	public static boolean equals( float a, float b, float epsilon )
	{
		return Math.abs( a - b ) < epsilon;
	}

	public static void randomDirection(Rangef yaw, Rangef pitch, Vec3f out)
	{
		direction( yaw.rnd(), pitch.rnd(), out );
	}
	
	public static void direction(float yaw, float pitch, Vec3f out)
	{
		float tcos = cos( yaw );
		float tsin = sin( yaw );
		float rcos = cos( pitch );
		float rsin = sin( pitch );
		
		out.x = tcos * rcos;
		out.z = tsin * rcos;
		out.y = rsin;
	}
	
	public static int factorial(int x)
	{
		int n = x;
		while (--x >= 1) {
			n *= x;
		}
		return n;
	}
	
	// greatest common divisor, 32-bit integer
	public static int gcd( int a, int b ) 
	{
		int shift = 0;
		
		if ( a == 0 || b == 0 ) {
			return (a | b);
		}
		
		for (shift = 0; ((a | b) & 1) == 0; ++shift) {
			a >>= 1;
			b >>= 1;
		}
		
		while ((a & 1) == 0) {
			a >>= 1;
		}
		
		do {
			while ((b & 1) == 0) {
				b >>= 1;
			}
			if (a < b) {
				b -= a;
			} else {
				int d = a - b;
				a = b;
				b = d;
			}
			b >>= 1;
		} while (b != 0);
		
		return (a << shift);
	}

	// greatest common divisor, 64-bit integer
	public static long gcd( long a, long b ) 
	{
		int shift = 0;
		
		if ( a == 0 || b == 0 ) {
			return (a | b);
		}
		
		for (shift = 0; ((a | b) & 1) == 0; ++shift) {
			a >>= 1;
			b >>= 1;
		}
		
		while ((a & 1) == 0) {
			a >>= 1;
		}
		
		do {
			while ((b & 1) == 0) {
				b >>= 1;
			}
			if (a < b) {
				b -= a;
			} else {
				long d = a - b;
				a = b;
				b = d;
			}
			b >>= 1;
		} while (b != 0);
		
		return (a << shift);
	}
	
	// Calculates the combination of the given integer n and m. Un-ordered collection of distinct elements.
	// C(n,m) = n! / m!(n - m)!
	public static long choose(long n, long m) 
	{
		long num = 1, den = 1, gcd;
		
		if (m > (n >> 1)) {
			m = n - m;
		}
		
		while (m >= 1) 
		{
			num *= n--;
			den *= m--;
			gcd = gcd(num, den);
			num /= gcd;
			den /= gcd;
		}
		
		return num;
	}
	
	// Calculates the combination of the given integer n and m. Un-ordered collection of distinct elements.
	// C(n,m) = n! / m!(n - m)!
	public static int choose(int n, int m) 
	{
		int num = 1, den = 1, gcd;
		
		if (m > (n >> 1)) {
			m = n - m;
		}
		
		while (m >= 1) 
		{
			num *= n--;
			den *= m--;
			gcd = gcd(num, den);
			num /= gcd;
			den /= gcd;
		}
		
		return num;
	}
	
	public static float quadraticFormula(float a, float b, float c, float noSolution)
	{
		float t0 = Float.MIN_VALUE;
        float t1 = Float.MIN_VALUE;

        if ( Math.abs( a ) < EPSILON )
        {
            if ( Math.abs( b ) < EPSILON )
            {
                if ( Math.abs( c ) < EPSILON )
                {
                    t0 = 0.0f;
                    t1 = 0.0f;
                }
            }
            else
            {
                t0 = -c / b;
                t1 = -c / b;
            }
        }
        else
        {
            float disc = b * b - 4 * a * c;

            if ( disc >= 0 )
            {
                disc = sqrt( disc );
                a = 2 * a;
                t0 = (-b - disc) / a;
                t1 = (-b + disc) / a;
            }
        }

        if ( t0 != Float.MIN_VALUE )
        {
            float t = Math.min( t0, t1 );

            if ( t < 0 )
            {
                t = Math.max( t0, t1 );
            }

            if ( t > 0 )
            {
                return t;
            }
        }

        return noSolution;
	}

}
