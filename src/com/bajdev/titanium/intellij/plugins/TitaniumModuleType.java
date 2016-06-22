package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by bourtney on 6/15/2016.
 */
public class TitaniumModuleType extends ModuleType<TitaniumModuleTypeBuilder>{


  private static TitaniumModuleType _instance;
  public static String MODULE_ID = "TitaniumModule";
  private static final TitaniumModuleTypeBuilder _titaniumModuleTypeBuilder = new TitaniumModuleTypeBuilder();

  public TitaniumModuleType() {
    super(MODULE_ID);
  }

  public static TitaniumModuleType getInstance(){
    return _instance == null ? _instance = new TitaniumModuleType() : _instance;
  }

  @NotNull
  @Override
  public TitaniumModuleTypeBuilder createModuleBuilder() {
    return _titaniumModuleTypeBuilder;
  }

  @NotNull
  @Override
  public String getName() {
    return "Titanium";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Some awesome description";
  }

  @Override
  public Icon getBigIcon() {
    return null;
  }

  @Override
  public Icon getNodeIcon(@Deprecated boolean b) {
    return null;
  }
}
