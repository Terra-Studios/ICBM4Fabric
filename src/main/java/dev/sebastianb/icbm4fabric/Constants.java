package dev.sebastianb.icbm4fabric;

import net.minecraft.util.Identifier;

public class Constants {
    public static String MOD_ID = "icbm4fabric";

    public interface Blocks {
        String ITEM_GROUP_BLOCKS = "blocks";
    }

    public interface Items {
        String ITEM_GROUP = "items";
    }

    public interface Entity {
        String TATER_MISSILE = "tater_missile";
    }

    public interface Packets {
        Identifier SUMMON_MISSILE = new Identifier(Constants.MOD_ID, "summon_missile");
        Identifier UPDATE_LAUNCH_SCREEN_FIELD = new Identifier(Constants.MOD_ID, "launch_field");
    }

}
