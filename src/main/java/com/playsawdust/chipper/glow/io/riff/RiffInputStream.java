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
import java.io.InputStream;

import com.playsawdust.chipper.glow.io.ArrayDataSlice;
import com.playsawdust.chipper.glow.io.DataSlice;

public class RiffInputStream {
	private final InputStream in;
	
	public RiffInputStream(InputStream in) throws IOException {
		this.in = in;
	}
	
	public RiffChunk readChunk() throws IOException {
		String chunkTag = readTag(in);
		
		int chunkSize = (in.read() & 0xFF) << 24;
		chunkSize |=    (in.read() & 0xFF) << 16;
		chunkSize |=    (in.read() & 0xFF) <<  8;
		chunkSize |=    (in.read()       );
		
		byte[] chunkData = in.readNBytes(chunkSize);
		
		//TODO: Return a more useful subclass depending on the tag type
		if (chunkTag.equals("RIFF") || chunkTag.equals("LIST")) {
			return new ListRiffChunk(chunkTag, new ArrayDataSlice(chunkData));
		} else {
			return new RiffChunk(chunkTag, new ArrayDataSlice(chunkData));
		}
	}
	
	
	public void close() throws IOException {
		in.close();
	}
	
	public static String readTag(InputStream in) throws IOException {
		String result = "";
		
		boolean terminated = false;
		for(int i=0; i<4; i++) {
			int ch = in.read() & 0xFF;
			if (ch==0) terminated = true;
			
			if (!terminated) {
				result += (char)ch;
			}
		}
		
		return result;
	}
	
	public static String readTag(DataSlice in) throws IOException {
		String result = "";
		
		boolean terminated = false;
		for(int i=0; i<4; i++) {
			int ch = in.read() & 0xFF;
			if (ch==0) terminated = true;
			
			if (!terminated) {
				result += (char)ch;
			}
		}
		
		return result;
	}
}
