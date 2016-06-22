package com.bajdev.titanium.intellij.plugins;

import com.bajdev.titanium.intellij.plugins.debug.TitaniumSuspendContext;
import com.bajdev.titanium.intellij.plugins.protocol.TitaniumAndroidDebugger;
import com.bajdev.titanium.intellij.plugins.protocol.api.PauseEvent;
import com.bajdev.titanium.intellij.plugins.protocol.api.ShutdownCallback;
import com.bajdev.titanium.intellij.plugins.protocol.api.TitaniumDebugger;
import com.bajdev.titanium.intellij.plugins.run.TitaniumCommandLine;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.breakpoints.XBreakpoint;
import com.intellij.xdebugger.breakpoints.XBreakpointHandler;
import com.intellij.xdebugger.breakpoints.XBreakpointType;
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider;
import org.apache.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.java.debugger.JavaDebuggerEditorsProvider;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Debugger extends com.intellij.xdebugger.XDebugProcess {

	private static final Logger Log = Logger.getInstance(Debugger.class.getName());
	private GeneralCommandLine _commandLine;
	private OSProcessHandler _process;


	private TitaniumDebugger _titaniumDebugger;

	private Class<? extends XBreakpointType> _javascriptBreakpointType;


	protected Debugger(@NotNull XDebugSession session, TitaniumRunConfiguration config) {
		super(session);
		_titaniumDebugger = new TitaniumAndroidDebugger();
		_titaniumDebugger.start();

		final XBreakpoint<?>[] breakpoints = XDebuggerManager.getInstance(getSession().getProject()).getBreakpointManager().getAllBreakpoints();

		LogManager.getRootLogger().getAppender("CONSOLE-WARN").clearFilters();



		_commandLine = new TitaniumCommandLine(config);

		_titaniumDebugger.initialize();

		for (XBreakpoint b : XDebuggerManager.getInstance(getSession().getProject()).getBreakpointManager().getAllBreakpoints()) {
			if (b.getType().getClass().getName().contains("JavaScriptBreakpoint")) {
				addBreakpoint(b);
			}
		}


		_titaniumDebugger.onPause(new PauseEvent() {
			@Override
			public void paused(String file, int line) {
				Log.info("Pause received. Starting pause process " + file + ":" + line );
				onSuspended(file, line);
			}
		});

		_titaniumDebugger.resume();


	}

	private void onSuspended(final String[] parts) {
		ApplicationManager.getApplication().runReadAction(new Runnable() {
			@Override
			public void run() {
				onSuspendedUiThread(parts);

			}
		});

	}

	private void onSuspendedUiThread(String[] parts) {
		String type = parts[2];
		Log.info("Suspended!");
		if ("breakpoint".equals(type)) {
			String file = parts[3];
			String lineNoStr = parts[4];
			long lineNo = Long.parseLong(lineNoStr) - 1;
			try {


				onSuspended(file, lineNo);
//												getSession().breakpointReached()
			} catch (NumberFormatException nfe) {
				Log.error("Failed to parse line number when receiving a breakpoint: " + lineNoStr);
			}
		}
		if ("requested".equals(type)) {
			String file = parts[3];
			String lineNoStr = parts[4];
			long lineNo = Long.parseLong(lineNoStr) - 1;
			positionReached(file, (int) lineNo);
		}
	}

	private void onSuspended(final String file, final long lineNoInput) {
		ApplicationManager.getApplication().runReadAction(new Runnable() {
			@Override
			public void run() {
				long lineNo = lineNoInput;
				Log.info("Suspended at breakpoint. Searching for it.");
				boolean found = false;
				for (XBreakpoint b : XDebuggerManager.getInstance(getSession().getProject()).getBreakpointManager().getAllBreakpoints()) {
					if (b.getType().getClass().getName().contains("JavaScriptBreakpoint")) {
						if (lineNo == b.getSourcePosition().getLine()) {
							Log.info("Source position matches: " + b.getSourcePosition().toString());
							final TitaniumSuspendContext xSuspendContext = new TitaniumSuspendContext(_titaniumDebugger, b.getSourcePosition(), getSession().getProject());
							getSession().breakpointReached(b, null, xSuspendContext);
							found = true;
							break;

						}
					}
				}
				if (!found) {
					Log.info("Could not find breakpoint, going to tell intellij to stop anyway");
					positionReached(file, (int) lineNo);
				}

			}
		});
	}

	private void positionReached(String file, int lineNo) {
		XSourcePosition pos = TiSourcePosition.getSourcePosition(getSession().getProject(), file, lineNo);

		final TitaniumSuspendContext xSuspendContext = new TitaniumSuspendContext(_titaniumDebugger, pos, getSession().getProject());
		getSession().positionReached(xSuspendContext);
	}

	@NotNull
	@Override
	public XBreakpointHandler<?>[] getBreakpointHandlers() {

		if (_javascriptBreakpointType == null) {
			for (XBreakpoint b : XDebuggerManager.getInstance(getSession().getProject()).getBreakpointManager().getAllBreakpoints()) {
				if (b.getType().getClass().getName().contains("JavaScriptBreakpoint")) {
					_javascriptBreakpointType = b.getType().getClass();
					break;
				}
			}
		}
		try {
			final Class<?> javaScriptDebugger1 = PluginManager.getPlugin(PluginId.findId("JavaScriptDebugger")).getPluginClassLoader().loadClass("com.intellij.javascript.debugger.breakpoints.JavaScriptBreakpointType");
			return new XBreakpointHandler[]{
							new XBreakpointHandler(javaScriptDebugger1) {

								@Override
								public void registerBreakpoint(@NotNull XBreakpoint breakpoint) {

									addBreakpoint(breakpoint);
								}

								@Override
								public void unregisterBreakpoint(@NotNull XBreakpoint breakpoint, boolean b1) {
									removeBreakpoint(breakpoint);
								}
							}
			};
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void removeBreakpoint(@NotNull XBreakpoint breakpoint) {
		Path p = Paths.get(breakpoint.getSourcePosition().getFile().getPath());
		final Path project = Paths.get(getSession().getProject().getBasePath(), "Resources");
		Path relative = project.relativize(p);

		Log.info("register Breakpoint");
		final int lineNo = breakpoint.getSourcePosition().getLine();

		_titaniumDebugger.removeBreakpoint(relative, lineNo);
		Log.info("Ungegister breakpoint");
	}

	private void addBreakpoint(@NotNull XBreakpoint breakpoint) {
		Path p = Paths.get(breakpoint.getSourcePosition().getFile().getPath());
		final Path project = Paths.get(getSession().getProject().getBasePath(), "Resources");
		Path relative = project.relativize(p);

		Log.info("register Breakpoint");
		final int lineNo = breakpoint.getSourcePosition().getLine();

		_titaniumDebugger.addBreakpoint(relative, lineNo);
	}

	@Nullable
	@Override
	protected ProcessHandler doGetProcessHandler() {
		Log.info("Creating process handler");
		try {
			return _process = new OSProcessHandler(_commandLine);
		} catch (ExecutionException e) {
			Log.error("Failed to create process handler for debuggger");
		}
		return null;
	}

	@Override
	public void stop() {
		Log.info("STOP");
		_titaniumDebugger.stop(new ShutdownCallback(){
			@Override
			public void completed() {
				getSession().stop();
			}
		});

	}


	@NotNull
	@Override
	public ExecutionConsole createConsole() {
		final ExecutionConsole console = super.createConsole();
		((ConsoleView) console).attachToProcess(_process);
		return console;
	}

	@NotNull
	@Override
	public XDebuggerEditorsProvider getEditorsProvider() {
		return new JavaDebuggerEditorsProvider();
	}

	@Override
	public void startStepOver() {
		Log.info("StepOver");
		_titaniumDebugger.stepOver();
	}

	@Override
	public void startStepInto() {
		Log.info("StepInto");
		_titaniumDebugger.stepInto();

	}

	@Override
	public void startStepOut() {
		Log.info("StepOut");
		_titaniumDebugger.stepReturn();

	}

	@Override
	public void resume() {

		Log.info("Resume");

		_titaniumDebugger.resume();

	}

	@Override
	public void runToPosition(@NotNull XSourcePosition xSourcePosition) {
		Log.debug("RUN TO POSITION");
	}
}
