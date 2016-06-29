package com.betterti.titanium.intellij.plugins.run;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class TitaniumRunConfigurationSettingsEditor extends SettingsEditor<TitaniumRunConfiguration> {

	private JTextField _srcDirField;
	private LabeledComponent<ComboBox> _platformBox;
	private JComponent _editor;
	private final LabeledComponent<ModulesComboBox> _moduleBox;
	private Project _project;
	private final TextFieldWithBrowseButton _executableDirectoryField;


	public TitaniumRunConfigurationSettingsEditor(@NotNull Project project){
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
		constraints.anchor = GridBagConstraints.EAST;
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

		_executableDirectoryField = new TextFieldWithBrowseButton();

		_executableDirectoryField.addBrowseFolderListener("Working Directory", null, project, new FileChooserDescriptor(
						false, true, false, false, false, false
		), TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		_editor.add(new Label("Working Directory: "), constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		_editor.add(_executableDirectoryField, constraints);




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
		_executableDirectoryField.setText(o.getExecutionDirectory());
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
		o.setExecutionDirectory(_executableDirectoryField.getText());
	}

	@NotNull
	@Override
	protected JComponent createEditor() {
		return _editor;
	}

	@Override
	public TitaniumRunConfiguration getSnapshot() throws ConfigurationException {
		return super.getSnapshot();
	}


}
