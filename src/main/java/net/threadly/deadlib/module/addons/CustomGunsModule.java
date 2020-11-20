package net.threadly.deadlib.module.addons;

import com.craftingdead.core.item.GunItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.threadly.deadlib.enums.State;
import net.threadly.deadlib.module.AbstractModule;
import net.threadly.deadlib.utils.ReflectionUtils;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Item;

import java.lang.reflect.Field;
import java.util.*;

public class CustomGunsModule extends AbstractModule {

    private CommentedConfigurationNode node = null;
    private Map<String, RegistryObject<?>> gunRegistries = null;

    public CustomGunsModule() {
        this.state = State.DEACTIVATED;
        this.configurationPath = "config/DeadLib/modules/custom_guns.config";
    }

    @Override
    public void checkConfiguration() {
        Class<?> modItemsClass = null;
        Class<?> deferredRegisterClass = null;
        DeferredRegister<?> register = null;
        Set<RegistryObject<?>> registries = null;
        try {
            modItemsClass = Class.forName("com.craftingdead.core.item.ModItems");
            deferredRegisterClass = Class.forName("net.minecraftforge.registries.DeferredRegister");
            register = (DeferredRegister<?>) modItemsClass.getDeclaredField("ITEMS").get(null);
            registries = (Set<RegistryObject<?>>) deferredRegisterClass.getDeclaredField("entriesView").get(register);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ex) {
            System.out.println("Error while checking CD classes.");
            Sponge.getServer().shutdown();
        }

        assert registries != null;
        Collection<RegistryObject<?>> guns = ReflectionUtils.RegistryObjectUtils.filterGenericParam(registries, GunItem.class);
        guns.forEach(gun -> gunRegistries.put(ReflectionUtils.RegistryObjectUtils.getResourceName(gun), gun));

        gunRegistries.forEach((name, registry) -> {
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
