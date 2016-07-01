package com.betterti.titanium.intellij.plugins.module;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.*;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.*;

/**
 * Created by johnsba1 on 6/14/16.
 */
public class TitaniumModuleTypeBuilder extends ModuleBuilder implements SourcePathsBuilder {

    private ModuleWizardStep[] _moduleWizardSteps;
    private TitaniumModuleModifySettingsWizardStep _opttionStep;

    @Override
    public String getBuilderId() {
        return getClass().getName();
    }

    @Override
    public String getPresentableName() {
        return "Titanium Application";
    }

    @Override
    public String getDescription() {
        return "Module for running titanium classic applications";
    }

    @Override
    public Icon getBigIcon() {
        return null;
    }

    @Override
    public Icon getNodeIcon() {
        return null;
    }


    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return _moduleWizardSteps == null ? _moduleWizardSteps = new ModuleWizardStep[]{} : _moduleWizardSteps;
    }

    @Nullable
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return _opttionStep == null ? _opttionStep = new TitaniumModuleModifySettingsWizardStep() : _opttionStep;
    }




    @Nullable
    @Override
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        return super.modifySettingsStep(settingsStep);
    }

    @Override
    public ModuleWizardStep modifyProjectTypeStep(@NotNull SettingsStep settingsStep) {
        return super.modifyProjectTypeStep(settingsStep);
    }

    @Override
    public ModuleWizardStep modifyStep(SettingsStep settingsStep) {
        return _opttionStep == null ? _opttionStep = new TitaniumModuleModifySettingsWizardStep() : _opttionStep;
    }



    @Override
    public void setupRootModel(final ModifiableRootModel rootModel) throws com.intellij.openapi.options.ConfigurationException {

        rootModel.getModule().setOption("titanium.cli", _opttionStep.getSelectedCli());

        ContentEntry contentEntry = doAddContentEntry(rootModel);

        if(contentEntry != null) {
            final List<Pair<String, String>> sourcePaths = getSourcePaths();
            for (Pair<String, String> sourcePath : sourcePaths) {
                String first = sourcePath.first;
                new File(first).mkdirs();
                final VirtualFile sourceRoot = LocalFileSystem.getInstance()
                        .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(first));
                if (sourceRoot != null) {
                    contentEntry.addSourceFolder(sourceRoot, false, sourcePath.second);
                }
            }
//            final VirtualFile excludeRoot = LocalFileSystem.getInstance().fin
//                    .refreshAndFindFileByPath(FileUtil.toSystemIndependentName("build"));
//            contentEntry.addExcludeFolder("build");
        }
//        final ModuleLibraryOrderEntryImpl orderEntry = new ModuleLibraryOrderEntryImpl(name, type, myRootModel, myProjectRootManager);

//        rootModel.addContentEntry("Resources").addSourceFolder("Resources", true);
//        rootModel.getModule()getModule
//        rootModel.commit();
//        ((RootModelImpl)rootModel).rootModel
//        rootModel.addOrderEntry(new TitaniumCliOrderEntry(rootModel.getModule(), rootModel.getProject().getBaseDir(), (RootModelImpl)rootModel));
//        rootModel.setSdk(new TitaniumCliSdk("test input 1", rootModel.getProject().getBaseDir()));

//        rootModel
//        rootModel.commit();

//        rootModel.commit();
//        rootModel.
//
//
////        setMyProject(rootModel.getProject().ad);
//        final CompilerModuleExtension compilerModuleExtension = rootModel.getModuleExtension(CompilerModuleExtension.class);
//        compilerModuleExtension.setExcludeOutput(true);
//
//        if (myJdk != null) {
//            rootModel.setSdk(myJdk);
//        } else {
//            rootModel.inheritSdk();
//        }
//
//        ContentEntry contentEntry = doAddContentEntry(rootModel);
//        if (contentEntry != null) {
//            final List<Pair<String, String>> sourcePaths = getSourcePaths();
//
//            if (sourcePaths != null) {
//                for (final Pair<String, String> sourcePath : sourcePaths) {
//                    String first = sourcePath.first;
//                    new File(first).mkdirs();
//                    final VirtualFile sourceRoot = LocalFileSystem.getInstance()
//                            .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(first));
//                    if (sourceRoot != null) {
//                        contentEntry.addSourceFolder(sourceRoot, false, sourcePath.second);
//                    }
//                }
//            }
//        }
//
//        if (myCompilerOutputPath != null) {
//            // should set only absolute paths
//            String canonicalPath;
//            try {
//                canonicalPath = FileUtil.resolveShortWindowsName(myCompilerOutputPath);
//            } catch (IOException e) {
//                canonicalPath = myCompilerOutputPath;
//            }
//            compilerModuleExtension
//                    .setCompilerOutputPath(VfsUtil.pathToUrl(FileUtil.toSystemIndependentName(canonicalPath)));
//        } else {
//            compilerModuleExtension.inheritCompilerOutputPath(true);
//        }
//
//        LibraryTable libraryTable = rootModel.getModuleLibraryTable();
//        for (Pair<String, String> libInfo : myModuleLibraries) {
//            final String moduleLibraryPath = libInfo.first;
//            final String sourceLibraryPath = libInfo.second;
//            Library library = libraryTable.createLibrary();
//            Library.ModifiableModel modifiableModel = library.getModifiableModel();
//            modifiableModel.addRoot(getUrlByPath(moduleLibraryPath), OrderRootType.CLASSES);
//            if (sourceLibraryPath != null) {
//                modifiableModel.addRoot(getUrlByPath(sourceLibraryPath), OrderRootType.SOURCES);
//            }
//            modifiableModel.commit();
//        }
    }

    @Override
    public String getGroupName() {
        return "Test Group";
    }


    private List<Pair<String, String>> mySourcePaths;

    public List<Pair<String, String>> getSourcePaths() {
        if (mySourcePaths == null) {
            final List<Pair<String, String>> paths = new ArrayList<Pair<String, String>>();
            @NonNls final String path = getContentEntryPath() + File.separator + "Resources";
            new File(path).mkdirs();
            paths.add(Pair.create(path, ""));
            return paths;
        }
        return mySourcePaths;
    }

    public void setSourcePaths(List<Pair<String, String>> sourcePaths) {
        mySourcePaths = sourcePaths != null ? new ArrayList<Pair<String, String>>(sourcePaths) : null;
    }

    public void addSourcePath(Pair<String, String> sourcePathInfo) {
        if (mySourcePaths == null) {
            mySourcePaths = new ArrayList<Pair<String, String>>();
        }
        mySourcePaths.add(sourcePathInfo);
    }

    public ModuleType getModuleType() {
        return TitaniumModuleType.getInstance();
    }

    @Override
    public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return false;
    }

}