package net.threadly.deadlib.module;

import net.threadly.deadlib.enums.State;

public interface Module {
    String getId();

    String getConfigPath();

    State getState();

    void activate();

    void deactivate();

    void restart();

}
