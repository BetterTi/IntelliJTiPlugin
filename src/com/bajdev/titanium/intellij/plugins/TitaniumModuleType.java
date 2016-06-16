package com.bajdev.titanium.intellij.plugins;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.javaee.model.xml.Icon;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by johnsba1 on 6/14/16.
 */
public class TitaniumModuleType extends ModuleType<TitaniumModuleBuilder>{
    public TitaniumModuleType() {
        super(Constants.MODULE_TYPE_ID);
    }

    @NotNull
    public static TitaniumModuleType getInstance() {
        return (TitaniumModuleType) ModuleTypeManager.getInstance().findByID(Constants.MODULE_TYPE_ID);
    }

    @NotNull
    @Override
    public TitaniumModuleBuilder createModuleBuilder() {
        return new TitaniumModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "Go Module";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Go modules are used for developing <b>Go</b> applications.";
    }

    @Nullable
    @Override
    public Icon getBigIcon() {
        return null;
    }

    @Nullable
    @Override
    public Icon getNodeIcon(boolean isOpened) {
        return null;
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext,
                                                @NotNull final TitaniumModuleBuilder moduleBuilder,
                                                @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{new ProjectJdkForModuleStep(wizardContext, GoSdkType.getInstance()) {
            @Override
            public void updateDataModel() {
                super.updateDataModel();
                moduleBuilder.setModuleJdk(getJdk());
            }
        }};
    }
}


