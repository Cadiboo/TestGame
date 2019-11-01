package io.github.cadiboo.testgame.util.math;

/**
 * @author Cadiboo
 */
public class Vec3d {

	public double x;
	public double y;
	public double z;

	public Vec3d(final double x, final double y, final double z) {
		set(x, y, z);
	}

	public Vec3d(final Vec3d vec3d) {
		set(vec3d);
	}

	public void set(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(final Vec3d vec3d) {
		set(vec3d.x, vec3d.y, vec3d.z);
	}

	public void add(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void add(final Vec3d vec3d) {
		add(vec3d.x, vec3d.y, vec3d.z);
	}

	public void add(final double d) {
		add(d, d, d);
	}

	public void subtract(final double x, final double y, final double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}

	public void subtract(final Vec3d vec3d) {
		subtract(vec3d.x, vec3d.y, vec3d.z);
	}

	public void subtract(final double d) {
		subtract(d, d, d);
	}

	public void multiply(final double x, final double y, final double z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
	}

	public void multiply(final Vec3d vec3d) {
		multiply(vec3d.x, vec3d.y, vec3d.z);
	}

	public void multiply(final double d) {
		multiply(d, d, d);
	}

	public void divide(final double x, final double y, final double z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
	}

	public void divide(final Vec3d vec3d) {
		divide(vec3d.x, vec3d.y, vec3d.z);
	}

	public void divide(final double d) {
		divide(d, d, d);
	}

}
