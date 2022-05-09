package dev.sebastianb.icbm4fabric.missile;

import dev.sebastianb.icbm4fabric.Constants;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MissileManager {
    public List<Missile> missiles;
    MinecraftServer server;

    private MissileDataPersistentState worldMissileData;
    private List<Missile> missilesToRemove;

    public static MissileManager missileManager;

    public MissileManager(MinecraftServer server) {
        this.server = server;
        missileManager = this;

        worldMissileData = getMissileDataForWorld(server.getWorld(World.OVERWORLD)); // should probably do this for every dimension but just doing it for the overworld rn

        missiles = new ArrayList<>();
        missilesToRemove = new ArrayList<>();

        for (var missileData : worldMissileData.missilesDatas) {
            this.missiles.add(new Missile(missileData, server.getWorld(World.OVERWORLD))); // add the missiles on load
        }
    }

    public static void register() { // register server events
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            missileManager.tick(server);
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            new MissileManager(server);
        });
    }

    public void tick(MinecraftServer server) {

        for (var missile : missiles) {
            missile.tick();

            // spawn particles where the missile is for now
            // TODO: spawn entities when they are loaded

            Vec3d pos = missile.getMissileData().getPos();
            Vec3d vel = missile.getMissileData().getVelocity();

            for (int i = 0; i < 10; i++) {
                missile.world.spawnParticles(ParticleTypes.FLAME, pos.getX(), pos.getY(), pos.getZ(), 1, vel.getX(), vel.getY(), vel.getZ(), .01D);
            }
        }

        for (var missile : missilesToRemove) { // use a list of missiles that need to be removed so that we don't change the list while looping over it
            int index = missiles.indexOf(missile);

            missiles.remove(index);
            worldMissileData.missilesDatas.remove(index);
            worldMissileData.markDirty();
        }

        missilesToRemove.clear(); // clear the list of missiles to be removed
    }

    public static MissileDataPersistentState getMissileDataForWorld(ServerWorld world) { // returns the missile data for a world
        return world.getPersistentStateManager().getOrCreate(MissileDataPersistentState::new, MissileDataPersistentState::new, Constants.Data.MISSILE_DATA);
    }

    public void addMissile(Missile missile) { // adds a missile to the manager
        this.missiles.add(missile);

        worldMissileData.addMissile(missile.getMissileData());
    }

    public void clearMissiles() {
        missiles.clear();
        worldMissileData.missilesDatas.clear();
        worldMissileData.markDirty();
    }

    public void removeMissile(Missile missile) {
        missilesToRemove.add(missile);
    }

//    public MissileManager getMissileManager(ServerWorld world)
}
