package dev.sebastianb.icbm4fabric.utils;

import java.util.Arrays;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRegistry {
  private String mod_id;

  public ModRegistry(String mod_id) {
    this.mod_id = mod_id;
  }

  public Item item(Item item, String name) {
    return Registry.register(Registry.ITEM, new Identifier(mod_id, name), item);
  }

  public Block block(Block block, String name) {
    return Registry.register(Registry.BLOCK, new Identifier(mod_id, name), block);
  }

  public Block block(Block block, Item.Settings settings, String name) {
    Block b = block(block, name);
    item(new BlockItem(b, settings), name);
    return b;
  }

  public Block block(Block block, ItemGroup group, String name) {
    return block(block, new Item.Settings().group(group), name);
  }

  public <T extends BlockEntity> BlockEntityType<T> blockEntity(BlockEntityType<T> bet, String name) {
    return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(mod_id, name), bet);
  }

  public <T extends BlockEntity> BlockEntityType<T> blockEntity(FabricBlockEntityTypeBuilder.Factory<T> factory, String name, Block... block) {
    return blockEntity(FabricBlockEntityTypeBuilder.create(factory, block).build(null), name);
  }

  public <T extends BlockEntity> BlockEntityType<T> blockEntity(FabricBlockEntityTypeBuilder.Factory<T> factory, String name, BlockEnum... blocks) {
		return blockEntity(factory, name, Arrays.stream(blocks).map(i -> i.asBlock()).toArray(Block[]::new));
	}
}