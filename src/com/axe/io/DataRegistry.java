package com.axe.io;

import java.util.Map;

import com.axe.Axe;
import com.axe.color.Color;
import com.axe.color.Colors;
import com.axe.easing.Easings;
import com.axe.gfx.Blend;
import com.axe.gfx.Coord;
import com.axe.math.Bound2f;
import com.axe.math.Bound2i;
import com.axe.math.Bound3f;
import com.axe.math.Bound3i;
import com.axe.math.Rangef;
import com.axe.math.Rangei;
import com.axe.math.Rect2i;
import com.axe.math.Scalarf;
import com.axe.math.Scalari;
import com.axe.math.Vec2f;
import com.axe.math.Vec2i;
import com.axe.math.Vec3f;
import com.axe.math.Vec3i;
import com.axe.noise.PerlinNoise;
import com.axe.noise.SimplexNoise;
import com.axe.path.AdditivePath;
import com.axe.path.BezierPath;
import com.axe.path.ComboPath;
import com.axe.path.CompiledPath;
import com.axe.path.CubicPath;
import com.axe.path.DurationPath;
import com.axe.path.EasedPath;
import com.axe.path.IntegralPath;
import com.axe.path.JumpPath;
import com.axe.path.LinearPath;
import com.axe.path.PointPath;
import com.axe.path.QuadraticPath;
import com.axe.path.ScaledPath;
import com.axe.path.SubPath;
import com.axe.path.TimedPath;
import com.axe.path.Tween;
import com.axe.path.UniformPath;
import com.axe.tile.Tile;

public class DataRegistry
{
	
	public static final RegistryMap<String, Object> objects = new RegistryMap<String, Object>();
	public static final RegistryMap<String, Class<?>> classes = new RegistryMap<String, Class<?>>();
	
	public static void register(String name, Class<?> clazz)
	{
		classes.put( name, clazz );
	}
	
	public static void registerClasses(Map<String, Class<?>> classMap)
	{
		classes.putAll( classMap );
	}
	
	public static void register(String name, Object object)
	{
		objects.put( name, object );
	}
	
	public static void registerObjects(Map<String, ?> objectMap)
	{
		objects.putAll( objectMap );
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(String name)
	{
		return (Class<T>)classes.getForward( name );
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String className, boolean throwRuntimeException)
	{
		try
		{
			return (T)classes.getForward( className ).newInstance();	
		}
		catch (Exception e)
		{
			if ( throwRuntimeException )
			{
				throw new RuntimeException( e );
			}
			
			Axe.logger.log( null, e );
			
			return null;
		}
	}
	
	public static String getClassName(Class<?> clazz)
	{
		return classes.getBackward( clazz );
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String name)
	{
		return (T)objects.getForward( name );
	}
	
	public static String getName(Object value)
	{
		return objects.getBackward( value );
	}
	
	static
	{
		// Paths
		register( "path-bezier", BezierPath.class );
		register( "path-combo", ComboPath.class );
		register( "path-compiled", CompiledPath.class );
		register( "path-cubic", CubicPath.class );
		register( "path-duration", DurationPath.class );
		register( "path-eased", EasedPath.class );
		register( "path-integral", IntegralPath.class );
		register( "path-jump", JumpPath.class );
		register( "path-linear", LinearPath.class );
		register( "path-point", PointPath.class );
		register( "path-quadratic", QuadraticPath.class );
		register( "path-sub", SubPath.class );
		register( "path-timed", TimedPath.class );
		register( "path-tween", Tween.class );
		register( "path-uniform", UniformPath.class );
		register( "path-scaled", ScaledPath.class );
		register( "path-additive", AdditivePath.class );
		
		/*
		// Particle Influences 2d
		register( "influence2d-acceleration", InfluenceAcceleration.class );
		register( "influence2d-align", InfluenceAlign.class );
		register( "influence2d-alpha", InfluenceAlpha.class );
		register( "influence2d-angle", InfluenceAngle.class );
		register( "influence2d-color", InfluenceColor.class );
		register( "influence2d-constraint", InfluenceConstraint.class );
		register( "influence2d-damping", InfluenceDamping.class );
		register( "influence2d-scale", InfluenceScale.class );
		register( "influence2d-size", InfluenceSize.class );
		register( "influence2d-tile", InfluenceTile.class );
		register( "influence2d-velocity", InfluenceVelocity.class );
		
		// Particle Initializers 2d
		register( "initialize2d-acceleration", InitializeAcceleration.class );
		register( "initialize2d-acceleration-scalar", InitializeAccelerationScalar.class );
		register( "initialize2d-angle", InitializeAngle.class );
		register( "initialize2d-angle-acceleration", InitializeAngleAcceleration.class );
		register( "initialize2d-angle-velocity", InitializeAngleVelocity.class );
		register( "initialize2d-color", InitializeColor.class );
		register( "initialize2d-scale", InitializeScale.class );
		register( "initialize2d-scale-acceleration", InitializeScaleAcceleration.class );
		register( "initialize2d-scale-velocity", InitializeScaleVelocity.class );
		register( "initialize2d-size", InitializeSize.class );
		register( "initialize2d-tile", InitializeTile.class );

		// Particle Velocity 2d
		register( "velocity2d-default", VelocityDefault.class );
		register( "velocity2d-directional", VelocityDirectional.class );
		register( "velocity2d-ortho", VelocityOrtho.class );
		register( "velocity2d-outward", VelocityOutward.class );
		register( "velocity2d-towards", VelocityTowards.class );
		
		// Particle Volume 2d
		register( "volume2d-bounds", VolumeBounds.class );
		register( "volume2d-default", VolumeDefault.class );
		register( "volume2d-ellipse", VolumeEllipse.class );
		register( "volume2d-path", VolumePath.class );
		register( "volume2d-pinwheel", VolumePinwheel.class );
		
		// Particle Factory 2d
		register( "particle-factory2d-angle", AngleParticle.factory );
		register( "particle-factory2d-Base", BaseParticle.factory );
		register( "particle-factory2d-color", ColorParticle.factory );
		register( "particle-factory2d-colortile", ColorTileParticle.factory );
		register( "particle-factory2d-full", FullParticle.factory );
		register( "particle-factory2d-physics", PhysicsParticle.factory );
		register( "particle-factory2d-tile", TileParticle.factory );
		register( "particle-factory2d-twin", TwinParticle.factory );

		// Particle Renderer 2d
		register( "particle-renderer2d-angle", AngleParticle.VIEW );
		register( "particle-renderer2d-Base", BaseParticle.VIEW );
		register( "particle-renderer2d-color", ColorParticle.VIEW );
		register( "particle-renderer2d-colortile", ColorTileParticle.VIEW );
		register( "particle-renderer2d-full", FullParticle.VIEW );
		register( "particle-renderer2d-physics", PhysicsParticle.VIEW );
		register( "particle-renderer2d-tile", TileParticle.VIEW );
		register( "particle-renderer2d-twin", TwinParticle.VIEW );
		
		// Particle Influences 3d
		register( "influence3d-acceleration", com.axe3d.efx.influence.InfluenceAcceleration.class );
		register( "influence3d-alpha", com.axe3d.efx.influence.InfluenceAlpha.class );
		register( "influence3d-angle", com.axe3d.efx.influence.InfluenceAngle.class );
		register( "influence3d-color", com.axe3d.efx.influence.InfluenceColor.class );
		register( "influence3d-constraint", com.axe3d.efx.influence.InfluenceConstraint.class );
		register( "influence3d-damping", com.axe3d.efx.influence.InfluenceDamping.class );
		register( "influence3d-scale", com.axe3d.efx.influence.InfluenceScale.class );
		register( "influence3d-size", com.axe3d.efx.influence.InfluenceSize.class );
		register( "influence3d-tile", com.axe3d.efx.influence.InfluenceTile.class );
		register( "influence3d-velocity", com.axe3d.efx.influence.InfluenceVelocity.class );
		
		// Particle Initializers 3d
		register( "initialize3d-acceleration", com.axe3d.efx.initializer.InitializeAcceleration.class );
		register( "initialize3d-acceleration-scalar", com.axe3d.efx.initializer.InitializeAccelerationScalar.class );
		register( "initialize3d-angle", com.axe3d.efx.initializer.InitializeAngle.class );
		register( "initialize3d-angle-acceleration", com.axe3d.efx.initializer.InitializeAngleAcceleration.class );
		register( "initialize3d-angle-velocity", com.axe3d.efx.initializer.InitializeAngleVelocity.class );
		register( "initialize3d-color", com.axe3d.efx.initializer.InitializeColor.class );
		register( "initialize3d-scale", com.axe3d.efx.initializer.InitializeScale.class );
		register( "initialize3d-scale-acceleration", com.axe3d.efx.initializer.InitializeScaleAcceleration.class );
		register( "initialize3d-scale-velocity", com.axe3d.efx.initializer.InitializeScaleVelocity.class );
		register( "initialize3d-size", com.axe3d.efx.initializer.InitializeSize.class );
		register( "initialize3d-tile", com.axe3d.efx.initializer.InitializeTile.class );

		// Particle Velocity 3d
		register( "velocity3d-default", com.axe3d.efx.velocity.VelocityDefault.class );
		register( "velocity3d-directional", com.axe3d.efx.velocity.VelocityDirectional.class );
		register( "velocity3d-ortho", com.axe3d.efx.velocity.VelocityOrtho.class );
		register( "velocity3d-outward", com.axe3d.efx.velocity.VelocityOutward.class );
		register( "velocity3d-towards", com.axe3d.efx.velocity.VelocityTowards.class );
		
		// Particle Volume 3d
		register( "volume3d-bounds", com.axe3d.efx.volume.VolumeBounds.class );
		register( "volume3d-default", com.axe3d.efx.volume.VolumeDefault.class );
		register( "volume3d-ellipse", com.axe3d.efx.volume.VolumeEllipse.class );
		register( "volume3d-path", com.axe3d.efx.volume.VolumePath.class );

		// Particle Factory 3d
		register( "particle-factory3d-angle", com.axe3d.efx.particle.AngleParticle.factory );
		register( "particle-factory3d-Base", com.axe3d.efx.particle.BaseParticle.factory );
		register( "particle-factory3d-color", com.axe3d.efx.particle.ColorParticle.factory );
		register( "particle-factory3d-colortile", com.axe3d.efx.particle.ColorTileParticle.factory );
		register( "particle-factory3d-full", com.axe3d.efx.particle.FullParticle.factory );
		register( "particle-factory3d-physics", com.axe3d.efx.particle.PhysicsParticle.factory );
		register( "particle-factory3d-tile", com.axe3d.efx.particle.TileParticle.factory );
		register( "particle-factory3d-twin", com.axe3d.efx.particle.TwinParticle.factory );
		
		// Particle Renderer 3d
		register( "particle-renderer3d-angle", com.axe3d.efx.particle.AngleParticle.VIEW );
		register( "particle-renderer3d-Base", com.axe3d.efx.particle.BaseParticle.VIEW );
		register( "particle-renderer3d-color", com.axe3d.efx.particle.ColorParticle.VIEW );
		register( "particle-renderer3d-colortile", com.axe3d.efx.particle.ColorTileParticle.VIEW );
		register( "particle-renderer3d-full", com.axe3d.efx.particle.FullParticle.VIEW );
		register( "particle-renderer3d-physics", com.axe3d.efx.particle.PhysicsParticle.VIEW );
		register( "particle-renderer3d-tile", com.axe3d.efx.particle.TileParticle.VIEW );
		register( "particle-renderer3d-twin", com.axe3d.efx.particle.TwinParticle.VIEW );
		 */
		
		// Noise
		DataRegistry.register( "noise-perlin", PerlinNoise.class );
		DataRegistry.register( "noise-simplex", SimplexNoise.class );
		
		// Attributes
		register( "color", Color.class );
		register( "coord", Coord.class );
		register( "tile", Tile.class );
		register( "scalarf", Scalarf.class );
		register( "scalari", Scalari.class );
		register( "bound2f", Bound2f.class );
		register( "bound2i", Bound2i.class );
		register( "bound3f", Bound3f.class );
		register( "bound3i", Bound3i.class );
		// register( "camera2", Camera2.class );
		// register( "camera3", Camera3.class );
		register( "rect2f", Bound2i.class );
		register( "rect2i", Rect2i.class );
		register( "vec2f", Vec2f.class );
		register( "vec2i", Vec2i.class );
		register( "vec3f", Vec3f.class );
		register( "vec3i", Vec3i.class );
		register( "rangef", Rangef.class );
		register( "rangei", Rangei.class );

		// Colors
		registerObjects( Colors.ColorMap );
		
		// Easing Types
		registerObjects( Easings.TypeMap );

		// Easing Functions
		registerObjects( Easings.MethodMap );

		// Blends
		register( "Add", Blend.Add );
		register( "AlphaAdd", Blend.AlphaAdd );
		register( "Alpha", Blend.Alpha );
		register( "Color", Blend.Color );
		register( "Minus", Blend.Minus );
		register( "PremultAlpha", Blend.PremultAlpha );
		register( "Modulate", Blend.Modulate );
		register( "Xor", Blend.Xor );
		register( "None", Blend.None );
	}
}
