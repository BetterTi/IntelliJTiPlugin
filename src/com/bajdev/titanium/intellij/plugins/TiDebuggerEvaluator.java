package com.bajdev.titanium.intellij.plugins;

import com.bajdev.titanium.intellij.plugins.protocol.TitaniumAndroidDebugger;
import com.bajdev.titanium.intellij.plugins.protocol.api.*;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TiDebuggerEvaluator extends XDebuggerEvaluator {
	private final TitaniumDebugger _titaniumDebugger;

	public TiDebuggerEvaluator(TitaniumDebugger titaniumDebugger) {
		_titaniumDebugger = titaniumDebugger;
	}

	@Override
	public void evaluate(@NotNull String s, @NotNull final XEvaluationCallback xEvaluationCallback, @Nullable XSourcePosition xSourcePosition) {
//		_titaniumDebugger.evaluate(s, new RequestCallback() {
//			@Override
//			public void completed(String[] response) {
//				String content = response[3];
//				final String[] contentParts = content.split("\\|");
//				xEvaluationCallback.evaluated(new XValue() {
//					@Override
//					public void computePresentation(@NotNull XValueNode xValueNode, @NotNull XValuePlace xValuePlace) {
//						xValueNode.setPresentation(null, contentParts[0], contentParts[2], true);
//					}
//				});
//			}
//		});
	}
}
