package io.github.cadiboo.testgame.event;

/**
 * @author Cadiboo
 */
public class BusTest {

	public static void main(String... args) {
		final long startTime = System.nanoTime();

//		final EventBus unsafeEventBus = new UnsafeEventBusImpl();
		final EventBus safeEventBus = new SafeEventBusImpl();

//		register(unsafeEventBus);
		register(safeEventBus);
//		test(unsafeEventBus);
		test(safeEventBus);

		final long timeElapsed = System.nanoTime() - startTime;
		System.out.println("TimeElapsed: " + timeElapsed);
	}

	private static void test(final EventBus bus) {
		bus.post(new Event());
		bus.post(new GenericEvent<>(Integer.class));
		bus.post(new CustomEvent(1));
		bus.post(new CustomGenericEvent("Hello"));
		bus.post(new NestedEvent3());
		bus.post(new NestedEvent5("Super Nested!"));
	}

	private static void register(final EventBus bus) {
		bus.register(BusTest::acceptEvents);
		bus.register(BusTest::acceptGenericEvents);
		bus.register(BusTest::acceptEvent);
		bus.registerGeneric(String.class, BusTest::acceptGenericEvent);
		bus.register(BusTest::acceptNestedEvent5);
		bus.register(BusTest::acceptNestedEvent3);
	}

	private static void acceptEvents(final Event e) {
		System.out.println("acceptEvents: " + EventBus.getEventName(e));
	}

	private static void acceptGenericEvents(final GenericEvent<?> e) {
		System.out.println("acceptGenericEvents: " + EventBus.getEventName(e));
	}

	private static void acceptEvent(final CustomEvent e) {
		System.out.println("acceptEvent: " + EventBus.getEventName(e) + " " + e.object);
	}

	private static void acceptGenericEvent(final CustomGenericEvent e) {
		System.out.println("acceptGenericEvent: " + EventBus.getEventName(e) + " " + e.value);
	}

	private static void acceptNestedEvent3(final NestedEvent3 e) {
		System.out.println("acceptNestedEvent3: " + EventBus.getEventName(e));
	}

	private static void acceptNestedEvent5(final NestedEvent5 e) {
		System.out.println("acceptNestedEvent5: " + EventBus.getEventName(e) + " " + e.nestedObject);
	}

	private static class CustomEvent extends Event {

		Object object;

		public CustomEvent(final Object object) {
			this.object = object;
		}

	}

	private static class CustomGenericEvent extends GenericEvent<String> {

		String value;

		public CustomGenericEvent(final String value) {
			super(String.class);
			this.value = value;
		}

	}

	private static class NestedEvent1 extends Event {

	}

	private static class NestedEvent2 extends NestedEvent1 {

	}

	private static class NestedEvent3 extends NestedEvent2 {

	}

	private static class NestedEvent4 extends NestedEvent3 {

	}

	private static class NestedEvent5 extends NestedEvent4 {

		Object nestedObject;

		public NestedEvent5(final Object nestedObject) {
			this.nestedObject = nestedObject;
		}

	}

}
