package org.bcdtech.Elements.Events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import org.bcdtech.CustomEvents.CustomEventBukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
@Name("Custom Event")
@Description("Listen to when a custom event of the specified ID is called.")
@Since("1.0")
@SuppressWarnings("unused")
public class SkriptCustomEvent extends SkriptEvent {

    Literal<String> id;

    static{
        Skript.registerEvent("Custom Event", SkriptCustomEvent.class, CustomEventBukkit.class, "[BCD] custom event [(with|of) id] %string%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        this.id = (Literal<String>) args[0];
        return true;
    }

    @Override
    public boolean check(Event event) {
        CustomEventBukkit customEvent = (CustomEventBukkit) event;
        return customEvent.getCustomEvent().getEventID().equalsIgnoreCase(id.getSingle());
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "[BCD] custom event [(with|of) id] " + id.toString();
    }
}
