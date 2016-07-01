package com.betterti.titanium.intellij.plugins;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

/**
 * Created by bourtney on 6/30/2016.
 */
public class FirstTestCase extends LightPlatformCodeInsightFixtureTestCase {


  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected boolean isCommunity() {
    return false;
  }

  @Override
  protected LightProjectDescriptor getProjectDescriptor() {
    return new LightProjectDescriptor(){
      @Override
      public void setUpProject(@NotNull Project project, @NotNull SetupHandler setupHandler) throws Exception {
        final TestFixtureBuilder<IdeaProjectTestFixture> projectBuilder = IdeaTestFixtureFactory.getFixtureFactory().createFixtureBuilder(getName());


        myFixture = JavaTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(projectBuilder.getFixture());
      }
    };
  }

  @Test
  public void testThis(){

  }

}
