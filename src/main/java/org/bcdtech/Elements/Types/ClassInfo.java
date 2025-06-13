package org.bcdtech.Elements.Types;

import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import org.bcdtech.CustomEvents.CustomEvent;
import org.bcdtech.CustomEvents.CustomEventBukkit;
import org.bcdtech.CustomEvents.CustomEventINTF;

import java.util.Arrays;

public class ClassInfo {

    static{
        EventValues.registerEventValue(CustomEventBukkit.class, CustomEvent.class, CustomEventBukkit::getCustomEvent);
        Classes.registerClass(new ch.njol.skript.classes.ClassInfo<>(CustomEventINTF.class, "customevent")
                .user("custom ?event[s]")
                .name("Custom Event")
                .description("A custom event.")
                .since("1.0")
                .parser(new Parser<>() {
                    @Override
                    public boolean canParse(final ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(CustomEventINTF o, int flags) {
                        return o.hasEventValues() ? o.getEventID() + " with values " + Arrays.toString(o.getEventValues()) : o.getEventID();
                    }

                    @Override
                    public String toVariableNameString(CustomEventINTF o) {
                        return this.toString(o, 0);
                    }
                })
        );
    }
}
