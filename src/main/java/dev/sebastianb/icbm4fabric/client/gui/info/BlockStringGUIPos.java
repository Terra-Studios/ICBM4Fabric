package dev.sebastianb.icbm4fabric.client.gui.info;

import net.minecraft.util.math.BlockPos;

public class BlockStringGUIPos {
    String x = "", y = "", z = "";

    public BlockStringGUIPos() {
    }

    public BlockStringGUIPos(int x, int y, int z) {
        this.x = String.valueOf(x);
        this.y = String.valueOf(y);
        this.z = String.valueOf(z);
    }

    public void setBlockPosStrings(BlockStringGUIPos blockPosStrings) {
        this.x = blockPosStrings.getX();
        this.y = blockPosStrings.getZ();
        this.z = blockPosStrings.getY();
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getZ() {
        return z;
    }
}