/*
 * Minecraft Forge
 * Copyright (c) 2016-2019.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package io.github.cadiboo.testgame.util;

import java.io.PrintStream;

/**
 * @author Arkan
 * @author Cadiboo
 */
public class TracingPrintStream extends PrintStream {

	private static final int BASE_DEPTH = 4;

	public TracingPrintStream(PrintStream original) {
		super(original);
	}

	private static String getPrefix() {
		StackTraceElement[] elems = Thread.currentThread().getStackTrace();
		StackTraceElement elem = elems[Math.min(BASE_DEPTH, elems.length - 1)]; // The caller is always at BASE_DEPTH, including this call.
		if (elem.getClassName().startsWith("kotlin.io.")) {
			elem = elems[Math.min(BASE_DEPTH + 2, elems.length - 1)]; // Kotlins IoPackage masks origins 2 deeper in the stack.
		} else if (elem.getClassName().startsWith("java.lang.Throwable")) {
			elem = elems[Math.min(BASE_DEPTH + 4, elems.length - 1)];
		}
		return "[" + elem.getClassName() + ":" + elem.getMethodName() + ":" + elem.getLineNumber() + "]: ";
	}

	@Override
	public void println(final boolean x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final char x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final int x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final long x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final float x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final double x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final char[] x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final String x) {
		printPrefix();
		super.println(x);
	}

	@Override
	public void println(final Object x) {
		printPrefix();
		super.println(x);
	}

	private void printPrefix() {
		super.print(getPrefix());
	}

}
