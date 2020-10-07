package net.threadly.deadlib;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

import java.util.Map;

@Plugin(
        id = "deadlib",
        name = "DeadLib",
        authors = {
                "threadly"
        }
)
public class DeadLib {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

    }
}
