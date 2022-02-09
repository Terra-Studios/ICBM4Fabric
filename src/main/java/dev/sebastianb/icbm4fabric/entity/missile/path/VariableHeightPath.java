package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;

public class VariableHeightPath extends AbstractEquationPath{
    double a;
    double h;
    double height;

    public VariableHeightPath(AbstractMissileProjectile missile, double maxHeight) {
        super(missile);

        this.height = maxHeight;

        double groundDist = change.horizontalLength();
        double yChange = change.getY();

        a =  (-2.0 * (Math.sqrt(maxHeight * (-yChange + maxHeight)))) / (groundDist * groundDist) + (yChange - 2.0 * maxHeight) / (groundDist * groundDist);
        h = Math.sqrt(-(maxHeight / a));
    }

    @Override
    public double evaluateFunctionAtValue(double value) {
        return a * (value - h) * (value - h) + height;
    }
}
