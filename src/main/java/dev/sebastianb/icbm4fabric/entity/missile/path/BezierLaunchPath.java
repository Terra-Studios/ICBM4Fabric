package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.util.math.Vec3d;

public class BezierLaunchPath extends AbstractLaunchPath {
    Vec3d initialPos;
    Vec3d secondPoint;
    Vec3d thirdPoint;
    Vec3d finalPos;

    public static final int BLOCKS_PER_TICK = 1;

    Vec3d lastVelocity;

    double timeToTake;

    public BezierLaunchPath(AbstractMissileProjectile rocket, double maxHeight) {
        super(rocket);

        initialPos = Vec3d.ofCenter(rocket.initialLocation);
        finalPos = Vec3d.ofCenter(rocket.finalLocation);

        double height = initialPos.getY() > finalPos.getY() ? initialPos.getY() + maxHeight : finalPos.getY() + maxHeight;

        secondPoint = new Vec3d(initialPos.getX(), height, initialPos.getZ());
        thirdPoint = new Vec3d(finalPos.getX(), height, finalPos.getZ());

        double distance = finalPos.subtract(initialPos).horizontalLength();

        timeToTake = distance / BLOCKS_PER_TICK;
    }

    @Override
    public void updateMotion() {
        double t = rocket.timeSinceStage / timeToTake;

        if (t >= 1) {

        }

        Vec3d quad1 = initialPos.lerp(secondPoint, t);
        Vec3d quad2 = secondPoint.lerp(thirdPoint, t);
        Vec3d quad3 = thirdPoint.lerp(finalPos, t);

        Vec3d linear1 = quad1.lerp(quad2, t);
        Vec3d linear2 = quad2.lerp(quad3, t);

        Vec3d targetPosition = linear1.lerp(linear2, t);

        Vec3d newVelocity = new Vec3d(targetPosition.getX() - rocket.getPos().getX(),
                targetPosition.getY() - rocket.getPos().getY(),
                targetPosition.getZ() - rocket.getPos().getZ());

        rocket.setVelocity(newVelocity);

        lastVelocity = newVelocity;
    }
}
