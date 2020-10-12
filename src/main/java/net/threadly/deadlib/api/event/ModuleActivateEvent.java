package net.threadly.deadlib.api.event;

import net.threadly.deadlib.module.Module;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;

public class ModuleActivateEvent implements Event {

    private Class<? extends Module> activatedModule;

    public ModuleActivateEvent(Class<? extends Module> activatedModule) {
        this.activatedModule = activatedModule;
    }

    @Override
    public Cause getCause() {
        return null;
    }

    @Override
    public Object getSource() {
        return null;
    }

    @Override
    public EventContext getContext() {
        return null;
    }
}
