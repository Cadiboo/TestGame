package io.github.cadiboo.testgame.energy;

import io.github.cadiboo.testgame.capability.Capability;
import io.github.cadiboo.testgame.save.SaveData;

public class EnergyStorage implements Capability<EnergyStorage> {

	public long energy;

	public EnergyStorage(final long energy) {
		this.energy = energy;
	}

	public EnergyStorage read(final SaveData saveData) {
		return new EnergyStorage(saveData.readVarLong());
	}

	@Override
	public void write(final SaveData saveData) {
		saveData.writeVarLong(this.energy);
	}

	@Override
	public String toString() {
		return "EnergyStorage{" +
				"energy=" + energy +
				'}';
	}

}
