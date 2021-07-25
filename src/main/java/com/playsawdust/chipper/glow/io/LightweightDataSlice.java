/*
 * Glow - GL Object Wrapper
 * Copyright (C) 2020 the Chipper developers
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.playsawdust.chipper.glow.io;

import java.io.IOException;
import java.nio.ByteOrder;

public class LightweightDataSlice implements DataSlice {
	protected final DataSlice underlying;
	protected final long baseOffset;
	protected final long length;
	protected long pointer = 0L;
	protected ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
	
	public LightweightDataSlice(DataSlice underlying, long offset, long length) {
		this.underlying = underlying;
		this.baseOffset = offset;
		this.length = length;
		this.byteOrder = underlying.getByteOrder();
	}

	@Override
	public void seek(long offset) {
		if (offset<0 || offset>length) throw new ArrayIndexOutOfBoundsException();
		pointer = offset;
	}

	@Override
	public int read() throws IOException {
		int result = underlying.read(pointer);
		seek(pointer+1);
		return result;
	}
	
	@Override
	public int read(long offset) throws IOException {
		return underlying.read(baseOffset + offset);
	}

	@Override
	public long position() {
		return pointer;
	}

	@Override
	public long length() {
		return length;
	}

	@Override
	public DataSlice slice(long offset, long length) {
		return new LightweightDataSlice(this, offset, length);
	}

	@Override
	public ByteOrder getByteOrder() {
		return byteOrder;
	}

	@Override
	public void setByteOrder(ByteOrder order) {
		this.byteOrder = order;
	}
	
}