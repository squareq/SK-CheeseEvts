package org.bcdtech.Elements.Effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bcdtech.CustomEvents.CustomEvent;
import org.bcdtech.CustomEvents.CustomEventBukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
@Name("Call Custom Event")
@Description("Call a custom event to be listened for.")
@Since("1.0")
@SuppressWarnings("unused")
public class CallCustomEvent extends Effect {

    Expression<CustomEvent> customEvent;

    static {
        Skript.registerEffect(CallCustomEvent.class, "[BCD] call [the] custom event %customevent%");
    }

    @Override
    protected void execute(Event event) {
        CustomEventBukkit bukkitCustomEvent = new CustomEventBukkit(customEvent.getSingle(event));
        bukkitCustomEvent.callEvent();
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "[BCD] call [the] custom event " + customEvent.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        customEvent = (Expression<CustomEvent>) expressions[0];
        return true;
    }
}
