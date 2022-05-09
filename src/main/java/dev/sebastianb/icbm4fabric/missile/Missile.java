package dev.sebastianb.icbm4fabric.missile;

import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.blast.TaterBlast;
import dev.sebastianb.icbm4fabric.missile.path.AbstractLaunchPath;
import dev.sebastianb.icbm4fabric.missile.path.BezierLaunchPath;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.logging.Level;

public class Missile {
    private MissileData missileData;
    public ServerWorld world;

    private AbstractLaunchPath path;

    public Missile(ServerWorld world, Vec3d pos) { // creates a new missile at a given pos
        this.world = world;
        this.missileData = new MissileData(MissileManager.getMissileDataForWorld(world));
        missileData.setPos(pos);
    }

    public Missile(MissileData missileData, ServerWorld world) { // used to load missiles in on load
        this.missileData = missileData;
        this.world = world;
    }

    public void tick() {
//        path.updateMotion();
//        path.updateRotation();
//        if(world.isSpaceEmpty(new Box(missileData.getBlockPos()))) {
//            missileData.setStage(LaunchStage.EXPLODED);
//        }

        switch (missileData.getStage()) {
            case IDLE -> {
                missileData.setPitch(0);
                missileData.setVelocity(Vec3d.ZERO);
            }
            case LIT -> {
                missileData.setPitch(0);
                missileData.setVelocity(Vec3d.ZERO);

                if (missileData.getTimeSinceStage() > 50) {
                    missileData.setInitialPos(missileData.getBlockPos());

                    setPath(new BezierLaunchPath(this, 100));
                    missileData.setStage(LaunchStage.LAUNCHED);
                }
            }
            case LAUNCHED -> {
                if (path != null) {
                    path.updateMotion();
                    path.updateRotation();
                }
                // collision for explosion
                // make sure that the block where we are isn't where we started
                if (!world.isSpaceEmpty(new Box(missileData.getBlockPos())) && !missileData.getBlockPos().equals(missileData.getInitialPos())) {
                    missileData.setStage(LaunchStage.EXPLODED);
                }
            }
            case EXPLODED -> {

                ICBM4Fabric.LOGGER.log(Level.INFO, "EXPLOSION!");
                ICBM4Fabric.LOGGER.log(Level.INFO, "LOCATION = " + missileData.getBlockPos());
                new TaterBlast(world, missileData.getBlockPos());

                MissileManager.missileManager.removeMissile(this);
            }
        }

        missileData.setPos(missileData.getPos().add(missileData.getVelocity()));

//        System.out.println(missileData.getPos());

        missileData.setTimeSinceStage(missileData.getTimeSinceStage() + 1);
    }

    public void setPath(AbstractLaunchPath path) {
        this.path = path;
    }

    public MissileData getMissileData() {
        return missileData;
    }
}
