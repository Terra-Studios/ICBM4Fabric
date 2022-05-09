package dev.sebastianb.icbm4fabric.missile.path;

import dev.sebastianb.icbm4fabric.missile.Missile;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractEquationPath extends AbstractLaunchPath { // Everything here is basically the same as the other path package but changed to use the new missile system
    Vec3d initialPosVec;
    Vec3d finalPosVec;

    Vec3d change;

    public final double SPEED = 1;
    private final double CHANGE_FOR_DERIVATIVE  = .00001;

    double dYaw;

    public AbstractEquationPath(Missile missile) {
        super(missile);

        initialPosVec = Vec3d.ofCenter(missile.getMissileData().getInitialPos());
        finalPosVec = Vec3d.ofCenter(missile.getMissileData().getTargetPos());

        change = initialPosVec.subtract(finalPosVec);

        dYaw = Math.atan2(change.getX(), change.getZ());
        System.out.println(dYaw);
    }

    @Override
    public void updateMotion() {
        // get distance traveled
        Vec3d distanceTraveled = initialPosVec.subtract(missile.getMissileData().getPos());

        double groundDistanceTraveled = distanceTraveled.horizontalLength();

        double heightAtPos = evaluateFunctionAtValue(groundDistanceTraveled); // where we should be at this position
        double heightForDerivative = evaluateFunctionAtValue(groundDistanceTraveled + CHANGE_FOR_DERIVATIVE); // where we should be in a tiny bit

        double slope = (heightForDerivative - heightAtPos) / CHANGE_FOR_DERIVATIVE; // get slope at our position

        double groundChange = Math.sqrt(SPEED * SPEED / (slope * slope + 1)); // get how far we should travel horizontally

        double targetHeight = evaluateFunctionAtValue(groundDistanceTraveled + groundChange); // where we should be at our target location

        Vec3d targetPosition = new Vec3d(initialPosVec.getX() - Math.sin(dYaw) * (groundDistanceTraveled + groundChange), // target position
                initialPosVec.getY() + targetHeight,
                initialPosVec.getZ() - Math.cos(dYaw) * (groundDistanceTraveled + groundChange));

        Vec3d newVelocity = targetPosition.subtract(missile.getMissileData().getPos());

        missile.getMissileData().setVelocity(newVelocity);
    }

    public abstract double evaluateFunctionAtValue(double value);
}
