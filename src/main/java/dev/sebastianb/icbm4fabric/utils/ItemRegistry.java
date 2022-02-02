/*
 * Copyright (c) 2022 mc.neko.rs contributors <https://mc.neko.rs>
 *
 * Licensed with GNU Lesser General Public License v3.0
 */

package dev.sebastianb.icbm4fabric.utils;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

public interface ItemRegistry extends ItemConvertible {
  default ItemStack asStack() {
    return new ItemStack(asItem());
  }

  default ItemStack getStack(int amount) {
    return new ItemStack(asItem(), amount);
  }
}