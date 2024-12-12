import java.io.IOException;

public abstract class Module {
    protected User currentUser;

    public abstract void startModule() throws IOException;
}