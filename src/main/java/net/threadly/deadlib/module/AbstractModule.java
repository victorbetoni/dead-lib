package net.threadly.deadlib.module;

import net.threadly.deadlib.enums.State;

public abstract class AbstractModule implements Module {
    protected String configurationPath;
    protected State state;

    @Override
    public String getConfigPath() {
        return configurationPath;
    }

    @Override
    public State getState() {
        return state;
    }
}
