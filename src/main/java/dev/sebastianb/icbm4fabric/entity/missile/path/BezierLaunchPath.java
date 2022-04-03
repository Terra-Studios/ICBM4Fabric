package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;

public class BezierLaunchPath extends AbstractEquationPath {
    double height;

    double yChange;
    double groundChange;

    public BezierLaunchPath(AbstractMissileProjectile missile, double height) {
        super(missile);

        groundChange = Math.sqrt(change.getX() * change.getX() + change.getZ() * change.getZ());

        this.height = height;

        yChange = change.getY();

        double maxHeight = evaluateFunctionAtValue(groundChange * (height - Math.sqrt(height * height - yChange * height)) / yChange);

        if (yChange == 0) {
            maxHeight = evaluateFunctionAtValue(groundChange / 2);
        }

        this.height *= height / maxHeight;
    }

    @Override
    public double evaluateFunctionAtValue(double time) {
        time = time / groundChange;

        return yChange * time * time * time + 3 * height * time - 3 * height * time * time;
    }
}
