package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorBound2f;

public class Bound2f implements Attribute<Bound2f>
{
	public float l, r, t, b;

	public Bound2f() 
	{
	}

	public Bound2f(float x, float y) 
	{
		clear(x, y);
	}

	public Bound2f(float left, float top, float right, float bottom) 
	{
		set(left, top, right, bottom);
	}

	public Bound2f(Bound2f x) 
	{
		set(x);
	}

	public Bound2f clone()
	{
		return new Bound2f( this ); 
	}
	
	public void include(float x, float y) 
	{
		if (x < l) l = x;
		if (x > r) r = x;
		if (y > t) t = y;
		if (y < b) b = y;
	}
	
	public void clear( Vec2f v ) {
		clear( v.x, v.y );
	}

	public void clear(float x, float y) {
		l = r = x;
		t = b = y;
	}

	public void set(float left, float top, float right, float bottom) {
		l = left;
		r = right;
		t = top;
		b = bottom;
	}
	
	public void set(Vec2f min, Vec2f max)
	{
		l = min.x;
		b = min.y;
		r = max.x;
		t = max.y;
	}
	
	public void setWidthFromLeft(float w) {
		r = l + w;
	}
	public void setWidthFromRight(float w) {
		l = r - w;
	}
	public void setHeightFromTop(float h) {
		b = t - h;
	}
	public void setHeightFromBottom(float h) {
		t = b + h;
	}

	public void expand(float gx, float gy) {
		l -= gx;
		r += gx;
		t += gy;
		b -= gy;
	}
	
	public boolean isNegative() {
		return r < l || t < b;
	}
	
	public void rect(float x, float y, float width, float height) {
		set(x, y, x + width, y - height);
	}

	public void line(float x0, float y0, float x1, float y1) {
		l = Math.min(x0, x1);
		r = Math.max(x0, x1);
		t = Math.max(y0, y1);
		b = Math.min(y0, y1);
	}

	public void ellipse(float cx, float cy, float rw, float rh) {
		rw = Math.abs(rw);
		rh = Math.abs(rh);
		l = cx - rw;
		r = cx + rw;
		t = cy + rh;
		b = cy - rh;
	}

	public void quad(float cx, float cy, float w, float h, float angle) {
		quad(cx, cy, w, h, Numbers.cos(angle), Numbers.sin(angle));
	}

	public void quad(float cx, float cy, float w, float h, float vx, float vy) {
		vx = Math.abs(vx);
		vy = Math.abs(vy);
		float hw = (vx * w + vy * h) * 0.5f;
		float hh = (vx * h + vy * w) * 0.5f;
		ellipse(cx, cy, hw, hh);
	}

	public void center(float x, float y) {
		float w = width() * 0.5f;
		float h = height() * 0.5f;
		l = x - w;
		r = x + w;
		t = y + h;
		b = y - h;
	}

	public void move(float dx, float dy) {
		l += dx; r += dx;
		t += dy; b += dy;
	}

	public void zoom(float sx, float sy) {
		float hw = width() * 0.5f * Math.abs(sx);
		float hh = height() * 0.5f * Math.abs(sy);
		ellipse(cx(), cy(), hw, hh);
	}

	public float x(float dx) {
		return (r - l) * dx + l;
	}
	
	public float y(float dy) {
		return (b - t) * dy + t;
	}
	
	public float dx(float x) {
		return (x - l) / (r - l);
	}
	
	public float dy(float y) {
		return (y - t) / (b - t);
	}
	
	public float width() {
		return (r - l);
	}

	public float height() {
		return (t - b);
	}
	
	public float area() {
		return (r - l) * (t - b);
	}

	public float cx() {
		return (l + r) * 0.5f;
	}

	public float cy() {
		return (t + b) * 0.5f;
	}

	public void intersection(Bound2f x, Bound2f y) {
		l = Math.max(x.l, y.l);
		r = Math.min(x.r, y.r);
		t = Math.min(x.t, y.t);
		b = Math.max(x.b, y.b);
	}

	public void union(Bound2f x, Bound2f y) {
		l = Math.min(x.l, y.l);
		r = Math.max(x.r, y.r);
		t = Math.max(x.t, y.t);
		b = Math.min(x.b, y.b);
	}

	public int evaluate(Bound2f x) {
		if (x.r <= l || x.l >= r || x.t >= b || x.b <= t) {
			return 1;
		}
		if (x.l < l || x.r > r || x.t > t || x.b < b) {
			return 0;
		}
		return -1;
	}
	
	public boolean intersects(Bound2f x) {
		return !(x.l >= r || x.r <= l || x.t <= b || x.b >= t);
	}

	public boolean touches(Bound2f x) {
		return !(x.l > r || x.r < l || x.t < b || x.b > t);
	}

	public boolean contains(Bound2f x) {
		return !(x.l < l || x.r > r || x.t > t || x.b < b);
	}

	public boolean inside(Bound2f x) {
		return !(x.l <= l || x.r >= r || x.t >= t || x.b <= b);
	}

	public String toString() {
		return String.format("{%.2f, %.2f, %.2f, %.2f}", l, t, width(), height());
	}

	public boolean contains(float x, float y) {
		return !(x < l || x > r || y > t || y < b);
	}

	public void closest(Vec2f v, Vec2f out) {
		out.x = v.x;
		out.y = v.y;
		
		if (out.x < l) out.x = l;
		if (out.x > r) out.x = r;
		if (out.y > t) out.y = t;
		if (out.y < b) out.y = b;
	}
	
	@Override
	public Bound2f get()
	{
		return this;
	}
	
	@Override
	public final CalculatorBound2f getCalculator()
	{
		return CalculatorBound2f.INSTANCE;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(t + b * 215 + r * 46225 + l * 9938375);
	}

}


