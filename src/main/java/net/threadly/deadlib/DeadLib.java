package net.threadly.deadlib;

import com.google.inject.Inject;
import net.threadly.deadlib.module.Module;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.network.ChannelRegistrar;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.util.Map;

@Plugin(
        id = "deadlib",
        name = "DeadLib",
        authors = {
                "threadly"
        }
)
public class DeadLib {

    @ConfigDir(sharedRoot = false)
    private File configDir;

    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private Map<String, File> config;

    private static DeadLib instance;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        if(!configDir.exists()) configDir.mkdir();
    }

    public static DeadLib getInstance() {
        return instance;
    }

    private void registerModule(String id, Class<? extends Module> module) {
    }

}
