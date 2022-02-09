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

        initialPosVec = Vec3d.ofCenter(missile.initialLocation);
        finalPosVec = Vec3d.ofCenter(missile.getFinalLocation());

        change = new Vec3d(initialPosVec.getX() - finalPosVec.getX(),
                finalPosVec.getY() - initialPosVec.getY(),
                initialPosVec.getZ() - finalPosVec.getZ());

        dYaw = Math.atan2(change.getX(), change.getZ());
    }

    @Override
    public void updateMotion() {
        double groundDistanceTraveled = Math.sqrt((initialPosVec.getX() - missile.getPos().getX()) * (initialPosVec.getX() - missile.getPos().getX()) + (initialPosVec.getZ() - missile.getPos().getZ()) * (initialPosVec.getZ() - missile.getPos().getZ()));

        double heightAtPos = evaluateFunctionAtValue(groundDistanceTraveled);
        double heightForDerivative = evaluateFunctionAtValue(groundDistanceTraveled + CHANGE_FOR_DERIVATIVE);

        double slope = (heightForDerivative - heightAtPos) / CHANGE_FOR_DERIVATIVE;

        double groundChange = Math.sqrt(SPEED * SPEED / (slope * slope + 1));

        double targetHeight = evaluateFunctionAtValue(groundDistanceTraveled + groundChange);

        Vec3d targetPosition = new Vec3d(initialPosVec.getX() - Math.sin(dYaw) * (groundDistanceTraveled + groundChange),
                initialPosVec.getY() + targetHeight,
                initialPosVec.getZ() - Math.cos(dYaw) * (groundDistanceTraveled + groundChange));

        Vec3d newVelocity = targetPosition.subtract(missile.getPos());

        missile.setVelocity(newVelocity);
        missile.move(MovementType.SELF, newVelocity);
    }

    public abstract double evaluateFunctionAtValue(double value);
}
