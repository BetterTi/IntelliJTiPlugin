package com.bajdev.titanium.intellij.plugins;

import com.intellij.debugger.engine.JavaDebugProcess;
import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebugProcessStarter;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import com.intellij.xdebugger.impl.XDebugProcessConfiguratorStarter;
import com.intellij.xdebugger.impl.XDebugSessionImpl;
import com.intellij.xdebugger.impl.ui.XDebugSessionData;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.debugger.DebuggableRunConfiguration;

import java.io.File;
import java.net.InetSocketAddress;

public class TitaniumRunConfiguration extends LocatableConfigurationBase  implements
				ProgramRunner, RunConfiguration, DebuggableRunConfiguration {


	private CommandLineState _s;
	private String _srcDirectory = "Resources";
	private String _platform = "Android";
	private String _executionDirectory = "./";
	private String _module;

	protected TitaniumRunConfiguration(Project project, @NotNull ConfigurationFactory factory, String name) {
		super(project, factory, name);
	}

	@Nullable
	public String getTitaniumPath(){
		if(_module == null){
			return null;
		}
		Module m = ModuleManager.getInstance(getProject()).findModuleByName(_module);
		if(m == null){
			return null;
		}
		return m.getOptionValue("titanium.cli");

	}

	@NotNull
	@Override
	public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
		return new TitaniumSettingsEditor(getProject());
	}
	@Nullable
	@Override
	public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment executionEnvironment) throws ExecutionException {

		_s = new CommandLineState(executionEnvironment) {
			@NotNull
			@Override
			protected ProcessHandler startProcess() throws ExecutionException {
				System.out.println(new File(".").getAbsolutePath());
				GeneralCommandLine l = new GeneralCommandLine(
								"titanium",
								"build",
								"--platform", "android",
								"--debug-host", "localhost:54321");
				l.setWorkDirectory(getProject().getBasePath());

				OSProcessHandler p = new OSProcessHandler(l.createProcess());
		    ProcessTerminatedListener.attach(p);

		    return p;
			}

		};

		return _s;
	}



	@Override
	public SettingsEditor<ConfigurationPerRunnerSettings> getRunnerSettingsEditor(ProgramRunner runner) {
		return super.getRunnerSettingsEditor(runner);
	}

	@NotNull
	@Override
	public InetSocketAddress computeDebugAddress() throws ExecutionException {
		InetSocketAddress a = new InetSocketAddress(8999);
		return a;
	}



	@NotNull
	@Override
	public XDebugProcess createDebugProcess(@NotNull InetSocketAddress inetSocketAddress, @NotNull final XDebugSession xDebugSession, @Nullable ExecutionResult executionResult, @NotNull final ExecutionEnvironment executionEnvironment) throws ExecutionException {

		return new Debugger(xDebugSession, this);
	}



	@NotNull
	@Override
	public String getRunnerId() {
		return "100l";
	}

	@Override
	public boolean canRun(@NotNull String s, @NotNull RunProfile runProfile) {
		return true;
	}

	public void setModule(String module) {
		_module = module;
	}

	public String getModule() {
		return _module;
	}


	private static class SaveableSetting implements RunnerSettings{
		public String srcDirectory;
		public String platform;
		public String executionDirectory;

		@Override
		public void readExternal(Element element) throws InvalidDataException {
			srcDirectory = element.getAttribute("srcDir").getValue();
			platform     = element.getAttribute("platform").getValue();
			executionDirectory = element.getAttributeValue("executionDir");
		}

		@Override
		public void writeExternal(Element element) throws WriteExternalException {
			element.setAttribute("srcDir", srcDirectory);
			element.setAttribute("platform", platform);
			element.setAttribute("executionDir", executionDirectory);

		}
	}

	@Nullable
	@Override
	public RunnerSettings createConfigurationData(ConfigurationInfoProvider configurationInfoProvider) {
		SaveableSetting setting = new SaveableSetting();
		setting.srcDirectory = getSrcDirectory();
		setting.platform = getPlatform();
		setting.executionDirectory = getExecutionDirectory();
		return setting;
	}

	@Override
	public void checkConfiguration(RunnerSettings runnerSettings, @Nullable ConfigurationPerRunnerSettings configurationPerRunnerSettings) throws RuntimeConfigurationException {

	}

	@Override
	public void onProcessStarted(RunnerSettings runnerSettings, ExecutionResult executionResult) {

	}

	@Nullable
	@Override
	public SettingsEditor getSettingsEditor(Executor executor, RunConfiguration runConfiguration) {
		return null;
	}


	@Override
	public void execute(@NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
		XDebuggerManager.getInstance(getProject()).startSession(executionEnvironment, new XDebugProcessConfiguratorStarter() {
			@Override
			public void configure(XDebugSessionData xDebugSessionData) {

			}


			@NotNull
			@Override
			public XDebugProcess start(@NotNull XDebugSession session) throws ExecutionException {
        XDebugSessionImpl sessionImpl = (XDebugSessionImpl)session;
//        ExecutionResult executionResult = sessionImpl.getDebugProcess().getE.getExecutionResult();
//        sessionImpl.addExtraActions(executionResult.getActions());
//        if (executionResult instanceof DefaultExecutionResult) {
//            sessionImpl.addRestartActions(((DefaultExecutionResult)executionResult).getRestartActions());
//            sessionImpl.addExtraStopActions(((DefaultExecutionResult)executionResult).getAdditionalStopActions());
//        }
        return new Debugger(session, TitaniumRunConfiguration.this);
			}
		});
	}

	@Override
	public void execute(@NotNull ExecutionEnvironment executionEnvironment, @Nullable Callback callback) throws ExecutionException {
		System.out.println("meh!");
	}


	public String getSrcDirectory() {
		return _srcDirectory;
	}

	public void setSrcDirectory(String srcDirectory) {
		_srcDirectory = srcDirectory;
	}

	public String getPlatform() {
		return _platform;
	}

	public void setPlatform(String platform) {
		_platform = platform;
	}

	public String getExecutionDirectory() {
		return _executionDirectory;
	}

	public void setExecutionDirectory(String executionDirectory) {
		_executionDirectory = executionDirectory;
	}

}
