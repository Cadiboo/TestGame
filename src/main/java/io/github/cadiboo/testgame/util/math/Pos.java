package io.github.cadiboo.testgame.util.math;

/**
 * @author Cadiboo
 */
public class Pos {

	public long x;
	public long y;
	public long z;

	public Pos(final long x, final long y, final long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + Long.hashCode(x);
		result = 31 * result + Long.hashCode(y);
		result = 31 * result + Long.hashCode(z);
		return result;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Pos pos = (Pos) o;
		return x == pos.x &&
				y == pos.y &&
				z == pos.z;
	}

	@Override
	public String toString() {
		return "Pos {x=" + x + ", y=" + y + ", z=" + z + '}';
	}

}
