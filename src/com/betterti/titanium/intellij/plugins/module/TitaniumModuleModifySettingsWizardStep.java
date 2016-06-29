package com.betterti.titanium.intellij.plugins.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bourtney on 6/15/2016.
 */
public class TitaniumModuleModifySettingsWizardStep extends ModuleWizardStep {
  private JPanel _tiExecutableChooser;
  private TextFieldWithBrowseButton _tiCliBrowser;

  public TitaniumModuleModifySettingsWizardStep() {
  }

  @Override
  public void onStepLeaving() {
    super.onStepLeaving();
  }

  @Override
  public JComponent getComponent() {

    JPanel outer = new JPanel(new BorderLayout());


    final JPanel jPanel = new JPanel();
    GroupLayout layout = new GroupLayout(jPanel);
    jPanel.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    _tiExecutableChooser = getTitaniumCliExecutable();


    JLabel comp = new JLabel("Hello world!");

    layout.setVerticalGroup(
            layout.createSequentialGroup()
                    .addComponent(comp)
                    .addComponent(_tiExecutableChooser)
    );

    layout.setHorizontalGroup(
            layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(comp)
                            .addComponent(_tiExecutableChooser)
                    )
    );

    jPanel.add(comp);



    outer.add(jPanel, BorderLayout.NORTH);

    return outer;
  }

  @Override
  public void updateDataModel() {
    System.out.println("here");
  }

  public void writeSettings(SettingsStep step){
//    step.addSettingsField("ti.cli", _tiCliBrowser.getText());

  }

  private JPanel getTitaniumCliExecutable(){
    JPanel fileChoosingPanel = new JPanel();
    fileChoosingPanel.setLayout(new BorderLayout());
    JTextField tiappXmlTextField = new JTextField();
    _tiCliBrowser = new TextFieldWithBrowseButton(tiappXmlTextField);
    _tiCliBrowser.addBrowseFolderListener("Title", "description", null, new FileChooserDescriptor(
            false, true, false, false, false, false
    ), TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT);
    _tiCliBrowser.setText(getAutomaticLocationDetection());

    fileChoosingPanel.add(new Label("Ti CLI Location: "), BorderLayout.WEST);
    fileChoosingPanel.add(_tiCliBrowser, BorderLayout.CENTER);

    return fileChoosingPanel;
  }

  @NotNull
  private String getAutomaticLocationDetection() {
    return "C:\\Users\\bourtney\\AppData\\Roaming\\npm\\ti.cmd";
  }

  public String getSelectedCli() {
    return _tiCliBrowser.getText();
  }
}
