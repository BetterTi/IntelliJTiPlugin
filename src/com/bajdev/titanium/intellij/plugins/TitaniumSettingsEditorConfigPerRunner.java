package com.bajdev.titanium.intellij.plugins;

import com.intellij.execution.configurations.ConfigurationPerRunnerSettings;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class TitaniumSettingsEditorConfigPerRunner extends SettingsEditor<ConfigurationPerRunnerSettings> {
	@Override
	protected void resetEditorFrom(ConfigurationPerRunnerSettings configurationPerRunnerSettings) {

	}

	@Override
	protected void applyEditorTo(ConfigurationPerRunnerSettings configurationPerRunnerSettings) throws ConfigurationException {

	}

	@NotNull
	@Override
	protected JComponent createEditor() {
		return null;
	}
}
