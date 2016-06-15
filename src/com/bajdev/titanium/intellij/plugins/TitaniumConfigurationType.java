package com.bajdev.titanium.intellij.plugins;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class TitaniumConfigurationType implements ConfigurationType {
	@Override
	public String getDisplayName() {
		return "Titanium";
	}

	@Override
	public String getConfigurationTypeDescription() {
		return "Start a titanium project";
	}

	@Override
	public Icon getIcon() {
		return null;
	}

	@NotNull
	@Override
	public String getId() {
		return "com.bajdev.titanium.intellij.plugins";
	}

	@Override
	public ConfigurationFactory[] getConfigurationFactories() {
		return new ConfigurationFactory[]{new TitaniumConfigurationFactory(this)};
	}
}
