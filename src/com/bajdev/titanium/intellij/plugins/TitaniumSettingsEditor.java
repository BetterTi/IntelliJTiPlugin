package com.bajdev.titanium.intellij.plugins;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ui.configuration.ModulesAlphaComparator;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.SortedComboBoxModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class TitaniumSettingsEditor extends SettingsEditor<TitaniumRunConfiguration> {

	private JTextField _srcDirField;
	private LabeledComponent<ComboBox> _platformBox;
	private JComponent _editor;
	private final LabeledComponent<ModulesComboBox> _moduleBox;
	private Project _project;


	public TitaniumSettingsEditor(@NotNull Project project){
		_project = project;
		_editor = new JPanel(new GridBagLayout());

		ModulesComboBox b = new ModulesComboBox();
		_moduleBox = LabeledComponent.create(b, "Module");
		_moduleBox.getComponent().fillModules(project);
		_moduleBox.setLabelInsets(new Insets(5,5,5,5));

		GridBagConstraints constraints = new GridBagConstraints();


		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		_editor.add(_moduleBox.getLabel(), constraints);


		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		_editor.add(_moduleBox.getComponent(), constraints);




		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>(
						new String[]{
										"Android",
										"iPhone"
						}
		);
		ComboBox platformComboBoxCompnent = new ComboBox(comboBoxModel);
		_platformBox = LabeledComponent.create(platformComboBoxCompnent, "Platform");


		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		_editor.add(_platformBox.getLabel(), constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		_editor.add(_platformBox.getComponent(), constraints);
	}

	@Override
	protected void resetEditorFrom(TitaniumRunConfiguration o) {
		_platformBox.getComponent().setSelectedItem(o.getPlatform() != null ? o.getPlatform() : "Android");
		if(o.getModule() != null) {
			for(Module m : ModuleManager.getInstance(_project).getModules()) {
				if(o.getModule().equals(m.getName())) {
					_moduleBox.getComponent().setSelectedModule(m);
				}
			}
		}
		else{
			_moduleBox.getComponent().setSelectedModule(null);
		}
	}

	@Override
	protected void applyEditorTo(TitaniumRunConfiguration o) throws ConfigurationException {
	 	o.setPlatform(_platformBox.getComponent().getSelectedItem().toString());
		Module selectedModule = _moduleBox.getComponent().getSelectedModule();
		if(selectedModule != null) {
			o.setModule(selectedModule.getName());
		}
		else{
			o.setModule(null);
		}
	}

	@NotNull
	@Override
	protected JComponent createEditor() {
		return _editor;
	}
}
