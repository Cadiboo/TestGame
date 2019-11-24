package io.github.cadiboo.testgame.client.render;

/**
 * @author Cadiboo
 */
public interface Renderer<T> {

	/**
	 * Render the object
	 * @param delta
	 */
	void render(T object, float delta);

}
