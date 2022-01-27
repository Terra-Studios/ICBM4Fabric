package dev.sebastianb.icbm4fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Constants {
    public static String MOD_ID = "icbm4fabric";

    /**
     * Tier level for missile launcher.
     */
    public enum LauncherType {
        NOT_AVAILABLE, // If something wacky happens for whatever reason.
        TIER1,
        TIER2,
        TIER3

    }

    public interface Blocks {
        String ITEM_GROUP_BLOCKS = "blocks";
        String TIER1_MISSILE_LAUNCHER = "tier1_missile_launcher";
        String TIER2_MISSILE_LAUNCHER = "tier2_missile_launcher";
        String TIER3_MISSILE_LAUNCHER = "tier3_missile_launcher";
    }

    public interface Items {
        String ITEM_GROUP = "items";

        // missiles
        String TATER_MISSILE = "tater_missile";

    }

    public interface Entity {
        String TATER_MISSILE = "tater_missile";
    }

    public interface Packets {
        Identifier SUMMON_MISSILE = new Identifier(Constants.MOD_ID, "summon_missile");
    }

}
