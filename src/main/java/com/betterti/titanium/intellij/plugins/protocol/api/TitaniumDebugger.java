package com.betterti.titanium.intellij.plugins.protocol.api;

import com.betterti.titanium.intellij.plugins.protocol.RequestCallback;

import java.nio.file.Path;

public interface TitaniumDebugger {
	void onPause(PauseEvent callback);

	void start();

	void stop(ShutdownCallback shutdownCallback);

	void initialize();


	void addBreakpoint(Path relative, int lineNo);

	void removeBreakpoint(Path relative, int lineNo);

	void stepOver();

	void stepInto();

	void stepReturn();

	void resume();

	void fetchFrames(FramesCallback callback);

	void evaluate(String command, RequestCallback callback);
}
