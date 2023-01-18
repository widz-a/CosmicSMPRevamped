package withicality.csmp.enums;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import withicality.csmp.CosmicPlugin;

import java.util.Arrays;
import java.util.List;

public enum Power {
    NOT_SELECTED("Not Selected.", true, Material.BARRIER), //lazy lol
    PREVIEW1("First rpeview", false, Material.ACACIA_PLANKS),
    PREVIEW2("2nd preview", false, Material.BEDROCK),
    PREVIEW3("3rd ", true, Material.FLOWER_POT),
    PREVIEW3_1("i dont wan go to schol today ", true, Material.ACACIA_FENCE),
    PREVIEW3_2("i want to work on ", true, Material.AMETHYST_SHARD),
    PREVIEW3_3("funny roblox game ", true, Material.SADDLE),
    PREVIEW3_4("so im gonna ", true, Material.CACTUS),
    PREVIEW3_5("do a funny and ", true, Material.TOTEM_OF_UNDYING),
    PREVIEW3_6("take laxative ", true, Material.DIRT),
    PREVIEW3_7("to shit my guts out ", true, Material.HAY_BLOCK),
    PREVIEW3_8("and say i have diarrhea ", true, Material.DANDELION),
    PREVIEW3_9("b ", true, Material.NETHER_BRICK_SLAB),
    PREVIEW3_10("i dont go to school ", true, Material.NETHERITE_INGOT),
    PREVIEW3_11("also i am learning polish ", true, Material.BEEF),
    ;

    public static final NamespacedKey KEY = new NamespacedKey(CosmicPlugin.getInstance(), "powernameidybtb");
    public static final Power[] HOTBAR = Arrays.stream(Power.values()).filter(Power::isHotbar).toArray(Power[]::new);

    private final boolean hotbar;
    private final String name;
    private final Material material;

    Power(String name, boolean hotbar, Material material) {
        this.hotbar =  hotbar;
        this.name = name;
        this.material = material;
    }

    public boolean isHotbar() {
        return hotbar;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, name());
        meta.displayName(Component.text(name));
        item.setItemMeta(meta);
        return item;
    }

    public static List<Power> actualValues() {
        return Arrays.stream(Power.values()).filter(x -> !x.equals(NOT_SELECTED)).toList();
    }
}
