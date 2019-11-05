package io.github.cadiboo.testgame.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Cadiboo
 */
public final class Utils {

	/**
	 * @param input Some text containing newlines.
	 * @return Input split by newline.
	 */
	public static String[] splitNewline(String input) {
		return input.split("\\r\\n|\\n");
	}

	/**
	 * @see "https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java"
	 */
	public static String convertStreamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static byte[] readStreamFully(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(Math.max(8192, is.available()));
		byte[] buffer = new byte[8192];
		int read;
		while ((read = is.read(buffer)) >= 0) {
			baos.write(buffer, 0, read);
		}
		return baos.toByteArray();
	}

	public static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
	}

	public static int countBits(int i) {
		int count = 0;
		while (i != 0) {
			++count;
			i >>= 1;
		}
		return count;
	}

	static String requireNotEmpty(final String s, final String msg) {
		if (s.isEmpty())
			throw new IllegalStateException(msg);
		return s;
	}

	public static long nanosToMillis(final long nanos) {
		return nanos / 1_000_000;
	}

	public static long millisToSeconds(final long millis) {
		return millis / 1_000;
	}

}
