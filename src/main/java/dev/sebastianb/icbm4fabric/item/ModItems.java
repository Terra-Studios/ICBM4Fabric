package dev.sebastianb.icbm4fabric.item;

import java.util.Arrays;
import java.util.Locale;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.item.missile.MissileItem;
import dev.sebastianb.icbm4fabric.utils.ItemRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItems {
    public static ItemGroup ITEMS_GROUP = FabricItemGroupBuilder
            .create(new Identifier(Constants.MOD_ID, Constants.Items.ITEM_GROUP))
            .icon(() -> new ItemStack(net.minecraft.item.Items.DIAMOND))
            .build();

    public static enum Missiles implements ItemRegistry {
        TATER;

        private final String name;
        private final Item item;

        Missiles() {
            name = this.toString().toLowerCase(Locale.ROOT) + "_missile";
            item = ICBM4Fabric.REGISTRY.item(new MissileItem(new Item.Settings().group(ITEMS_GROUP).maxCount(1)), name);
        }

        @Override
        public Item asItem() {
            return item;
        }
    }

    public static void register() {
        // Make sure everything is initialized
        Arrays.stream(Missiles.values()).forEach(v -> System.out.println(v.name));
    }
}
