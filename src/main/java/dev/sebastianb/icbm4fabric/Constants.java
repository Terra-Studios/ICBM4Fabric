package dev.sebastianb.icbm4fabric;

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



    public static class Blocks {
        public static final String ITEM_GROUP_BLOCKS = "blocks";
        public static final String TIER_1_MISSILE_LAUNCHER = "tier1_missile_launcher";
        public static final String TIER2_MISSILE_LAUNCHER = "tier2_missile_launcher";
        public static final String TIER3_MISSILE_LAUNCHER = "tier3_missile_launcher";


    }

}
