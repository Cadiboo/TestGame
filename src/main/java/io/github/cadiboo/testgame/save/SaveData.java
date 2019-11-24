package io.github.cadiboo.testgame.save;

import io.github.cadiboo.testgame.client.ClientGame;
import io.github.cadiboo.testgame.energy.EnergyStorage;
import io.github.cadiboo.testgame.init.CapabilityTypes;
import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.math.Pos;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Cadiboo
 */
public class SaveData {

	private static final int VAR_DATA_BITS_SIZE = 8 - 1;
	private static final int MAX_VAR_CHAR_BYTES = 3;
	private static final int MAX_VAR_INT_BYTES = 5;
	private static final int MAX_VAR_LONG_BYTES = 10;

	private final ByteBuffer buf;

	public SaveData(final ByteBuffer wrapped) {
		this.buf = Objects.requireNonNull(wrapped, "Buffer cannot be null");
	}

	/**
	 * Calculates the number of bytes required to fit the supplied int
	 */
	public static int getVarIntSize(final int input) {
		for (int i = 1; i < MAX_VAR_INT_BYTES; ++i)
			if ((input & -1 << i * 7) == 0)
				return i;
		return MAX_VAR_INT_BYTES;
	}

	/**
	 * Calculates the number of bytes required to fit the supplied int
	 */
	public static int getVarLongSize(final long input) {
		for (int i = 1; i < MAX_VAR_LONG_BYTES; ++i)
			if ((input & -1L << i * 7) == 0)
				return i;
		return MAX_VAR_LONG_BYTES;
	}

	public static void main(String... args) {
		new ClientGame();
		final SaveData save = new SaveData(ByteBuffer.allocate(0x80));
		SaveData load = new SaveData(save.buf.duplicate());
		save.writeVarChar('a');
		print(save);
//		save.writeVarChar(Character.MAX_VALUE);
//		print(save);
//		save.writeChar('a');
//		print(save);
//		save.writeChar(Character.MAX_VALUE);
//		print(save);
		System.out.println(load.readVarChar());
//		System.out.println(load.readVarChar());
//		System.out.println(load.readChar());
//		System.out.println(load.readChar());
		new EnergyStorage(1243256475635L).write(save);
		System.out.println(CapabilityTypes.ENERGY_STORAGE.get().read(load));
	}

	private static void print(final SaveData save) {
		final ByteBuffer buf = save.buf;
		final byte[] array = buf.array();
		for (int i = 0; i < buf.position(); i++) {
			System.out.print(String.format("%8s", Integer.toBinaryString(array[i] & 0xFF)).replace(' ', '0'));
			if (i < buf.position() - 1)
				System.out.print('_');
		}
		System.out.println();
	}

	public SaveData writeByteArray(final byte[] array) {
		this.writeVarInt(array.length);
		this.writeBytes(array);
		return this;
	}

	public byte[] readByteArray() {
		byte[] abyte = new byte[this.readVarInt()];
		this.readBytes(abyte);
		return abyte;
	}

	private void readBytes(final byte[] bytes) {
		this.buf.get(bytes);
	}

	/**
	 * Writes an array of VarInts to the buffer, prefixed by the length of the array (as a VarInt).
	 */
	public SaveData writeVarIntArray(final int[] array) {
		this.writeVarInt(array.length);
		for (int i : array)
			this.writeVarInt(i);
		return this;
	}

	public int[] readVarIntArray() {
		int[] ints = new int[this.readVarInt()];
		for (int j = 0; j < ints.length; ++j)
			ints[j] = this.readVarInt();
		return ints;
	}

	/**
	 * Writes an array of longs to the buffer, prefixed by the length of the array (as a VarInt).
	 */
	public SaveData writeLongArray(final long[] array) {
		this.writeVarInt(array.length);
		for (long i : array)
			this.writeLong(i);
		return this;
	}

	/**
	 * Writes an array of var longs to the buffer, prefixed by the length of the array (as a VarInt).
	 */
	public SaveData writeVarLongArray(final long[] array) {
		this.writeVarInt(array.length);
		for (long i : array)
			this.writeVarLong(i);
		return this;
	}

	/**
	 * Reads a length-prefixed array of longs from the buffer.
	 */
	public long[] readLongArray() {
		final long[] longs = new long[this.readVarInt()];
		for (int i = 0; i < longs.length; ++i)
			longs[i] = this.readLong();
		return longs;
	}

	/**
	 * Reads a length-prefixed array of longs from the buffer.
	 */
	public long[] readVarLongArray() {
		final long[] longs = new long[this.readVarInt()];
		for (int i = 0; i < longs.length; ++i)
			longs[i] = this.readVarLong();
		return longs;
	}

	public Pos readPos() {
		return new Pos(this.readVarLong(), this.readVarLong(), this.readVarLong());
	}

	public SaveData writePos(final Pos pos) {
		this.writeVarLong(pos.x);
		this.writeVarLong(pos.y);
		this.writeVarLong(pos.z);
		return this;
	}

	public <T extends Enum<T>> T readEnumValue(final Class<T> enumClass) {
		return (T) (enumClass.getEnumConstants())[this.readVarInt()];
	}

	public SaveData writeEnumValue(final Enum<?> value) {
		return this.writeVarInt(value.ordinal());
	}

	/**
	 * Reads a compressed int from the buffer.
	 * To do so it maximally reads byte-sized chunks whose most significant bit dictates whether another byte should be read.
	 */
	public int readVarInt() {
		int ret = 0;
		int varIntByteIndex = 0;
		while (true) {
			byte b = this.readByte();
			ret |= (b & 127) << (varIntByteIndex++ * 7);
			if (varIntByteIndex > MAX_VAR_INT_BYTES)
				throw new RuntimeException("VarInt too big");
			if ((b & 128) != 128)
				break;
		}
		return ret;
	}

	/**
	 * Reads a compressed long from the buffer.
	 * To do so it maximally reads byte-sized chunks whose most significant bit dictates whether another byte should be read.
	 */
	public long readVarLong() {
		long ret = 0L;
		int varLongByteIndex = 0;
		while (true) {
			byte b = this.readByte();
			ret |= (long) (b & 127) << varLongByteIndex++ * 7;
			if (varLongByteIndex > MAX_VAR_LONG_BYTES)
				throw new RuntimeException("VarLong too big");
			if ((b & 128) != 128)
				break;
		}
		return ret;
	}

	public byte readByte() {
		return this.buf.get();
	}

	public SaveData writeUniqueId(UUID uuid) {
		this.writeLong(uuid.getMostSignificantBits());
		this.writeLong(uuid.getLeastSignificantBits());
		return this;
	}

	public UUID readUniqueId() {
		return new UUID(this.readLong(), this.readLong());
	}

	/**
	 * Writes a compressed int to the buffer. The smallest number of bytes to fit the passed int will be written. Of each
	 * such byte only 7 bits will be used to describe the actual value since its most significant bit dictates whether
	 * the next byte is part of that same int. Micro-optimization for int values that are expected to have values below
	 * 128.
	 */
	public SaveData writeVarInt(int input) {
		while ((input & -128) != 0) {
			this.writeByte((input & 127) | 128);
			input >>>= VAR_DATA_BITS_SIZE;
		}
		return this.writeByte((byte) input);
	}

	public SaveData writeByte(final int b) {
		return writeByte((byte) (b & 0xFF));
	}

	public SaveData writeByte(final byte b) {
		this.buf.put(b);
		return this;
	}

	public SaveData writeVarLong(long value) {
		while ((value & -128L) != 0L) {
			this.writeByte((int) (value & 127L) | 128);
			value >>>= VAR_DATA_BITS_SIZE;
		}
		return this.writeByte((byte) value);
	}

	public SaveData writeItemStack(final ItemStack<?> stack) {
		stack.write(this);
		return this;
	}

	public ItemStack<?> readItemStack() {
		return ItemStack.EMPTY.read(this);
	}

	public String readString() {
		return this.readString(0x7FFF);
	}

	/**
	 * Reads a string from this buffer.
	 * Expected parameter is maximum allowed string length.
	 * Will throw an IllegalStateException if string length exceeds this value!
	 */
	public String readString(final int maxLength) {
		int strLength = this.readVarInt();
		if (strLength > maxLength * 4) {
			throw new IllegalStateException("The received encoded string buffer length is longer than maximum allowed (" + strLength + " > " + maxLength * 4 + ")");
		} else if (strLength < 0) {
			throw new IllegalStateException("The received encoded string buffer length is less than zero! Weird string!");
		} else {
			final byte[] bytes = new byte[strLength];
			this.buf.get(bytes);
			return new String(bytes);
		}
	}

	public SaveData writeString(final String string) {
		return this.writeString(string, 0x7FFF);
	}

	public SaveData writeString(final String string, final int maxLength) {
		byte[] stringBytes = string.getBytes(StandardCharsets.UTF_8);
		if (stringBytes.length > maxLength) {
			throw new IllegalStateException("String too big (was " + stringBytes.length + " bytes encoded, max " + maxLength + ")");
		} else {
			this.writeVarInt(stringBytes.length);
			this.writeBytes(stringBytes);
			return this;
		}
	}

	private void writeBytes(final byte[] bytes) {
		this.buf.put(bytes);
	}

	public Location readLocation() {
		return Location.of(this.readString());
	}

	public SaveData writeLocation(final Location location) {
		this.writeString(location.toString());
		return this;
	}

	public Date readTime() {
		return new Date(this.readLong());
	}

	public SaveData writeTime(final Date time) {
		return this.writeLong(time.getTime());
	}

	public long readLong() {
		return this.buf.getLong();
	}

	public SaveData writeLong(final long l) {
		this.buf.putLong(l);
		return this;
	}

	public int readInt() {
		return this.buf.getInt();
	}

	public SaveData writeInt(final int i) {
		this.buf.putInt(i);
		return this;
	}

	public char readChar() {
		return this.buf.getChar();
	}

	public SaveData writeChar(final char c) {
		this.buf.putChar(c);
		return this;
	}

	public int capacity() {
		return this.buf.capacity();
	}

	public ByteOrder order() {
		return this.buf.order();
	}

	public ByteBuffer order(final ByteOrder order) {
		return this.buf.order(order);
	}

	public boolean isDirect() {
		return this.buf.isDirect();
	}

	public boolean isReadOnly() {
		return this.buf.isReadOnly();
	}

	public SaveData writeBoolean(final boolean b) {
		this.writeByte(b ? 0x00 : 0xFF);
		return this;
	}

	public boolean readBoolean() {
		return this.readByte() != 0x00;
	}

	public SaveData writeVarChar(char input) {
		while ((input & (char) -128) != 0) {
			this.writeByte((input & (char) 127) | 128);
			input >>>= VAR_DATA_BITS_SIZE;
		}
		return this.writeByte((byte) input);
	}

	/**
	 * Reads a compressed char from the buffer.
	 * To do so it maximally reads byte-sized chunks whose most significant bit dictates whether another byte should be read.
	 */
	public char readVarChar() {
		char ret = 0;
		int varIntByteIndex = 0;
		while (true) {
			byte b = this.readByte();
			ret |= (b & 127) << (varIntByteIndex++ * 7);
			if (varIntByteIndex > MAX_VAR_CHAR_BYTES)
				throw new RuntimeException("VarChar too big: " + varIntByteIndex);
			if ((b & 128) != 128)
				break;
		}
		return ret;
	}

}
