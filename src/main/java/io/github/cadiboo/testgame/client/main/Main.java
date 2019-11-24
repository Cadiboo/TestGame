package io.github.cadiboo.testgame.client.main;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.client.idk.Window;

/**
 * @author Cadiboo
 */
public class Main {

	public static void main(String[] args) {
		// 1 create window (rendering)
		// 2 register callbacks (game)
		// 3 init game (game)
		// 4 display errors (rendering)
		// 5 display main menu (rendering)

		createWindow();
		game();
	}

	private static void createWindow() {
		final Window window = new Window(1280, 760, TestGame.DOMAIN);
		window.create();
	}

	private static void game() {
		new Thread(() -> {

		}, "Game Thread").start();
	}

}
