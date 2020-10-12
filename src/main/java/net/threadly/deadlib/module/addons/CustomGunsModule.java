package net.threadly.deadlib.module.addons;

import com.craftingdead.core.item.GunItem;
import net.minecraftforge.fml.RegistryObject;
import net.threadly.deadlib.enums.State;
import net.threadly.deadlib.module.AbstractModule;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomGunsModule extends AbstractModule {

    private CommentedConfigurationNode node = null;
    private Map<String, RegistryObject<?>> gunsRegistries = new HashMap<>();

    public CustomGunsModule() {
        this.state = State.DEACTIVATED;
        this.configurationPath = "config/DeadLib/modules/custom_guns.config";
    }

    @Override
    public void checkConfiguration() {
        Class<?> modItemsClass = null;
        try {
            modItemsClass = Class.forName("com.craftingdead.core.item.ModItems");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        Arrays.asList(modItemsClass.getDeclaredFields()).forEach(field -> {
            Object obj = null;
            try {
                obj = field.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (obj instanceof RegistryObject) {
                RegistryObject<?> registryObject = (RegistryObject<?>) obj;
                if (registryObject.get() instanceof GunItem)
                    gunsRegistries.put(field.getName(), registryObject);
            }
        });
        gunsRegistries.forEach((name, registry) -> {
            if (!node.getNode(name).isVirtual()) {
                CommentedConfigurationNode gunNode = node.getNode(name);
                GunItem gun = (GunItem) registry.get();
                gunNode.getNode("damage").setValue(gun.getDamage());
                gunNode.getNode("rpm").setValue(gun.getFireRateRPM());
                gunNode.getNode("acurracy").setValue(gun.getAccuracy());
                gunNode.getNode("allowed-firemodes").setValue(gun.getFireModes());
                gunNode.getNode("allowed-magazines").setValue(gun.getAcceptedMagazines());
                gunNode.getNode("allowed-atachments").setValue(gun.getAcceptedAttachments());
                gunNode.getNode("allowed-paints").setValue(gun.getAcceptedPaints());
                gunNode.getNode("reload-duration-ticks").setValue(gun.getReloadDurationTicks());
            }
        });

    }

    @Override
    public String getId() {
        return "custom-guns";
    }

    @Override
    public void activate() {
        this.state = State.ACTIVATING;
        this.checkConfiguration();
    }

    @Override
    public void deactivate() {
        this.state = State.DEACTIVATING;
    }

    @Override
    public void restart() {

    }
}
