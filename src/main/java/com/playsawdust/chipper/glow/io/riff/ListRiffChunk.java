/*
 * Glow - GL Object Wrapper
 * Copyright (C) 2020 the Chipper developers
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.playsawdust.chipper.glow.io.riff;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;

import com.playsawdust.chipper.glow.io.DataSlice;

public class ListRiffChunk extends RiffChunk {
	protected String listType;
	protected ArrayList<RiffChunk> children = new ArrayList<>();
	
	public ListRiffChunk(String tag, DataSlice contents) throws IOException {
		super(tag, contents);
		contents.seek(0L);
		contents.setByteOrder(ByteOrder.LITTLE_ENDIAN);
		listType = "" + contents.read() + contents.read() + contents.read() + contents.read();
		
		try {
			while(contents.position()<contents.length()) {
				String subchunkTag = "" + contents.read() + contents.read() + contents.read() + contents.read();
				int chunkSize = contents.readI32s();
				DataSlice chunkData = contents.slice(chunkSize);
				if (subchunkTag.equals("RIFF") || subchunkTag.equals("LIST")) {
					children.add(new ListRiffChunk(subchunkTag, chunkData));
				} else {
					children.add(new RiffChunk(subchunkTag, chunkData));
				}
			}
		} catch (IOException ex) {} //Catch exception in case of a truncated chunk, which will be dropped.
	}

}
