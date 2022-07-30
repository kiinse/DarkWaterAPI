package kiinse.plugins.darkwaterapi.api.commands;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public interface DarkRegisteredCommand {

    @NotNull Command getAnnotation();

    @NotNull Method getMethod();

    @NotNull Object getInstance();

}
