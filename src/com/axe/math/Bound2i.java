package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorBound2i;

public class Bound2i implements Attribute<Bound2i>
{
	public int l, r, t, b;

	public Bound2i() {
	}
	
	public Bound2i(int x, int y) {
		clear(x, y);
	}
	
	public Bound2i(int left, int top, int right, int bottom) {
		set(left, top, right, bottom);
	}
	
	public Bound2i(Bound2i x) {
		set(x);
	}
	
	public void include(int x, int y) {
		if (x < l) l = x;
		if (x > r) r = x;
		if (y > t) t = y;
		if (y < b) b = y;
	}
	
	public void clear(int x, int y) {
		l = r = x;
		t = b = y;
	}
	
	public void set(int left, int top, int right, int bottom) {
		l = left;
		r = right;
		t = top;
		b = bottom;
	}
	
	public boolean isNegative() {
		return r < l || b > t;
	}
	
	public void rect(int x, int y, int width, int height) {
		set(x, y, x + width, y + height);
	}
	
	public void line(int x0, int y0, int x1, int y1) {
		l = Math.min(x0, x1);
		r = Math.max(x0, x1);
		t = Math.max(y0, y1);
		b = Math.min(y0, y1);
	}
	
	public void ellipse(int cx, int cy, int rw, int rh) {
		rw = Math.abs(rw);
		rh = Math.abs(rh);
		l = cx - rw;
		r = cx + rw;
		t = cy + rh;
		b = cy - rh;
	}
	
	
	public void center(int x, int y) {
		int w = width() / 2;
		int h = height() / 2;
		l = x - w;
		r = x + w;
		t = y + h;
		b = y - h;
	}
	
	public void move(int dx, int dy) {
		l += dx; r += dx;
		t += dy; b += dy;
	}
	
	public void zoom(float sx, float sy) {
		float hw = width() * 0.5f * Math.abs(sx);
		float hh = height() * 0.5f * Math.abs(sy);
		ellipse(cx(), cy(), (int)hw, (int)hh);
	}
	
	public int width() {
		return (r - l);
	}
	
	public int height() {
		return (t - b);
	}
	
	public int cx() {
		return (l + r) / 2;
	}
	
	public int cy() {
		return (t + b) / 2;
	}
	
	public int area() {
		return (r - l) * (t - b);
	}
	
	public void intersection(Bound2i x, Bound2i y) {
		l = Math.max(x.l, y.l);
		r = Math.min(x.r, y.r);
		t = Math.min(x.t, y.t);
		b = Math.max(x.b, y.b);
	}
	
	public void union(Bound2i x, Bound2i y) {
		l = Math.min(x.l, y.l);
		r = Math.max(x.r, y.r);
		t = Math.max(x.t, y.t);
		b = Math.min(x.b, y.b);
	}
	
	public boolean inBounds(Vec2i v) {
		return inBounds( v.x, v.y );
	}
	
	public boolean inBounds(Vec2f v) {
		return inBounds( v.x, v.y );
	}
	
	public boolean inBounds(float x, float y) {
		return !(x < l || x >= r || y > t || y <= b);
	}
	
	public boolean intersects(Bound2i x) {
		return !(x.l >= r || x.r <= l || x.t <= b || x.b >= t);
	}
	
	public boolean touches(Bound2i x) { 
		return !(x.l > r || x.r < l || x.t < b || x.b > t);
	}
	
	public boolean contains(Bound2i x) {
		return !(x.l < l || x.r > r || x.t > t || x.b < b);
	}
	
	public boolean inside(Bound2i x) {
		return !(x.l <= l || x.r >= r || x.t >= t || x.b <= b);
	}
	
	@Override
	public String toString() 
	{
		return String.format("{%d, %d, %d, %d}", l, t, width(), height());
	}
	
	@Override
	public Bound2i clone()
	{
		return new Bound2i( this );
	}

	@Override
	public Bound2i get() 
	{
		return this;
	}

	@Override
	public int hashCode()
	{
		return (int)(t + b * 215 + r * 46225 + l * 9938375);
	}


	@Override
	public final CalculatorBound2i getCalculator() 
	{
		return CalculatorBound2i.INSTANCE;
	}

}
