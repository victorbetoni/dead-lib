package net.threadly.deadlib.module.addons;

import net.threadly.deadlib.enums.State;
import net.threadly.deadlib.module.AbstractModule;

public class GunPropertiesModule extends AbstractModule {

    public GunPropertiesModule() {
        this.state = State.DEACTIVATED;
        this.configurationPath = "config/DeadLib/modules/gun_properties.config";
    }

    @Override
    public String getId() {
        return "gun-properties-module";
    }

    @Override
    public void activate() {
        this.state = State.ACTIVATING;
    }

    @Override
    public void deactivate() {
        this.state = State.DEACTIVATING;
    }

    @Override
    public void restart() {

    }
}
