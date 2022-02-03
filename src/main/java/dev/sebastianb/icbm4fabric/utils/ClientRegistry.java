/*
 * Copyright (c) 2022 mc.neko.rs contributors <https://mc.neko.rs>
 *
 * Licensed with GNU Lesser General Public License v3.0
 */

package dev.sebastianb.icbm4fabric.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientRegistry {
  private final String mod_id;

  public ClientRegistry(String mod_id) {
    this.mod_id = mod_id;
  }
}
