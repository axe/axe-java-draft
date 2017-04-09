package com.axe.gfx;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.axe.util.Buffers;

public class VertexFormat 
{ 
	public final Primitive primitive;
	public final int vertexDimension;
	public final DataType vertexType;
	public final int colorComponents;
	public final DataType colorType;
	public final int secondaryColorComponents;
	public final DataType secondaryColorType;
	public final int normalDimension;
	public final DataType normalType;
	public final int textures;
	public final int[] textureDimension;
	public final DataType[] textureType;
	public final int attributes;
	public final int[] attributeIndex;
	public final int[] attributeSize;
	public final DataType[] attributeType;
	public final boolean[] attributeNormalize;
	
	public final boolean aligned;
	public final int size;
	public final int stride;
	public final int padding;
	public final int vertexOffset;
	public final int normalOffset;
	public final int colorOffset;
	public final int secondaryColorOffset;
	public final int[] textureOffsets;
	public final int[] attributeOffset;
	
	public final VertexFormatBuilder builder;
	
	public VertexFormat( VertexFormatBuilder builder )
	{
		this.builder = builder;
		
		this.primitive = builder.getPrimitive();
		this.vertexDimension = builder.getVertexDimension();
		this.vertexType = builder.getVertexType();
		this.colorComponents = builder.getColorComponents();
		this.colorType = builder.getColorType();
		this.secondaryColorComponents = builder.getSecondaryColorComponents();
		this.secondaryColorType = builder.getSecondaryColorType();
		this.normalDimension = builder.getNormalDimension();
		this.normalType = builder.getNormalType();
		this.textures = builder.getTextures();
		this.textureDimension = builder.getTextureDimension();
		this.textureType = builder.getTextureType();
		this.attributes = builder.getAttributes();
		this.attributeIndex = builder.getAttributeIndex();
		this.attributeSize = builder.getAttributeSize();
		this.attributeType = builder.getAttributeType();
		this.attributeNormalize = builder.getAttributeNormalize();
		
		this.aligned = builder.isAligned();
		this.size = builder.getSize();
		this.stride = (aligned ? builder.getStride() : size);
		this.padding = builder.getPadding();
		this.vertexOffset = builder.getVertexOffset();
		this.normalOffset = builder.getNormalOffset();
		this.colorOffset = builder.getColorOffset();
		this.secondaryColorOffset = builder.getSecondaryColorOffset();
		this.textureOffsets = builder.getTextureOffsets();
		this.attributeOffset = builder.getAttributeOffsets();
	}
	
	public VertexFormat( Primitive primitive, VertexMeshType type, int vertexDimension, DataType vertexType, int colorComponents, DataType colorType, int secondaryColorComponents, DataType secondaryColorType, int normalDimension, DataType normalType, int textures, int[] textureDimension, DataType[] textureType, int attributes, int[] attributeIndex, int[] attributeSize, DataType[] attributeType, boolean[] attributeNormalize, boolean aligned, int size, int stride, int padding, int vertexOffset, int normalOffset, int colorOffset, int secondaryColorOffset, int[] textureOffsets, int[] attributeOffset )
	{
		this.builder = null;
		
		this.primitive = primitive;
		this.vertexDimension = vertexDimension;
		this.vertexType = vertexType;
		this.colorComponents = colorComponents;
		this.colorType = colorType;
		this.secondaryColorComponents = secondaryColorComponents;
		this.secondaryColorType = secondaryColorType;
		this.normalDimension = normalDimension;
		this.normalType = normalType;
		this.textures = textures;
		this.textureDimension = textureDimension;
		this.textureType = textureType;
		this.attributes = attributes;
		this.attributeIndex = attributeIndex;
		this.attributeSize = attributeSize;
		this.attributeType = attributeType;
		this.attributeNormalize = attributeNormalize;
		this.aligned = aligned;
		this.size = size;
		this.stride = (aligned ? stride : size);
		this.padding = padding;
		this.vertexOffset = vertexOffset;
		this.normalOffset = normalOffset;
		this.colorOffset = colorOffset;
		this.secondaryColorOffset = secondaryColorOffset;
		this.textureOffsets = textureOffsets;
		this.attributeOffset = attributeOffset;
	}

	public ByteBuffer getBuffer( int vertices )
	{
		return Buffers.bytes( vertices * stride );
	}

	@Override
	public String toString()
	{
		return "VertexFormat [primitive=" + primitive + ", vertexDimension=" + vertexDimension + ", vertexType=" + vertexType + ", colorComponents=" + colorComponents + ", colorType=" + colorType + ", secondaryColorComponents=" + secondaryColorComponents + ", secondaryColorType=" + secondaryColorType + ", normalDimension=" + normalDimension + ", normalType=" + normalType + ", textures=" + textures + ", textureDimension=" + Arrays.toString( textureDimension ) + ", textureType=" + Arrays.toString( textureType ) + ", attributes=" + attributes + ", attributeIndex=" + Arrays.toString( attributeIndex ) + ", attributeSize=" + Arrays.toString( attributeSize ) + ", attributeType=" + Arrays.toString( attributeType ) + ", attributeNormalize=" + Arrays.toString( attributeNormalize ) + ", aligned=" + aligned + ", size=" + size + ", stride=" + stride + ", padding=" + padding + ", vertexOffset=" + vertexOffset + ", normalOffset=" + normalOffset + ", colorOffset=" + colorOffset + ", secondaryColorOffset=" + secondaryColorOffset + ", textureOffsets=" + Arrays.toString( textureOffsets ) + ", attributeOffset=" + Arrays.toString( attributeOffset ) + ", builder=" + builder + "]";
	}
	
}