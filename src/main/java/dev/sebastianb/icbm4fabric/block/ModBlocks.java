package dev.sebastianb.icbm4fabric.block;

import java.util.Arrays;
import java.util.Locale;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncher;
import dev.sebastianb.icbm4fabric.utils.BlockRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // ITEM GROUPS
    public static final ItemGroup BLOCKS_GROUP = FabricItemGroupBuilder.create(
            new Identifier(Constants.MOD_ID, Constants.Blocks.ITEM_GROUP_BLOCKS))
            .icon(() -> new ItemStack(MissileLaunchers.TIER1.block)).build();

    public static enum MissileLaunchers implements BlockRegistry {
        TIER1, TIER2, TIER3;

        private final String name;
        private final Block block;

        MissileLaunchers() {
            name = this.toString().toLowerCase(Locale.ROOT) + "_missile_launcher";
            block = ICBM4Fabric.REGISTRY.block(new GenericMissileLauncher(), BLOCKS_GROUP, name);
        }

        @Override
        public Item asItem() {
            return block.asItem();
        }

        @Override
        public Block asBlock() {
            return block;
        }
    }

    public static void register() {
        // Make sure everything is initialized
        Arrays.stream(MissileLaunchers.values()).forEach(v -> System.out.println(v.name));
    }
}
