package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractEquationPath extends AbstractLaunchPath{

    Vec3d initialPosVec;
    Vec3d finalPosVec;

    Vec3d change;

    public final double SPEED = 5;
    private final double CHANGE_FOR_DERIVATIVE  = .00001;

    double dYaw;

    public AbstractEquationPath(AbstractMissileProjectile missile) {
        super(missile);

        initialPosVec = Vec3d.ofCenter(missile.initialLocation); // set initial and final pos
        finalPosVec = Vec3d.ofCenter(missile.getFinalLocation());

        change = new Vec3d(initialPosVec.getX() - finalPosVec.getX(), // get position change
                finalPosVec.getY() - initialPosVec.getY(),
                initialPosVec.getZ() - finalPosVec.getZ());

        dYaw = Math.atan2(change.getX(), change.getZ()); // set angle 
    }

    @Override
    public void updateMotion() {
        // get distance traveled 
        double groundDistanceTraveled = Math.sqrt((initialPosVec.getX() - missile.getPos().getX()) * (initialPosVec.getX() - missile.getPos().getX()) + (initialPosVec.getZ() - missile.getPos().getZ()) * (initialPosVec.getZ() - missile.getPos().getZ()));

        double heightAtPos = evaluateFunctionAtValue(groundDistanceTraveled); // where we should be at this potition
        double heightForDerivative = evaluateFunctionAtValue(groundDistanceTraveled + CHANGE_FOR_DERIVATIVE); // where we should be in a tiny bit

        double slope = (heightForDerivative - heightAtPos) / CHANGE_FOR_DERIVATIVE; // get slope at our position

        double groundChange = Math.sqrt(SPEED * SPEED / (slope * slope + 1)); // get how far we should travel horiztonally 

        double targetHeight = evaluateFunctionAtValue(groundDistanceTraveled + groundChange); // where we should be at our target location

        Vec3d targetPosition = new Vec3d(initialPosVec.getX() - Math.sin(dYaw) * (groundDistanceTraveled + groundChange), // target position
                initialPosVec.getY() + targetHeight,
                initialPosVec.getZ() - Math.cos(dYaw) * (groundDistanceTraveled + groundChange));

        Vec3d newVelocity = targetPosition.subtract(missile.getPos());

        missile.setVelocity(newVelocity); // set velocity in missile
        missile.move(MovementType.SELF, newVelocity); // move missile 
    }

    public abstract double evaluateFunctionAtValue(double value); // returns height relative to start given a value 
}
