package io.github.cadiboo.testgame.event;

import java.util.List;

/**
 * @author Cadiboo
 */
public class GenericsTest {

	public static void main(String... args) {
		EventBus bus = EventBus.make();

		bus.registerGeneric(null, GenericsTest::printEventType);
		bus.registerGeneric(List.class, GenericsTest::printEventType);

		bus.post(new GenericEvent<>(null));
		bus.post(new GenericEvent<>(List.class));
	}

	private static void printEventType(final Object x) {
		System.out.println(x);
	}

}
