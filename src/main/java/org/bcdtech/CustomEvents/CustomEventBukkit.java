package org.bcdtech.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomEventBukkit extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final CustomEvent customEvent;


    public CustomEventBukkit(CustomEvent e){
        this.customEvent = e;
    }

    public CustomEvent getCustomEvent(){
        return customEvent;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }


}
