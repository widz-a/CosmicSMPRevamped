package withicality.csmp.manager;

import me.spartacus04.jext.config.Disc;
import me.spartacus04.jext.disc.DiscContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class LootManager {
    private static ItemStack jext(String namespace) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> ParameterDisc = Class.forName("me.spartacus04.jext.command.ParameterDisc");
        Class<?> Companion = ParameterDisc.getDeclaredField("Companion").get(Object.class).getClass();
        Disc disc = (Disc) Companion.getDeclaredMethod("getDisc", String.class).invoke(Disc.class, namespace);
        return new DiscContainer(disc).getDiscItem();
    }

    private static ItemStack vanilla(String namespace) {
        return new ItemStack(Material.valueOf(namespace.toUpperCase(Locale.ROOT)));
    }

    public static ItemStack getItem(String namespace, String type) {
        type = type.toLowerCase();
        try {
            return (ItemStack) LootManager.class.getDeclaredMethod(type, String.class).invoke(ItemStack.class, namespace);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}