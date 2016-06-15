package com.bajdev.titanium.intellij.plugins;

import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class TitaniumSettingsEditor extends SettingsEditor<TitaniumRunConfiguration> {

	private JTextField _srcDirField;
	private JComboBox _platformBox;
	private JComponent _editor;


	public TitaniumSettingsEditor(){
		_editor = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();


		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		JLabel label = new JLabel("Sources Directory:");
		label.setBackground(Color.blue);
		_editor.add(label, constraints);


		_srcDirField = new JTextField();


		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		_editor.add(_srcDirField, constraints);


		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		label = new JLabel("Sources Directory:");
		label.setBackground(Color.blue);
		_editor.add(label, constraints);


//		_srcDirField = new JTextField();
//
//
//		constraints.gridx = 1;
//		constraints.gridy = 0;
//		constraints.weightx = 1;
//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		_editor.add(_srcDirField, constraints);


		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		_editor.add( new JLabel("Platform:"), constraints);


		_platformBox = new JComboBox();
		_platformBox.addItem("Android");
		_platformBox.addItem("iPhone");
		_platformBox.setSelectedItem("Android");

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.NONE;
		_editor.add(_platformBox, constraints);
	}

	@Override
	protected void resetEditorFrom(TitaniumRunConfiguration o) {
		_platformBox.setSelectedItem(o.getPlatform() != null ? o.getPlatform() : "Android");
		_srcDirField.setText(o.getSrcDirectory());
	}

	@Override
	protected void applyEditorTo(TitaniumRunConfiguration o) throws ConfigurationException {
	 	o.setPlatform(_platformBox.getSelectedItem().toString());
		o.setSrcDirectory(_srcDirField.getText());
	}

	@NotNull
	@Override
	protected JComponent createEditor() {
		return _editor;
	}
}
