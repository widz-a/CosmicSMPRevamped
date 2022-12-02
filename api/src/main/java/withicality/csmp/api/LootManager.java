package withicality.csmp.api;

import me.tajam.jext.config.ConfigDiscManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class LootManager {
    private static ItemStack jext(String namespace) {
        return new ItemStack(ConfigDiscManager.getInstance().getDisc(namespace).getDiscItem());
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