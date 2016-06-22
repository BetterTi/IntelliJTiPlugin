package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.impl.OrderEntryBaseImpl;
import com.intellij.openapi.roots.impl.RootModelImpl;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by bourtney on 6/19/2016.
 */
public class TitaniumCliOrderEntry extends OrderEntryBaseImpl implements ModuleOrderEntry, ExportableOrderEntry {

  private Module _m;
  private VirtualFile _f;

  public TitaniumCliOrderEntry(Module m, VirtualFile f, RootModelImpl rmi){
    super(rmi);
    _m = m;

    _f = f;
  }

  @NotNull
  @Override
  public VirtualFile[] getFiles(OrderRootType orderRootType) {
    return new VirtualFile[]{_f};
  }

  @NotNull
  @Override
  public String[] getUrls(OrderRootType orderRootType) {
    return new String[0];
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "Presentable";
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @NotNull
  @Override
  public Module getOwnerModule() {
    return _m;
  }

  @Override
  public <R> R accept(RootPolicy<R> rootPolicy, @Nullable R r) {

    return r;
  }

  @Override
  public int compareTo(OrderEntry o) {
    return 0;
  }

  @Override
  public boolean isSynthetic() {
    return false;
  }

  @Nullable
  @Override
  public Module getModule() {
    return _m;
  }

  @Override
  public String getModuleName() {
    return _m.getName();
  }

  @Override
  public boolean isExported() {
    return true;
  }

  @Override
  public void setExported(boolean b) {

  }

  @NotNull
  @Override
  public DependencyScope getScope() {
    return DependencyScope.COMPILE;
  }

  @Override
  public void setScope(@NotNull DependencyScope dependencyScope) {

  }
}
