package com.axe;

import org.magnos.asset.Assets;
import org.magnos.asset.csv.CsvFormat;
import org.magnos.asset.font.FontFormat;
import org.magnos.asset.java.ClassFormat;
import org.magnos.asset.java.JarFormat;
import org.magnos.asset.json.JsonFormat;
import org.magnos.asset.props.PropertyFormat;
import org.magnos.asset.source.SmartSource;
import org.magnos.asset.text.TextFormat;
import org.magnos.asset.xml.XmlFormat;
import org.magnos.asset.zip.GzipFormat;
import org.magnos.asset.zip.ZipFormat;

import com.axe.audio.AudioEngine;
import com.axe.color.Color;
import com.axe.core.Factory;
import com.axe.game.Game;
import com.axe.gfx.Coord;
import com.axe.gfx.GraphicsEngine;
import com.axe.input.ControllerEngine;
import com.axe.input.KeyEngine;
import com.axe.input.MouseEngine;
import com.axe.math.Bound2f;
import com.axe.math.Bound2i;
import com.axe.math.Bound3f;
import com.axe.math.Bound3i;
import com.axe.math.Rangef;
import com.axe.math.Rangei;
import com.axe.math.Rect2f;
import com.axe.math.Rect2i;
import com.axe.math.Scalarf;
import com.axe.math.Scalari;
import com.axe.math.Vec2f;
import com.axe.math.Vec2i;
import com.axe.math.Vec3f;
import com.axe.math.Vec3i;
import com.axe.math.calc.CalculatorBound2f;
import com.axe.math.calc.CalculatorBound2i;
import com.axe.math.calc.CalculatorBound3f;
import com.axe.math.calc.CalculatorBound3i;
import com.axe.math.calc.CalculatorCamera2;
import com.axe.math.calc.CalculatorCamera3;
import com.axe.math.calc.CalculatorColor;
import com.axe.math.calc.CalculatorCoord;
import com.axe.math.calc.CalculatorRangef;
import com.axe.math.calc.CalculatorRangei;
import com.axe.math.calc.CalculatorRect2f;
import com.axe.math.calc.CalculatorRect2i;
import com.axe.math.calc.CalculatorRegistry;
import com.axe.math.calc.CalculatorScalarf;
import com.axe.math.calc.CalculatorScalari;
import com.axe.math.calc.CalculatorVec2f;
import com.axe.math.calc.CalculatorVec2i;
import com.axe.math.calc.CalculatorVec3f;
import com.axe.math.calc.CalculatorVec3i;
import com.axe.monitor.MonitorSystem;
import com.axe.render.Renderer;
import com.axe.resource.Resource;
import com.axe.resource.ResourceFutureAsset;
import com.axe.util.DefaultLogger;
import com.axe.util.Logger;
import com.axe.util.Registry;
import com.axe2d.Camera2;
import com.axe3d.Camera3;

public class Axe 
{
	
	public static Factory<Game> games;
	public static KeyEngine keys;
	public static MouseEngine mouse;
	public static ControllerEngine controllers;
	
	public static MonitorSystem monitors;
	public static AudioEngine audio;
	public static GraphicsEngine graphics;
	
	public static Registry<Renderer> renderers = new Registry<>();
	
	public static Logger logger = new DefaultLogger();
	
	public static void load()
	{
		Assets.addSource( "smart", new SmartSource( true ) );
		Assets.setDefaultSource( "smart" );
		
		Assets.addFutureAssetFactory( ResourceFutureAsset.FACTORY, Resource.class );
		
		Assets.addFormat( new CsvFormat() );
		Assets.addFormat( new FontFormat() );
		Assets.addFormat( new ClassFormat() );
		Assets.addFormat( new JarFormat() );
		Assets.addFormat( new JsonFormat() );
		Assets.addFormat( new PropertyFormat() );
		Assets.addFormat( new TextFormat() );
		Assets.addFormat( new XmlFormat() );
		Assets.addFormat( new GzipFormat() );
		Assets.addFormat( new ZipFormat() );

		CalculatorRegistry.register( CalculatorScalarf.INSTANCE, Scalarf.class, "scalar", "scalarf", "number", "float" );
		CalculatorRegistry.register( CalculatorScalari.INSTANCE, Scalari.class, "scalari", "integer", "int" );
		CalculatorRegistry.register( CalculatorVec2f.INSTANCE, Vec2f.class, "vec2f", "vec2" );
		CalculatorRegistry.register( CalculatorVec2i.INSTANCE, Vec2i.class, "vec2i" );
		CalculatorRegistry.register( CalculatorVec3f.INSTANCE, Vec3f.class, "vec3f", "vec3" );
		CalculatorRegistry.register( CalculatorVec3i.INSTANCE, Vec3i.class, "vec3i" );
		CalculatorRegistry.register( CalculatorBound2f.INSTANCE, Bound2f.class, "bound2f", "bound2" );
		CalculatorRegistry.register( CalculatorBound2i.INSTANCE, Bound2i.class, "bound2i" );
		CalculatorRegistry.register( CalculatorBound3f.INSTANCE, Bound3f.class, "bound3f", "bound3" );
		CalculatorRegistry.register( CalculatorBound3i.INSTANCE, Bound3i.class, "bound3i" );
		CalculatorRegistry.register( CalculatorRect2f.INSTANCE, Rect2f.class, "rect2f", "rect2" );
		CalculatorRegistry.register( CalculatorRect2i.INSTANCE, Rect2i.class, "rect2i" );
		CalculatorRegistry.register( CalculatorRangef.INSTANCE, Rangef.class, "rangef", "range" );
		CalculatorRegistry.register( CalculatorRangei.INSTANCE, Rangei.class, "rangei" );
		CalculatorRegistry.register( CalculatorColor.INSTANCE, Color.class, "color" );
		CalculatorRegistry.register( CalculatorCoord.INSTANCE, Coord.class, "coord" );
		CalculatorRegistry.register( CalculatorCamera2.INSTANCE, Camera2.class, "camera2" );
		CalculatorRegistry.register( CalculatorCamera3.INSTANCE, Camera3.class, "camera3" );
	}
	
}
