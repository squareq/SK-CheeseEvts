package org.bcdtech.Elements.Expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.LiteralUtils;
import ch.njol.util.Kleenean;
import org.bcdtech.CustomEvents.CustomEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@Name("New Custom Event")
@Description("Create a new custom event with an ID and optional values.")
@Since("1.0")
@SuppressWarnings("unused")
public class NewCustomEvent extends SimpleExpression<CustomEvent> {

    Expression<String> id;
    Expression<Object> values;
    Integer pattern;

    static{
        Skript.registerExpression(NewCustomEvent.class, CustomEvent.class, ExpressionType.PATTERN_MATCHES_EVERYTHING, "[BCD] [a] new custom event [with id] %string%", "[BCD] [a] new custom event [with id] %string% [and (argument[s]|value[s]) %-objects%]");
    }
    @Override
    protected CustomEvent @Nullable [] get(Event event) {
        CustomEvent customEvent = new CustomEvent(id.getSingle(event));
        if(pattern == 1){
            customEvent.setEventValues(values.getAll(event));
        }
        return new CustomEvent[] {customEvent};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends CustomEvent> getReturnType() {
        return CustomEvent.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return (pattern == 1) ? "[BCD] [a] new custom event [with id] " + id.toString() : "[BCD] [a] new custom event [with id] " + id.toString() + " [and (argument[s]|value[s]) " + Arrays.toString(values.getAll(event));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.id = (Expression<String>) expressions[0];
        pattern = matchedPattern;
        if(pattern == 1) {
            this.values = LiteralUtils.defendExpression(expressions[1]);
            return (LiteralUtils.canInitSafely(this.values));
        }
        return true;
    }
}
