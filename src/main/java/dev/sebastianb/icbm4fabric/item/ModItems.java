package dev.sebastianb.icbm4fabric.item;

import dev.sebastianb.icbm4fabric.Constants;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class ModItems {

    public static final Map<Identifier, Item> ITEMS = new HashMap<>();

    public static ItemGroup ITEMS_GROUP = FabricItemGroupBuilder.create(new Identifier(Constants.MOD_ID, Constants.Items.ITEM_GROUP))
            .icon(() -> new ItemStack(net.minecraft.item.Items.DIAMOND))
            .build();

    // missiles
    public static final Item TATER_MISSILE = registerItem(Constants.Items.TATER_MISSILE,
            new Item(new Item.Settings().group(ITEMS_GROUP).maxCount(1)));



    private static <T extends Item> T registerItem(String id, T item) {
        ITEMS.put(new Identifier(Constants.MOD_ID, id), item);
        return item;
    }

    public static void register() {
        ITEMS.forEach((identifier, item) -> Registry.register(Registry.ITEM, identifier, item));
    }

}
