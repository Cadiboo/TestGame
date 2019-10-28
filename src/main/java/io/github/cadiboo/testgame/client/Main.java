package io.github.cadiboo.testgame.client;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.event.Event;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;
import io.github.cadiboo.testgame.util.Location;

public final class Main {

	static final BlockSupplier<Block> TEST_BLOCK = BlockSupplier.of(Location.of("test_block"));

	public static void main(String... args) {
		long startTime = System.nanoTime();
		TestGame.EVENT_BUS.register(event -> {
			System.out.println("Base event fired!");
			System.out.println(event.toString());
		});
		TestGame.EVENT_BUS.post(new Event());
		System.out.println(TEST_BLOCK.get().getHardness());
		long endTime = System.nanoTime();
		System.out.println("Time Elapsed: " + (endTime - startTime));
	}

}
