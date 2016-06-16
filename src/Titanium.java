import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.module.Module;
import org.jetbrains.annotations.NotNull;

/**
 * Created by johnsba1 on 6/14/16.
 */
public class Titanium implements ModuleComponent {
    public Titanium(Module module) {
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
        return "Titanium";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
    }

    @Override
    public void projectClosed() {
        // called when project is being closed
    }

    @Override
    public void moduleAdded() {
        // Invoked when the module corresponding to this component instance has been completely
        // loaded and added to the project.
    }
}
