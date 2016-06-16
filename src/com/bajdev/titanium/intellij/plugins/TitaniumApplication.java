package com.bajdev.titanium.intellij.plugins;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by johnsba1 on 6/14/16.
 */
public class TitaniumApplication implements ApplicationComponent {
    public TitaniumApplication() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "TitaniumApplication";
    }
}
