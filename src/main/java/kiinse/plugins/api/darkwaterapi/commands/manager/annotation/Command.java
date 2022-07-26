package kiinse.plugins.api.darkwaterapi.commands.manager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * The command that the player must execute. For example /darkwater reload. Should start with a slash
     * @return Command
     */
    String command();

    /**
     * Number of received parameters. Default - 0
     * @return Number of parameters
     */
    int parameters() default 0;

    /**
     * Allows you to disable restrictions on the number of parameters in a command. Disabled by default
     * @return Enabled or Disabled
     */
    boolean overrideParameterLimit() default false;

    /**
     * Allows you to disable the use of the command by non-players (for example, via the console). Disabled by default
     * @return Enabled or Disabled
     */
    boolean disallowNonPlayer() default false;

    /**
     * Required rights to use the command. Default is empty
     * @return Permissions to use the command
     */
    String permission() default "";

}
