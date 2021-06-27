package dev.sebastianb.icbm4fabric.entity.rocket.path;

import dev.sebastianb.icbm4fabric.entity.rocket.AbstractRocketProjectile;
import net.minecraft.util.math.Vec3d;

public class VariableHeightPath extends AbstractLaunchPath{

    double a;
    double h;
    double height;
    double p;

    double dYaw;
    final double SPEED = 15;

    public VariableHeightPath(AbstractRocketProjectile rocket, double maxHeight) {
        super(rocket);
        this.height = maxHeight;

        Vec3d change = new Vec3d(rocket.initialLocation.getX() - rocket.finalLocation.getX(),
                rocket.finalLocation.getY() - rocket.initialLocation.getY(),
                rocket.initialLocation.getZ() - rocket.finalLocation.getZ());

        double groundDist = Math.sqrt(change.getX() * change.getX() + change.getZ() * change.getZ());
        double yChange = change.getY();

        dYaw = Math.atan2(change.getX(), change.getZ());

        a =  (-2.0 * (Math.sqrt(maxHeight * (-yChange + maxHeight)))) / (groundDist * groundDist) + (yChange - 2.0 * maxHeight) / (groundDist * groundDist);
        h = Math.sqrt(-(maxHeight / a));
    }

    @Override
    public void updateMotion() {
        double groundDistTraveled = Math.sqrt((rocket.initialLocation.getX() - rocket.getX()) * (rocket.initialLocation.getX() - rocket.getX()) + (rocket.initialLocation.getZ() - rocket.getZ()) * (rocket.initialLocation.getZ() - rocket.getZ()));
        double distToFocus = Math.sqrt(Math.pow(a * Math.pow(groundDistTraveled - h, 2) - 1 / (4 * a), 2) + Math.pow(groundDistTraveled - h, 2));

        double slope = ((a * Math.pow(groundDistTraveled - h, 2) + height) - (height + 1 / (4 * a) + distToFocus)) / (groundDistTraveled - h);

        double yChange = -(a * slope) / Math.sqrt(1 + slope * slope) * SPEED;
        double groundChange = a / Math.sqrt(1 + slope * slope) * SPEED;

        rocket.setVelocity(
                Math.sin(dYaw) * groundChange,
                yChange,
                Math.cos(dYaw) * groundChange
        );
    }


}
