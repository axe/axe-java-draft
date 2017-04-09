package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorBound3i;

public class Bound3i implements Attribute<Bound3i>
{
	public int l, r, t, b, n, f;

	public Bound3i() {
	}

	public Bound3i(int x, int y, int z) {
		clear(x, y, z);
	}

	public Bound3i(int left, int top, int right, int bottom, int near, int far) {
		set(left, top, right, bottom, near, far);
	}

	public Bound3i(Bound3i x) {
		set(x);
	}
	
	public Bound3i(Vec3i x, Vec3i y) {
		clear( x );
		include( y );
	}

	public void include(Vec3i v) {
		include( v.x, v.y, v.z );
	}
	
	public void include(int x, int y, int z) {
		if (x < l) l = x;
		if (x > r) r = x;
		if (y > t) t = y;
		if (y < b) b = y;
		if (z < n) n = z;
		if (z > f) f = z;
	}

	public void clear(Vec3i v) {
		clear( v.x, v.y, v.z );
	}
	
	public void clear(int x, int y, int z) {
		l = r = x;
		t = b = y;
		n = f = z;
	}
	
	public void translate(Vec3i v) {
		translate( v.x, v.y, v.z );
	}
	
	public void translate(int dx, int dy, int dz) {
		l += dx;
		r += dx;
		t += dy;
		b += dy;
		n += dz;
		f += dz;
	}

	public void set(int left, int top, int right, int bottom, int near, int far) {
		l = left;
		r = right;
		t = top;
		b = bottom;
		n = near;
		f = far;
	}

	public void rect(int x, int y, int z, int width, int height, int depth) {
		set(x, y, x + width, y - height, z, z + depth);
	}

	public void line(int x0, int y0, int z0, int x1, int y1, int z1) {
		l = Math.min(x0, x1);
		r = Math.max(x0, x1);
		t = Math.max(y0, y1);
		b = Math.min(y0, y1);
		n = Math.min(z0, z1);
		f = Math.max(z0, z1);
	}

	public void ellipse(int cx, int cy, int cz, int rw, int rh, int rz) {
		rw = Math.abs(rw);
		rh = Math.abs(rh);
		rz = Math.abs(rz);
		l = cx - rw;
		r = cx + rw;
		t = cy + rh;
		b = cy - rh;
		n = cz - rz;
		f = cz + rz;
	}

	public void center(int x, int y, int z) {
		int w = width() >> 1;
		int h = height() >> 1;
		int d = depth() >> 1;
		l = x - w;
		r = x + w;
		t = y + h;
		b = y - h;
		n = z - d;
		f = z + d;
	}

	public void move(int dx, int dy, int dz) {
		l += dx; r += dx;
		t += dy; b += dy;
		n += dz; f += dz;
	}

	public void zoom(int sx, int sy, int sz) {
		int hw = (width() >> 1) * Math.abs(sx);
		int hh = (height() >> 1) * Math.abs(sy);
		int hd = (depth() >> 1) * Math.abs(sz);
		ellipse(cx(), cy(), cz(), hw, hh, hd);
	}

	public int width() {
		return (r - l);
	}

	public int height() {
		return (t - b);
	}

	public int depth() {
		return (f - n);
	}

	public int area() {
		return (r - l) * (t - b) * (f - n);
	}

	public int cx() {
		return (l + r) >> 1;
	}

	public int cy() {
		return (t + b) >> 1;
	}

	public int cz() {
		return (n + f) >> 1;
	}

	public void intersection(Bound3i x, Bound3i y) {
		l = Math.max(x.l, y.l);
		r = Math.min(x.r, y.r);
		t = Math.min(x.t, y.t);
		b = Math.max(x.b, y.b);
		n = Math.max(x.n, y.n);
		f = Math.min(x.f, y.f);
	}

	public void union(Bound3i x, Bound3i y) {
		l = Math.min(x.l, y.l);
		r = Math.max(x.r, y.r);
		t = Math.max(x.t, y.t);
		b = Math.min(x.b, y.b);
		n = Math.min(x.n, y.n);
		f = Math.max(x.f, y.f);
	}

	public boolean intersects(Bound3i x) {
		return !(x.l >= r || x.r <= l || x.t <= b || x.b >= t || x.n >= f || x.f <= n);
	}

	public boolean touches(Bound3i x) {
		return !(x.l > r || x.r < l || x.t < b || x.b > t || x.n > f || x.f < n);
	}

	public boolean contains(Bound3i x) {
		return !(x.l <= l || x.r >= r || x.t >= t || x.b <= b || x.n <= n || x.f >= f);
	}

	public boolean inside(Bound3i x) {
		return !(x.l < l || x.r > r || x.t > t || x.b < b || x.n < n || x.f > f);
	}

	public String toString() {
		return String.format("{x=%d, y=%d, z=%d, w=%d, h=%d, d=%d}", l, b, n, width(), height(), depth());
	}

	public boolean contains(int x, int y, int z) {
		return !(x < l || x > r || y > t || y < b || z > f || z < n);
	}

	@Override
	public Bound3i get()
	{
		return this;
	}
	
	@Override
	public Bound3i clone() 
	{
		return new Bound3i( l, t, r, b, n, f );
	}
	
	@Override
	public CalculatorBound3i getCalculator()
	{
		return CalculatorBound3i.INSTANCE;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(t + b * 36 + r * 1296 + l * 46656 + n * 1679616 + f * 60466176);
	}

	public static Bound3i getIntersection(Bound3i x, Bound3i y)
	{
		Bound3i b = new Bound3i();
		b.intersection( x, y );
		return b;
	}

	public static Bound3i getUnion(Bound3i x, Bound3i y)
	{
		Bound3i b = new Bound3i();
		b.union( x, y );
		return b;
	}
	
}

