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
        gunRegistries.forEach((name, registry) -> {
            if (!node.getNode(name).isVirtual()) {
                CommentedConfigurationNode gunNode = node.getNode(name);
                GunItem gun = (GunItem) registry.get();
                gunNode.getNode("damage").setValue(gun.getDamage());
                gunNode.getNode("rpm").setValue(gun.getFireRateRPM());
                gunNode.getNode("acurracy").setValue(gun.getAccuracy());
                gunNode.getNode("allowed_firemodes").setValue(gun.getFireModes());
                gunNode.getNode("allowed_magazines").setValue(gun.getAcceptedMagazines());
                gunNode.getNode("allowed_atachments").setValue(gun.getAcceptedAttachments());
                gunNode.getNode("allowed_paints").setValue(gun.getAcceptedPaints());
                gunNode.getNode("reload_duration_ticks").setValue(gun.getReloadDurationTicks());
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
        try{
            Class<?> cdItemsClass = Class.forName("com.craftingdead.core.item.ModItems");
            DeferredRegister register = ReflectionUtils.reflectFieldAs(cdItemsClass, "ITEMS", null, DeferredRegister.class).get();
            Set<RegistryObject<?>> registries = ReflectionUtils.reflectFieldAs(register.getClass(), "entriesViews", register, Set.class).get();
            registries.forEach(registryObject -> {
                gunRegistries.put(ReflectionUtils.RegistryObjectUtils.reflectResourceName(registryObject), registryObject);
            });
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
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
