package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkAdditionalData;
import com.intellij.openapi.projectRoots.SdkModificator;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.RootProvider;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by bourtney on 6/19/2016.
 */
public class TitaniumCliSdk implements Sdk {
  private final String _name;
  private final VirtualFile _home;

  public TitaniumCliSdk(String name, VirtualFile home) {
    _name = name;
    _home = home;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @NotNull
  @Override
  public SdkTypeId getSdkType() {
    return TitaniumCliSdkType.getInstance();
  }

  @NotNull
  @Override
  public String getName() {
    return "Titanium CLI (SDK)";
  }

  @Nullable
  @Override
  public String getVersionString() {
    return "NA";
  }

  @Nullable
  @Override
  public String getHomePath() {
    return "HOME PATH";
  }

  @Nullable
  @Override
  public VirtualFile getHomeDirectory() {
//    return new VirtualFileImpl();
    return _home;
  }

  @NotNull
  @Override
  public RootProvider getRootProvider() {
    return new RootProvider() {
      @NotNull
      @Override
      public String[] getUrls(@NotNull OrderRootType orderRootType) {
        return new String[0];
      }

      @NotNull
      @Override
      public VirtualFile[] getFiles(@NotNull OrderRootType orderRootType) {
        return new VirtualFile[0];
      }

      @Override
      public void addRootSetChangedListener(@NotNull RootSetChangedListener rootSetChangedListener) {

      }

      @Override
      public void addRootSetChangedListener(@NotNull RootSetChangedListener rootSetChangedListener, @NotNull Disposable disposable) {

      }

      @Override
      public void removeRootSetChangedListener(@NotNull RootSetChangedListener rootSetChangedListener) {

      }
    };
  }

  @NotNull
  @Override
  public SdkModificator getSdkModificator() {
    return new SdkModificator() {
      @Override
      public String getName() {
        return "Modifier Name";
      }

      @Override
      public void setName(String s) {

      }

      @Override
      public String getHomePath() {
        return "Modifier home path";
      }

      @Override
      public void setHomePath(String s) {

      }

      @Nullable
      @Override
      public String getVersionString() {
        return "VERSION";
      }

      @Override
      public void setVersionString(String s) {

      }

      @Override
      public SdkAdditionalData getSdkAdditionalData() {
        return null;
      }

      @Override
      public void setSdkAdditionalData(SdkAdditionalData sdkAdditionalData) {

      }

      @Override
      public VirtualFile[] getRoots(OrderRootType orderRootType) {
        return new VirtualFile[0];
      }

      @Override
      public void addRoot(VirtualFile virtualFile, OrderRootType orderRootType) {

      }

      @Override
      public void removeRoot(VirtualFile virtualFile, OrderRootType orderRootType) {

      }

      @Override
      public void removeRoots(OrderRootType orderRootType) {

      }

      @Override
      public void removeAllRoots() {

      }

      @Override
      public void commitChanges() {

      }

      @Override
      public boolean isWritable() {
        return false;
      }
    };
  }

  @Nullable
  @Override
  public SdkAdditionalData getSdkAdditionalData() {
    return null;
  }

  @Nullable
  @Override
  public <T> T getUserData(@NotNull Key<T> key) {
    return null;
  }

  @Override
  public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

  }
}
