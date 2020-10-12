package net.threadly.deadlib.module;

import net.threadly.deadlib.enums.State;

public interface Module {
    void checkConfiguration() throws ClassNotFoundException;

    String getId();

    String getConfigPath();

    State getState();

    void activate();

    void deactivate();

    void restart();

}
