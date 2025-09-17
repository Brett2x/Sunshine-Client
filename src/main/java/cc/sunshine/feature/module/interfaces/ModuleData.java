package cc.sunshine.feature.module.interfaces;

import cc.sunshine.feature.module.enums.ModuleCategory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleData {
    String name();
    ModuleCategory category();
    String description() default "Default description";
    int key() default Short.MIN_VALUE;
    boolean enabled() default false;
}
