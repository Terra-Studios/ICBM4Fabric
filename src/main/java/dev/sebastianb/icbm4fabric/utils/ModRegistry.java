/*
 * Copyright (c) 2022 mc.neko.rs contributors <https://mc.neko.rs>
 *
 * Licensed with GNU Lesser General Public License v3.0
 */

package dev.sebastianb.icbm4fabric.utils;

import java.util.Arrays;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Registry helper for Minecraft's {@link Registry Registry}.
 * 
 * <h2>Example:</h2>
 *<pre><code>public class MyMod implements ModInitializer {
 *  // Create an instance of ModRegistry to use for registering.
 *  public static final ModRegistry REGISTRY = new ModRegistry("modid");
 *
 *  // Consider moving this into your ModItems class
 *  public enum Ingots implements ItemRegistry {
 *    STEEL, BRONZE, OSMIUM;
 *
 *    public final String name;
 *    public final Item item;
 *
 *    Ingots() {
 *      name = this.toString().toLowerCase(Locale.ROOT) + "_ingot";
 *      item = REGISTRY.item(new Item(new Item.Settings().group(ItemGroup.MATERIALS)), name);
 *    }
 *
 *    {@literal @}Override
 *    public Item asItem() {
 *      return item; 
 *    }
 *  }
 *
 *  {@literal @}Override
 *  public void onInitialize() {
 *    // Iterate over all items to make sure they get loaded
 *    Arrays.stream(Ingots.values()).forEach(i -> System.out.println(i.name));
 *
 *    // Usually you would that in your ModItems class and use this instead:
 *    // ModItems.register();
 *  }
 *}</code></pre>
 * 
 * @see #ModRegistry(String modid)
 * @see ClientRegistry
 */
public class ModRegistry {
  private final String modid;

  /**
   * Creates an instance of {@link ModRegistry} with the specified modid
   *
   * @param modid Your mod's MOD_ID
   * 
   * <h2>Example:</h2>
   *<pre><code>public static final ModRegistry REGISTRY = new ModRegistry("modid");</code></pre>
   *
   * @see ClientRegistry
   */
  public ModRegistry(String modid) {
    this.modid = modid;
  }

  /**
   * Registers a new {@link Item Item}
   *
   * @param item
   *        The {@link Item Item} to register
   * @param name
   *        The name of the {@link Item Item}
   * 
   * @return Registered instance of the {@link Item Item}
   * 
   * <h2>Example:</h2>
   *<pre><code>public void register() {
   *  // Register an Item
   *  MyMod.REGISTRY.item(new CookedAxolotl(), "cooked_axolotl");
   *}</code></pre>
   *
   * @see ClientRegistry
   */
  public Item item(Item item, String name) {
    return Registry.register(Registry.ITEM, new Identifier(modid, name), item);
  }

  /**
   * Registers a new {@link Block Block}
   *
   * @param block
   *        The {@link Block Block} to register
   * @param name
   *        The name of the {@link Block Block}
   * 
   * @return Registered instance of the {@link Block Block}
   * 
   * <h2>Example:</h2>
   *<pre><code>public void register() {
   *  // Register a block that doesn't have a BlockItem attached, so it will only be placeable with /setblock or other code
   *  MyMod.REGISTRY.block(new MysteryBlock(), "mystery_block");
   *}</code></pre>
   *
   * @see ClientRegistry
   */
  public Block block(Block block, String name) {
    return Registry.register(Registry.BLOCK, new Identifier(modid, name), block);
  }

  /**
   * Registers a new {@link Block Block} with a {@link BlockItem BlockItem}
   *
   * @param block
   *        The {@link Block Block} to register
   * @param settings
   *        The {@link Item.Settings Item.Settings} of the {@link BlockItem BlockItem}
   * @param name
   *        The name of the {@link Block Block}
   * 
   * @return Registered instance of the {@link Block Block}
   * 
   * <h2>Example:</h2>
   *<pre><code>public void register() {
   *  // Register a block so cool, that doesn't burn when dropped in lava or fire
   *  MyMod.REGISTRY.block(new VeryCoolBlock(), new Item.Settings().group(ItemGroup.BUILDING_BLOCKS).fireproof(), "very_cool_block");
   *}</code></pre>
   *
   * @see ClientRegistry
   */
  public Block block(Block block, Item.Settings settings, String name) {
    Block b = block(block, name);
    item(new BlockItem(b, settings), name);
    return b;
  }

  /**
   * Registers a new {@link Block Block} with a {@link BlockItem BlockItem}
   *
   * @param block
   *        The {@link Block Block} to register
   * @param group
   *        The {@link ItemGroup ItemGroup} of the {@link BlockItem BlockItem}
   * @param name
   *        The name of the {@link Block Block}
   * 
   * @return Registered instance of the {@link Block Block}
   * 
   * <h2>Example:</h2>
   *<pre><code>public void register() {
   *  // This block will be available in the Building Blocks tab in Creative
   *  MyMod.REGISTRY.block(new CoolBrickBlock(), ItemGroup.BUILDING_BLOCKS, "cool_brick_block");
   *}</code></pre>
   *
   * @see ClientRegistry
   */
  public Block block(Block block, ItemGroup group, String name) {
    return block(block, new Item.Settings().group(group), name);
  }

  /**
   * Registers a new {@link BlockEntityType BlockEntityType}
   * <p>You shouldn't use this unless you can't use {@link #blockEntity(FabricBlockEntityTypeBuilder.Factory factory, String name, Block... blocks) blockEntity()}
   *
   * @param <T>
   *        The {@link BlockEntity BlockEntity} being registered
   *
   * @param type
   *        The {@link BlockEntityType BlockEntityType} to register
   * @param name
   *        The name of the {@link BlockEntityType BlockEntityType}
   * 
   * @return {@link BlockEntityType BlockEntityType} of the registered {@link BlockEntity BlockEntity}
   * 
   * @see ClientRegistry
   */
  public <T extends BlockEntity> BlockEntityType<T> blockEntityType(BlockEntityType<T> type, String name) {
    return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, name), type);
  }

  /**
   * Registers a new {@link BlockEntity BlockEntity}
   *
   * @param <T>
   *        The {@link BlockEntity BlockEntity} being registered
   *
   * @param factory
   *        The {@link FabricBlockEntityTypeBuilder.Factory FabricBlockEntityTypeBuilder.Factory} of the {@link BlockEntity BlockEntity}
   * @param name
   *        The name of the {@link BlockEntity BlockEntity}
   * @param blocks
   *        The {@link Block Block}(s) using this {@link BlockEntity BlockEntity}
   * 
   * @return {@link BlockEntityType BlockEntityType} of the registered {@link BlockEntity BlockEntity}
   * 
   * <h2>Example:</h2>
   *<pre><code>public void register() {
   *  // If your BlockEntity constructor only needs BlockPos and BlockState
   *  MyMod.REGISTRY.blockEntity(SomeBlockEntity::new, "some_block_entity", ModBlocks.SOME_BLOCK);
   * 
   *  // If your BlockEntity constructor needs more than just BlockPos and BlockState
   *  MyMod.REGISTRY.blockEntity((pos, state) -> new OtherBlockEntity(pos, state, extra, arguments)), "other_block_entity", ModBlocks.OTHER_BLOCK);
   * 
   *  // This function takes a variable amount of blocks so you can also do
   *  MyMod.REGISTRY.blockEntity(SomeBlockEntity::new, "some_block_entity", ModBlocks.SOME_BLOCK, ModBlocks.SOME_BLOCK_2_ELECTRIC_BOOGALOO);
   *}</code></pre>
   *
   * @see ClientRegistry
   */
  public <T extends BlockEntity> BlockEntityType<T> blockEntity(FabricBlockEntityTypeBuilder.Factory<T> factory,
      String name, Block... blocks) {
    return blockEntityType(FabricBlockEntityTypeBuilder.create(factory, blocks).build(null), name);
  }

  /**
   * Registers a new {@link BlockEntity BlockEntity}
   *
   * @param <T>
   *        The {@link BlockEntity BlockEntity} being registered
   *
   * @param factory
   *        The {@link FabricBlockEntityTypeBuilder.Factory FabricBlockEntityTypeBuilder.Factory} of the {@link BlockEntity BlockEntity}
   * @param name
   *        The name of the {@link BlockEntity BlockEntity}
   * @param blocks
   *        The {@link rs.neko.mc.BlockRegistry BlockRegistry}(s) using this {@link BlockEntity BlockEntity}
   * 
   * @return {@link BlockEntityType BlockEntityType} of the registered {@link BlockEntity BlockEntity}
   * 
   * <h2>Example:</h2>
   *<pre><code>public void register() {
   *  // If your BlockEntity constructor only needs BlockPos and BlockState
   *  MyMod.REGISTRY.blockEntity(SomeBlockEntity::new, "cool_block", ModBlocks.CoolBlocks.values());
   * 
   *  // If your BlockEntity constructor needs more than just BlockPos and BlockState
   *  MyMod.REGISTRY.blockEntity((pos, state) -> new OtherBlockEntity(pos, state, extra, arguments)), "even_cooler_block", ModBlocks.EvenCoolerBlocks.values());
   *}</code></pre>
   *
   * @see ClientRegistry
   */
  public <T extends BlockEntity> BlockEntityType<T> blockEntity(FabricBlockEntityTypeBuilder.Factory<T> factory,
      String name, BlockRegistry... blocks) {
    return blockEntity(factory, name, Arrays.stream(blocks).map(i -> i.asBlock()).toArray(Block[]::new));
  }

  /**
   * Registers a new {@link EntityType EntityType}
   * @param <T>
   *        The {@link Entity Entity} being registered
   *
   * @param builder
   *        The {@link FabricEntityTypeBuilder FabricEntityTypeBuilder}
   * @param name
   *        The name of the {@link EntityType EntityType}
   * 
   * @return The registered {@link EntityType EntityType}
   * 
   * <h2>Example:</h2>
   *<pre><code>public void register() {
   *  // Register a new entity
   *  MyMod.REGISTRY.entityType(FabricEntityTypeBuilder.create(SpawnGroup.MISC, NuclearCreeper::new)), "nuclear_creeper");
   *}</code></pre>
   *
   * @see ClientRegistry
   */
  public <T extends Entity> EntityType<T> entityType(FabricEntityTypeBuilder<T> builder, String name) {
    return Registry.register(Registry.ENTITY_TYPE, new Identifier(modid, name), builder.build());
  }
}