package com.bajdev.titanium.intellij.plugins;

import com.intellij.ide.projectWizard.ProjectSettingsStep;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.impl.*;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by johnsba1 on 6/14/16.
 */
public class TitaniumModuleTypeBuilder extends ModuleBuilder implements SourcePathsBuilder {

    private Project myProject;
    private ModuleWizardStep[] _moduleWizardSteps;
    private TitaniumModuleModifySettingsWizardStep _opttionStep = new TitaniumModuleModifySettingsWizardStep(myProject);

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
        return "Awesome description!";
    }

    @Override
    public Icon getBigIcon() {
        return null;//AsposeIcons.AsposeMedium;
    }

    @Override
    public Icon getNodeIcon() {
        return null;//AsposeIcons.AsposeLogo;
    }


    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        if(_moduleWizardSteps == null) {
            _moduleWizardSteps = new ModuleWizardStep[]{
            };
        }
        return _moduleWizardSteps;
    }


    @Nullable
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return _opttionStep;
    }



    @Override
    public void setupRootModel(final ModifiableRootModel rootModel) throws com.intellij.openapi.options.ConfigurationException {

        System.out.println("okay");
        System.out.println(rootModel.getModule().getComponent(TestComponent.class));

        rootModel.getModule().setOption("titanium.cli", _opttionStep.getSelectedCli());

        File moduleDir = new File(rootModel.getModule().getModuleFilePath()).getParentFile();

        ContentEntry contentEntry = doAddContentEntry(rootModel);
        for(File f : moduleDir.listFiles()){
            if(f.getName().equals("Resources")){
                String first = f.getPath();
                final VirtualFile sourceRoot = LocalFileSystem.getInstance()
                        .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(first));
                if (sourceRoot != null) {
                    contentEntry.addSourceFolder(sourceRoot, false);
                }
            }
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

    public Project getMyProject() {
        return myProject;
    }

    public void setMyProject(Project myProject) {
        this.myProject = myProject;
    }


    private String myCompilerOutputPath;
    // Pair<Source Path, Package Prefix>
    private List<Pair<String, String>> mySourcePaths;
    // Pair<Library path, Source path>
    private final List<Pair<String, String>> myModuleLibraries = new ArrayList<Pair<String, String>>();

    public final void setCompilerOutputPath(String compilerOutputPath) {
        myCompilerOutputPath = acceptParameter(compilerOutputPath);
    }

    public List<Pair<String, String>> getSourcePaths() {
        if (mySourcePaths == null) {
            final List<Pair<String, String>> paths = new ArrayList<Pair<String, String>>();
            @NonNls final String path = getContentEntryPath() + File.separator + "src";
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


    private static String getUrlByPath(final String path) {
        return VfsUtil.getUrlForLibraryRoot(new File(path));
    }

    public void addModuleLibrary(String moduleLibraryPath, String sourcePath) {
        myModuleLibraries.add(Pair.create(moduleLibraryPath, sourcePath));
    }

    @Nullable
    protected static String getPathForOutputPathStep() {
        return null;
    }


//    public static class TestOrderEntry extends ModuleOrderEntryImpl {
//        public TestOrderEntry(String name, RootModelImpl rmi) {
//            super(name, rmi);
//        }
//    }
}