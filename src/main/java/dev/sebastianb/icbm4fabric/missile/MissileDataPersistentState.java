package dev.sebastianb.icbm4fabric.missile;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

import java.util.ArrayList;
import java.util.List;

public class MissileDataPersistentState extends PersistentState {

    List<MissileData> missilesDatas; // all of the different missile data

    public MissileDataPersistentState(NbtCompound nbt) { // for reinitialize on world load
        int numberOfMissiles = nbt.getInt("numberOfMissiles");

        missilesDatas = new ArrayList<>();

        for (int i = 0; i < numberOfMissiles; i++) {
            missilesDatas.add(new MissileData(nbt.getCompound(Integer.toString(i)), this));
        }
    }

    public MissileDataPersistentState() {
        missilesDatas = new ArrayList<>();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("numberOfMissiles", missilesDatas.size());

        int index = 0;
        for (MissileData missileData : missilesDatas) {
            nbt.put(Integer.toString(index), missileData.writeNbt(new NbtCompound()));

            index++;
        }

        return nbt;
    }

    public void addMissile(MissileData missileData) {
        missilesDatas.add(missileData);
        markDirty();
    }
}
