package io.github.cadiboo.testgame.save;

/**
 * @author Cadiboo
 */
public interface Saveable<T> {

	/**
	 * This method will be called on a default instance of your capability.
	 * Do not expect this instance to be properly initialised.
	 * Do not reference "this" from this method
	 *
	 * @param saveData The data to read the new instance from
	 * @return a NEW instance of your capability
	 */
	T read(SaveData saveData);

	void write(SaveData saveData);

}
