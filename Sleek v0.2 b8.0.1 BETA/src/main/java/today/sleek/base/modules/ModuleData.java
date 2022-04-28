package today.sleek.base.modules;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleData {

    String name();
    ModuleCategory category();

    String description();

    int bind() default Keyboard.KEY_NONE;



}
