package com.bajdev.titanium.intellij.plugins.run;

import com.bajdev.titanium.intellij.plugins.TitaniumModuleType;
import com.bajdev.titanium.intellij.plugins.TitaniumRunConfiguration;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.util.containers.HashMap;
import com.intellij.xdebugger.XDebugSession;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class TitaniumCommandLine extends GeneralCommandLine {
	public TitaniumCommandLine(@NotNull TitaniumRunConfiguration o) {
		super(o.getTitaniumPath(),
						"build",
						"--sdk", "3.5.1.GA",
						"--platform", o.getPlatform().toLowerCase(),
						"--skip-js-minify",
						"--debug-host", "/127.0.0.1:54321",
						"--android-sdk", "C:\\Users\\bourtney\\android-sdk-windows");
		setWorkDirectory(new File(o.getProject().getBasePath(), o.getExecutionDirectory()));
	}
}
