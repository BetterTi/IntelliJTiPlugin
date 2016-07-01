package com.betterti.titanium.intellij.plugins.protocol.api;

import java.util.List;

public interface FramesCallback {
	void event(List<FrameResult> results);
}
