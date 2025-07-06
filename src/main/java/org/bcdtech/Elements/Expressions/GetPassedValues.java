package org.bcdtech.Elements.Expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.apache.commons.lang3.ArrayUtils;
import org.bcdtech.CustomEvents.CustomEvent;
import org.bcdtech.CustomEvents.CustomEventBukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

@Name("Values of a custom event")
@Description("Get the passed value(s) of a custom event.")
@Since("1.0")
@SuppressWarnings("unused")
public class GetPassedValues extends SimpleExpression<Object> {

    Integer pattern;
    Expression<CustomEvent> customEventExpression;
	Expression<Integer> position;
	private static final Integer[] PATTERNS_WITH_EXPRCUSTOEMVENT = {2, 3};
	private static final Integer[] PATTERNS_WITH_POSITION = {1, 3};
	private static final String ERROR = "The 'passed values' expressions is only usable within a CustomEventBukkit.";

    static{
        Skript.registerExpression(GetPassedValues.class, Object.class, ExpressionType.PROPERTY, "[BCD] [the] passed (argument[s]|value[s])", "[BCD] [the] %integer%[(st|nd|rd|th)] passed argument", "[BCD] [the] passed (argument[s]|value[s]) of %customevent%", "[BCD] [the] %integer%[(st|nd|rd|th)] passed argument of %customevent%");
    }

    @Override
    protected Object @Nullable [] get(Event event) {
		//When using a pattern containing a specification for CustomEvent type, then grab the provided type. Otherwise, simply default to the implemented method's parameter.
        CustomEventBukkit customEvent = (ArrayUtils.contains(PATTERNS_WITH_EXPRCUSTOEMVENT, pattern)) ? new CustomEventBukkit(customEventExpression.getSingle(event)) : (CustomEventBukkit) event;
		//If trying to grab an element at a specific position then use that value. Otherwise, simply just default to all of the values within the CustomEvent.
		try{
			Object[] values = (ArrayUtils.contains(PATTERNS_WITH_POSITION, pattern)) ? customEvent.getCustomEvent().getEventValue(position.getSingle(customEvent)) : customEvent.getCustomEvent().getEventValues();
			return Arrays.stream(values).toArray();//
		} catch(IndexOutOfBoundsException | NullPointerException e){//
			return null;
		}
    }

    @Override
    public boolean isSingle() {
		//You can only get one value from the index.
        return (ArrayUtils.contains(PATTERNS_WITH_POSITION, pattern));
    }

    @Override
    public Class<Object[]> getReturnType() {
        return Object[].class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "get passed values";
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        pattern = matchedPattern;
		switch(pattern) {
			//Pattern 0: [BCD] [the] passed (argument[s]|value[s])
			case 0:
				if (!getParser().isCurrentEvent(CustomEventBukkit.class)) {
					Skript.error(ERROR);
					return false;
				}
				break;
			//Pattern 1: [BCD] [the] %integer%[(st|nd|rd|th)] passed argument
			case 1:
				if (!getParser().isCurrentEvent(CustomEventBukkit.class)) {//
					Skript.error(ERROR);
					return false;
				}
				this.position = (Expression<Integer>) expressions[0];
				break;
			//Pattern 2: [BCD] [the] passed (argument[s]|value[s]) of %customevent%
			case 2:
				this.customEventExpression = (Expression<CustomEvent>) expressions[0];
				break;
			//Pattern 3: [BCD] [the] %integer%[(st|nd|rd|th)] passed argument of %customevent%
			case 3:
				this.position = (Expression<Integer>) expressions[0];
				this.customEventExpression = (Expression<CustomEvent>) expressions[1];
				break;
		}
        return true;
    }

	@Override
	public Class<?> @Nullable [] acceptChange(Changer.ChangeMode mode) {
		switch(mode){
			case DELETE:
			case SET:
			case ADD:
				if(ArrayUtils.contains(PATTERNS_WITH_POSITION, pattern)){
					Skript.error("Cannot set index " + pattern + " to anything!");
					break;
				}
			case REMOVE:
				if(ArrayUtils.contains(PATTERNS_WITH_POSITION, pattern)){
					Skript.error("Cannot remove anything from index " + pattern + "!");
					break;
				}
				return new Class[]{Object.class};
		}
		return null;
	}

	@Override
	public void change(Event event,  Object @Nullable [] delta, Changer.ChangeMode mode) {
		System.out.println(event.toString());
		CustomEvent customEvent = ArrayUtils.contains(PATTERNS_WITH_EXPRCUSTOEMVENT, pattern)
			? customEventExpression.getSingle(event)
			: ((CustomEventBukkit) event).getCustomEvent();
		if(customEvent == null){
			return;
		}
		switch(mode){
			case SET:
				if(this.position != null){
					return;
				} else{
					customEvent.setEventValues(delta, null);
				}
				break;
			case ADD:
				Object[] currentvalues = customEvent.getEventValues();
				Object[] combineValues = ArrayUtils.addAll(delta, currentvalues);
				if(this.position != null){
					return;
				} else{
					customEvent.setEventValues(combineValues, null);
				}
				break;
			case DELETE:
				if(this.position != null){
					customEvent.clearEventValues(this.position.getSingle(event));
				} else{
					customEvent.clearEventValues();
				}
				break;
			case REMOVE:
				//Loop through each value in the delta
				for(Object v : Objects.requireNonNull(delta)){
					//Check if the value stored at the index of customEvent values matches delta
					for(int i = 0; i < customEvent.getEventValues().length; i++){
						Object[] thisvalue = Arrays.stream(customEvent.getEventValue(i+1)).toArray();
						if(thisvalue[0].toString().equals(v.toString())){
							customEvent.clearEventValues(i+1);
						}
					}
				}
		}
	}
}
