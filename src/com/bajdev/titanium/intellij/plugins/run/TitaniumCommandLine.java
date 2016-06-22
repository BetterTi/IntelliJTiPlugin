package com.bajdev.titanium.intellij.plugins.run;

import com.bajdev.titanium.intellij.plugins.TitaniumRunConfiguration;
import com.intellij.execution.configurations.GeneralCommandLine;

import java.io.File;

public class TitaniumCommandLine extends GeneralCommandLine {
	public TitaniumCommandLine(TitaniumRunConfiguration o) {

		super("titanium",
						"build",
						"--sdk", "3.5.1.GA",
						"--platform", o.getPlatform().toLowerCase(),
						"--skip-js-minify",
						"--debug-host", "/127.0.0.1:54321");
		setWorkDirectory(new File(o.getProject().getBasePath(), o.getExecutionDirectory()));
	}
}
