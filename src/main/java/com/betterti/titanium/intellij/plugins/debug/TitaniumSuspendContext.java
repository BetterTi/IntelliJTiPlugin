package com.betterti.titanium.intellij.plugins.debug;

import com.betterti.titanium.intellij.plugins.run.TiDebuggerEvaluator;
import com.betterti.titanium.intellij.plugins.protocol.api.FrameResult;
import com.betterti.titanium.intellij.plugins.protocol.api.FramesCallback;
import com.betterti.titanium.intellij.plugins.protocol.api.TitaniumDebugger;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.ui.ColoredTextContainer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator;
import com.intellij.xdebugger.frame.XExecutionStack;
import com.intellij.xdebugger.frame.XStackFrame;
import com.intellij.xdebugger.frame.XSuspendContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TitaniumSuspendContext extends XSuspendContext {

	private final TitaniumDebugger _titaniumDebugger;
	private final XSourcePosition _position;
	private final Project _project;
	private XStackFrame _frame;
	private TiExecutionStack _xExecutionStack;
	private static final Logger Log = Logger.getInstance(TitaniumSuspendContext.class.getName());

	public TitaniumSuspendContext(TitaniumDebugger titaniumDebugger, XSourcePosition position, Project project){
		_titaniumDebugger = titaniumDebugger;

		_position = position;
		_project = project;

		_titaniumDebugger.fetchFrames(new FramesCallback() {
			@Override
			public void event(List<FrameResult> results) {
				onFramesReceived(results);
			}

		});

		_frame = new TiStackFrame(null, position);
		_xExecutionStack = new TiExecutionStack(_frame);
	}




	private void onFramesReceived(List<FrameResult> response) {
		List<XStackFrame> frameList = new ArrayList<XStackFrame>();
		Log.info("Received frames from system: " + response.size());
		for(FrameResult f : response) {
				//Sample: 0|test||app:/second.js|4|false|0|0
				String func = f.funcName;
				String fileUrl = f.fileName;
				int lineStr = f.line;
				fileUrl = fileUrl.replaceAll("app:", "");


				if(!f.fileName.startsWith("ti:")) {
					XSourcePosition pos = TiSourcePosition.getSourcePosition(_project, fileUrl, (int) f.line);


	//						SourcePosition.createFromLine(PsiDocumentManager.getInstance(g).getPsiFile(new Do))
					frameList.add(new TiStackFrame(func, pos));
				}
			}
			_xExecutionStack.loaded(frameList);
	}


	@Override
	public void computeExecutionStacks(XExecutionStackContainer container) {
		super.computeExecutionStacks(container);
		container.addExecutionStack(Arrays.asList(_xExecutionStack), true);
	}

	@Override
	public XExecutionStack[] getExecutionStacks() {
		return new XExecutionStack[]{_xExecutionStack};
	}

	@Nullable
	@Override
	public XExecutionStack getActiveExecutionStack() {
		return _xExecutionStack;
	}

	private class TiStackFrame extends XStackFrame  {
		private final XSourcePosition _sourcePosition;
		private final String _functionName;

		private TiStackFrame(String functionName, XSourcePosition sourcePosition) {
			if(functionName == null || functionName.length() == 0) {
				_functionName = "(anonymous function): ";
			}
			else{
				_functionName = functionName +"(): ";
			}
			_sourcePosition = sourcePosition;
		}

		@Nullable
		@Override
		public Object getEqualityObject() {
			return null;
		}

		@Nullable
		@Override
		public XDebuggerEvaluator getEvaluator() {
			return new TiDebuggerEvaluator(_titaniumDebugger);
		}

		@Nullable
		@Override
		public XSourcePosition getSourcePosition() {
			return _sourcePosition;
		}

		@Override
		public void customizePresentation(@NotNull ColoredTextContainer component){

			component.append(_functionName, new SimpleTextAttributes(0, Color.BLACK));
			super.customizePresentation(component);
		}


	}

	private class TiExecutionStack extends XExecutionStack {
		private final XStackFrame _topFrame;
		private XStackFrameContainer _container;
		private List<? extends XStackFrame> _pending;

		public TiExecutionStack(XStackFrame topFrame) {
			super("Awesome Execution Stack");
			_topFrame = topFrame;
		}

		public void loaded(List<XStackFrame> frames){
			if(_container == null) {
				_pending = frames;
			}
			else{
				_container.addStackFrames(frames, true);
			}
		}

		@Nullable
		@Override
		public XStackFrame getTopFrame() {
			return _topFrame;
		}

		@Override
		public void computeStackFrames(int i, XStackFrameContainer xStackFrameContainer) {
			_container = xStackFrameContainer;
			if(_pending != null){
				_container.addStackFrames(_pending, true);
			}

		}








	}
}
