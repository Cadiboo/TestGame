package io.github.cadiboo.testgame.capability;

import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.save.SaveData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cadiboo
 */
public abstract class DefaultCapabilityHandler implements CapabilityHandler {

	private Map<CapabilityType<?>, Capability<?>> capabilities = null;

	protected DefaultCapabilityHandler() {
	}

	public <T extends Capability<T>> T getCapability(CapabilityType<T> type) {
		final Map<CapabilityType<?>, Capability<?>> capabilities = this.capabilities;
		if (capabilities == null)
			return null;
		final Capability<?> capability = capabilities.get(type);
		return capability == null ? null : capability.cast();
	}

	public <T extends Capability<T>> T addCapability(CapabilityType<T> type, T capability) {
		if (capabilities == null)
			capabilities = new HashMap<>();
		final Capability old = capabilities.put(type, capability);
		if (old != null)
			System.out.println("DefaultCapabilityHandler: Replaced capability " + type.getRegistryName() + ". Old: " + old + ", New: " + capability);
		return null;
	}

	protected void readCapabilities(final SaveData saveData) {
		for (int i = 0; i < saveData.readVarInt(); ++i) {
			final CapabilityType capabilityType = Registries.CAPABILITY_TYPES.get(saveData.readLocation());
			final Capability capability = capabilityType.read(saveData);
			addCapability(capabilityType, capability);
		}
	}

	/**
	 * Stores capabilities as {size,location,data,location,data...}
	 */
	protected void writeCapabilities(final SaveData saveData) {
		if (capabilities == null) {
			saveData.writeVarInt(0);
			return;
		}
		saveData.writeVarInt(capabilities.size());
		capabilities.forEach((capabilityType, capability) -> {
			saveData.writeLocation(capabilityType.getRegistryName());
			capability.write(saveData);
		});
	}

}
