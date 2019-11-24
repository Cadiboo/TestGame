package io.github.cadiboo.testgame.heat;

import io.github.cadiboo.testgame.capability.Capability;
import io.github.cadiboo.testgame.save.SaveData;

public class HeatLevel implements Capability<HeatLevel> {

	public long heat;

	public HeatLevel(final long heat) {
		this.heat = heat;
	}

	public HeatLevel read(final SaveData saveData) {
		return new HeatLevel(saveData.readVarLong());
	}

	@Override
	public void write(final SaveData saveData) {
		saveData.writeVarLong(this.heat);
	}

	@Override
	public String toString() {
		return "HeatLevel{" +
				"heat=" + heat +
				'}';
	}

}
