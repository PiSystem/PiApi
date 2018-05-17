package tech.teslex.pi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PiCommand {
	String command();

	String[] aliases() default {};

	String[] permissions() default {};

	String description() default "";

	String usage() default "";
}
