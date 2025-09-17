package cc.sunshine.feature.command.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandData {

    String name();
    String description();
    String[] alias();

}
