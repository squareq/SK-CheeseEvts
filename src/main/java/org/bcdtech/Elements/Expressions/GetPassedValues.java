package org.bcdtech.Elements.Expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bcdtech.CustomEvents.CustomEvent;
import org.bcdtech.CustomEvents.CustomEventBukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
@Name("Values of a custom event")
@Description("Get the passed value(s) of a custom event.")
@Since("1.0")
@SuppressWarnings("unused")
public class GetPassedValues extends SimpleExpression<Object> {

    Integer pattern;
    Expression<CustomEvent> customEventExpression;

    static{
        Skript.registerExpression(GetPassedValues.class, Object.class, ExpressionType.PROPERTY, "[the] passed (argument[s]|value[s])", "[the] passed (argument[s]|value[s]) of %customevent%");
    }

    @Override
    protected Object @Nullable [] get(Event event) {
        CustomEventBukkit customEvent = (pattern != 1) ? (CustomEventBukkit) event : new CustomEventBukkit(customEventExpression.getSingle(event));
        Object[] values = customEvent.getCustomEvent().getEventValues();
        return Arrays.stream(values).toArray();
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<Object> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "";
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        pattern = matchedPattern;
        if(pattern == 0){
            if(!getParser().isCurrentEvent(CustomEventBukkit.class)){
                Skript.error("The 'passed values' expressions is only usable within a CustomEventBukkit.");
                return false;
            }
        } else if (pattern == 1) {
            this.customEventExpression = (Expression<CustomEvent>) expressions[0];
        }
        return true;
    }
}
