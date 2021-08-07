/*
 * Glow - GL Object Wrapper
 * Copyright (C) 2020 the Chipper developers
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.playsawdust.chipper.glow.io.riff;

import com.playsawdust.chipper.glow.io.DataSlice;

//Note: this is not a record for subclassing reasons
public class RiffChunk {
	protected String tag;
	protected DataSlice contents;
	
	public RiffChunk(String tag, DataSlice contents) {
		this.tag = tag;
		this.contents = contents;
	}
}
