package com.betterti.titanium.intellij.plugins.debug;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.pom.Navigatable;
import com.intellij.xdebugger.XSourcePosition;
import org.jetbrains.annotations.NotNull;

public class TiSourcePosition implements XSourcePosition {
	private final VirtualFile _file;
	private final int _line;

	@NotNull
	public static XSourcePosition getSourcePosition(Project project, String fileUrl, int line) {
		fileUrl = fileUrl.replaceAll("app:", "");
		VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(
						"file://" + project.getBasePath() + "/Resources/"  + fileUrl);

		return new TiSourcePosition(file, line);
	}


	public TiSourcePosition(VirtualFile file, int line) {
		_file = file;
		_line = line;
	}

	@Override
	public int getLine() {
		return _line;
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@NotNull
	@Override
	public VirtualFile getFile() {
		return _file;
	}

	@NotNull
	@Override
	public Navigatable createNavigatable(@NotNull Project project) {
		return new OpenFileDescriptor(project, _file, _line, 0);
	}
}
