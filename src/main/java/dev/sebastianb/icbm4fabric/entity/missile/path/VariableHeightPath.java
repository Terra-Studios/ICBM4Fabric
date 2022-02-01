package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.util.math.Vec3d;

public class VariableHeightPath extends AbstractLaunchPath{

    double a;
    double h;
    double height;
    double p;

    Vec3d initialPosVec;
    Vec3d targetPosVec;

    double dYaw;
    final double SPEED = 7;
    final int NUMBER_OF_DISTANCE_CHECKS = 4;

    public VariableHeightPath(AbstractMissileProjectile rocket, double maxHeight) {
        super(rocket);
        this.height = maxHeight;

        initialPosVec = new Vec3d(rocket.initialLocation.getX() + .5, rocket.initialLocation.getY(), rocket.initialLocation.getZ() + .5);
        targetPosVec = new Vec3d(rocket.finalLocation.getX() + .5, rocket.finalLocation.getY(), rocket.finalLocation.getZ() + .5);


        Vec3d change = new Vec3d(initialPosVec.getX() - targetPosVec.getX(),
                targetPosVec.getY() - initialPosVec.getY(),
                initialPosVec.getZ() - targetPosVec.getZ());

        System.out.println(change);

        double groundDist = Math.sqrt(change.getX() * change.getX() + change.getZ() * change.getZ());
        double yChange = change.getY();

        dYaw = Math.atan2(change.getX(), change.getZ());

        a =  (-2.0 * (Math.sqrt(maxHeight * (-yChange + maxHeight)))) / (groundDist * groundDist) + (yChange - 2.0 * maxHeight) / (groundDist * groundDist);
        h = Math.sqrt(-(maxHeight / a));
    }

    @Override
    public void updateMotion() {
        double groundDistTraveled = Math.sqrt((initialPosVec.getX() - rocket.getPos().getX()) * (initialPosVec.getX() - rocket.getPos().getX()) + (initialPosVec.getZ() - rocket.getPos().getZ()) * (initialPosVec.getZ() - rocket.getPos().getZ()));

        double distToCheck = groundDistTraveled;

        double groundChange = 0;

        for (int i = 0; i < NUMBER_OF_DISTANCE_CHECKS; i++) {
            double distToFocus = Math.sqrt(Math.pow(a * Math.pow(distToCheck - h, 2) - 1 / (4 * a), 2) + Math.pow(distToCheck - h, 2));

            double slope = ((a * Math.pow(distToCheck - h, 2) + height) - (height + 1 / (4 * a) + distToFocus)) / (distToCheck - h);

            double distChange = 1 / Math.sqrt(1 + slope * slope) * SPEED / NUMBER_OF_DISTANCE_CHECKS;
            distToCheck += distChange;
            groundChange += distChange;
        }

        double targetGroundDistance = groundDistTraveled + groundChange;

        Vec3d targetPosition = new Vec3d(initialPosVec.getX() - Math.sin(dYaw) * (targetGroundDistance),
                a * (targetGroundDistance - h) * (targetGroundDistance - h) + height + initialPosVec.getY(),
                initialPosVec.getZ() - Math.cos(dYaw) * (targetGroundDistance));

//        Vec3d targetPosition = new Vec3d(-Math.sin(dYaw) * groundChange + rocket.getPos().getX(),
//                a * (targetGroundDistance - h + 1.7) * (targetGroundDistance - h + 1.7) + height + initialPosVec.getY(),
//                -Math.cos(dYaw) * groundChange + rocket.getPos().getZ());

        System.out.println(targetPosition);

        Vec3d newVelocity = new Vec3d(targetPosition.getX() - rocket.getPos().getX(),
                targetPosition.getY() - rocket.getPos().getY(),
                targetPosition.getZ() - rocket.getPos().getZ());

        rocket.setVelocity(newVelocity);
    }


}
