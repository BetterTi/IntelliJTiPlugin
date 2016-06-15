package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.AbstractDebuggerSession;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.EvaluationMode;
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider;
import com.intellij.xdebugger.impl.XDebuggerSupport;
import com.intellij.xdebugger.impl.actions.DebuggerActionHandler;
import com.intellij.xdebugger.impl.actions.DebuggerToggleActionHandler;
import com.intellij.xdebugger.impl.actions.EditBreakpointActionHandler;
import com.intellij.xdebugger.impl.actions.MarkObjectActionHandler;
import com.intellij.xdebugger.impl.breakpoints.ui.BreakpointPanelProvider;
import com.intellij.xdebugger.impl.evaluate.quick.common.QuickEvaluateHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DebuggerSupport extends XDebuggerSupport{
	public DebuggerSupport() {
		super();
	}

	@NotNull
	@Override
	public BreakpointPanelProvider<?> getBreakpointPanelProvider() {
		return super.getBreakpointPanelProvider();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getStepOverHandler() {
		return super.getStepOverHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getStepIntoHandler() {
		return super.getStepIntoHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getSmartStepIntoHandler() {
		return super.getSmartStepIntoHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getStepOutHandler() {
		return super.getStepOutHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getForceStepOverHandler() {
		return super.getForceStepOverHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getForceStepIntoHandler() {
		return super.getForceStepIntoHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getRunToCursorHandler() {
		return super.getRunToCursorHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getForceRunToCursorHandler() {
		return super.getForceRunToCursorHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getResumeActionHandler() {
		return super.getResumeActionHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getPauseHandler() {
		return super.getPauseHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getToggleLineBreakpointHandler() {
		return super.getToggleLineBreakpointHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getToggleTemporaryLineBreakpointHandler() {
		return super.getToggleTemporaryLineBreakpointHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getShowExecutionPointHandler() {
		return super.getShowExecutionPointHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getEvaluateHandler() {
		return super.getEvaluateHandler();
	}

	@NotNull
	@Override
	public QuickEvaluateHandler getQuickEvaluateHandler() {
		return super.getQuickEvaluateHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getAddToWatchesActionHandler() {
		return super.getAddToWatchesActionHandler();
	}

	@NotNull
	@Override
	public DebuggerActionHandler getEvaluateInConsoleActionHandler() {
		return super.getEvaluateInConsoleActionHandler();
	}

	@NotNull
	@Override
	public DebuggerToggleActionHandler getMuteBreakpointsHandler() {
		return super.getMuteBreakpointsHandler();
	}

	@NotNull
	@Override
	public MarkObjectActionHandler getMarkObjectHandler() {
		return super.getMarkObjectHandler();
	}

	@Override
	public AbstractDebuggerSession getCurrentSession(@NotNull Project project) {
		return super.getCurrentSession(project);
	}

	@NotNull
	@Override
	public EditBreakpointActionHandler getEditBreakpointAction() {
		return super.getEditBreakpointAction();
	}
}
