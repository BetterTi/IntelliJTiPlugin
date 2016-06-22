package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.roots.ModuleExtension;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

/**
 * Created by bourtney on 6/19/2016.
 */
public class TitaniumModuleExtension extends ModuleExtension {
  private String _test = "this";

  public String getTest() {
    return _test;
  }

  public void setTest(String test) {
    _test = test;
  }

  @Override
  public ModuleExtension getModifiableModel(boolean b) {
    return new TitaniumModuleExtension();
  }

  @Override
  public void commit() {

  }

  @Override
  public boolean isChanged() {
    return true;
  }

  @Override
  public int compareTo(Object o) {
    return 0;
  }

  @Override
  public void dispose() {

  }

  @Override
  public void readExternal(Element element) throws InvalidDataException {

  }

  @Override
  public void writeExternal(Element element) throws WriteExternalException {
    element.addContent("HELLO!!!");
  }
}
