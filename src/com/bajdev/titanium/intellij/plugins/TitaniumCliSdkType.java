package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkAdditionalData;
import com.intellij.openapi.projectRoots.SdkTypeId;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by bourtney on 6/19/2016.
 */
public class TitaniumCliSdkType implements SdkTypeId {
  private static TitaniumCliSdkType _instance;

  @NotNull
  @Override
  public String getName() {
    return "Titanium CLI";
  }

  @Nullable
  @Override
  public String getVersionString(@NotNull Sdk sdk) {
    return "";
  }

  @Override
  public void saveAdditionalData(@NotNull SdkAdditionalData sdkAdditionalData, @NotNull Element element) {
    element.addContent("HELLO WORLD!");
    element.setAttribute("blah", "blerg");
  }

  @Nullable
  @Override
  public SdkAdditionalData loadAdditionalData(@NotNull Sdk sdk, Element element) {
    return new SdkAdditionalData() {
      public String SDKADDDATA = "12345";
      @Override
      public int hashCode() {
        return super.hashCode();
      }

      @Override
      public Object clone() throws CloneNotSupportedException {
        return super.clone();
      }
    };
  }

  public static SdkTypeId getInstance() {
    return _instance == null ? _instance = new TitaniumCliSdkType() : _instance
            ;
  }
}
