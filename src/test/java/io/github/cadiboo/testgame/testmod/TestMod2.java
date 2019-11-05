package io.github.cadiboo.testgame.testmod;

import io.github.cadiboo.testgame.api.Mod;

/**
 * @author Cadiboo
 */
@Mod(TestMod2.MOD_ID)
public final class TestMod2 {

	public static final String MOD_ID = "test_mod2";

	public TestMod2() {
	}

	private void debug(Object msg) {
		System.out.println("[" + MOD_ID + "]: " + msg);
	}

}
