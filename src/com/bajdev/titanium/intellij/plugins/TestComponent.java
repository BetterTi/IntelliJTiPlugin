package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by bourtney on 6/15/2016.
 */
@State(
        name = "ConfigProviderState",
        storages = {
                @Storage(id = "other", file = StoragePathMacros.MODULE_FILE)
        }
)
public class TestComponent implements ModuleComponent, PersistentStateComponent<TestComponent.State> {

  @NotNull
  @Override
  public String getComponentName() {
    return "ConfigProviderState";
  }
  @Override
  public void disposeComponent() {}
  @Override
  public void initComponent() {}

  public State state = new State();

  @Override
  public void projectOpened() {

  }

  @Override
  public void projectClosed() {

  }

  @Override
  public void moduleAdded() {

  }

  public static class State
  {
    public State() {}
    public String foo = "stateing";

  }

  @Override
  @org.jetbrains.annotations.Nullable
  public TestComponent.State getState() {
    return state; //Saves all public variables to disk.
  }

  @Override
  public void loadState(TestComponent.State state) {
    this.state = state; //restores state from disk
  }


  public String getFoo() {
    if (this.state.foo == null) {
      this.state.foo = "https://jira.rz.is/rest/api/2/";
    }
    return this.state.foo;
  }

  public void setFoo(String foo) {
    this.state.foo = foo;
  }

}