package kiinse.plugins.api.darkwaterapi.commands.manager;

import kiinse.plugins.api.darkwaterapi.commands.manager.annotation.Command;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public final class RegisteredCommand {

    private final Object instance;

    private final Method method;

    private final Command annotation;

    RegisteredCommand(@NotNull Method method, @NotNull Object instance, @NotNull Command annotation) {
        this.method = method;
        this.instance = instance;
        this.annotation = annotation;
    }

    public @NotNull Command getAnnotation() {
        return annotation;
    }

    public @NotNull Method getMethod() {
        return method;
    }

    public @NotNull Object getInstance() {
        return instance;
    }
}