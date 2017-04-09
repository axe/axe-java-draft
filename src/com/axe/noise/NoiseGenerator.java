package com.axe.noise;

import java.lang.reflect.Constructor;

import com.axe.io.DataModel;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;

public class NoiseGenerator implements DataModel
{

	public Noise noise;
	public long seed;
	public double scaleX;
	public double scaleY;
	public double scaleZ;
	public int octaves;
	public double frequency;
	public double amplitude;
	public boolean normalize;

	public NoiseGenerator()
	{
	}
	
	public NoiseGenerator( double scaleX, double scaleY, double scaleZ, int octaves, double frequency, double amplitude, boolean normalize )
	{
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		this.octaves = octaves;
		this.frequency = frequency;
		this.amplitude = amplitude;
		this.normalize = normalize;
	}
	
	private double scale( double min, double max, double n )
	{
		return (n + 1) * 0.5f * (max - min) + min;
	}
	
	public double noise(double min, double max, double x)
	{
		return scale( min, max, noise.noise( x * scaleX, octaves, frequency, amplitude, normalize ) );
	}

	public double noise(double min, double max, double x, double y)
	{
		return scale( min, max, noise.noise( x * scaleX, y * scaleY, octaves, frequency, amplitude, normalize ) );
	}
	
	public double noise(double min, double max, double x, double y, double z)
	{
		return scale( min, max, noise.noise( x * scaleX, y * scaleY, z * scaleZ, octaves, frequency, amplitude, normalize ) );
	}

	public float noisef(float min, float max, float x)
	{
		return (float)noise( min, max, x );
	}
	
	public float noisef(float min, float max, float x, float y)
	{
		return (float)noise( min, max, x, y );
	}
	
	public float noisef(float min, float max, float x, float y, float z)
	{
		return (float)noise( min, max, x, y, z );
	}

	@Override
	public void read( InputModel input )
	{
		seed = input.readLong( "seed", seed );
		scaleX = input.readDouble( "scale-x", scaleX );
		scaleY = input.readDouble( "scale-y", scaleY );
		scaleZ = input.readDouble( "scale-z", scaleZ );
		octaves = input.readInt( "octaves", octaves );
		frequency = input.readDouble( "frequency", frequency );
		amplitude = input.readDouble( "amplitude", amplitude );
		normalize = input.readBoolean( "normalize", normalize );
		
		try
		{
			Class<Noise> noiseClass = input.readClass( "noise" );
			Constructor<Noise> noiseConstructor = noiseClass.getConstructor( long.class );
			noise = noiseConstructor.newInstance( seed );
		}
		catch (Exception e)
		{
			if ( noise == null )
			{
				noise = SimplexNoise.getInstance();
			}
		}
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "seed", seed );
		output.write( "scale-x", scaleX );
		output.write( "scale-y", scaleY );
		output.write( "scale-z", scaleZ );
		output.write( "octaves", octaves );
		output.write( "frequency", frequency );
		output.write( "amplitude", amplitude );
		output.write( "normalize", normalize );
		output.writeInstance( "noise", noise );
	}
	
}
