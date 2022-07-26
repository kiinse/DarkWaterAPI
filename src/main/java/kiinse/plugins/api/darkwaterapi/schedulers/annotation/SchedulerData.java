package kiinse.plugins.api.darkwaterapi.schedulers.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SchedulerData {

    String name() default "";

    long delay() default 0L;

    long period() default 20L;
}
