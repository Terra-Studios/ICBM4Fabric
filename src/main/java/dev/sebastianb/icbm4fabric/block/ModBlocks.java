package dev.sebastianb.icbm4fabric.block;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.launcher.GenericRocketLauncher;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


// https://github.com/StellarHorizons/Galacticraft-Rewoven/blob/ee3cdb6d8a0aa71f2469e5299351db4816dcaaa6/src/main/java/com/hrznstudio/galacticraft/block/GalacticraftBlocks.java#L215
// Credit to Galacticraft Rewoven
public class ModBlocks {

    //ITEM GROUPS
    public static final ItemGroup BLOCKS_GROUP = FabricItemGroupBuilder.create(
            new Identifier(Constants.MOD_ID, Constants.Blocks.ITEM_GROUP_BLOCKS))
            .icon(() -> new ItemStack(ModBlocks.TIER1_ROCKET_LAUNCHER)).build();

    public static final Block TIER1_ROCKET_LAUNCHER = registerBlock(new GenericRocketLauncher(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL), Constants.LauncherType.TIER1), Constants.Blocks.TIER1_MISSILE_LAUNCHER);
    public static final Block TIER2_ROCKET_LAUNCHER = registerBlock(new GenericRocketLauncher(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL), Constants.LauncherType.TIER2), Constants.Blocks.TIER2_MISSILE_LAUNCHER);
    public static final Block TIER3_ROCKET_LAUNCHER = registerBlock(new GenericRocketLauncher(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL), Constants.LauncherType.TIER3), Constants.Blocks.TIER3_MISSILE_LAUNCHER);

    public static void register() {

    }

    private static <T extends Block> T registerBlock(T block, String id) {
        return registerBlock(block, id, BLOCKS_GROUP);
    }

    private static <T extends Block> T registerBlock(T block, String id, ItemGroup group) {
        Identifier identifier = new Identifier(Constants.MOD_ID, id);
        Registry.register(Registry.BLOCK, identifier, block);
        BlockItem item = Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().group(group)));
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        return block;
    }

}
