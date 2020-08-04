package io.github.cadiboo.testgame.capability;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.save.SaveData;
import io.github.cadiboo.testgame.util.Location;

import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class CapabilityType<T extends Capability<T>> extends RegistryEntry<CapabilityType<?>> {

	private final Supplier<T> serialisationInstanceSupplier;
	private T serialisationInstance;

	/**
	 * @param serialisationInstanceSupplier An instance that will be constructed ONCE and have it's {@link Capability#read(SaveData)} method of it called to create new instances
	 */
	public CapabilityType(final Location registryName, final Supplier<T> serialisationInstanceSupplier) {
		super(registryName);
		this.serialisationInstanceSupplier = serialisationInstanceSupplier;
	}

	public T read(final SaveData saveData) {
		if (serialisationInstance == null)
			serialisationInstance = serialisationInstanceSupplier.get();
		return serialisationInstance.read(saveData);
	}

}
