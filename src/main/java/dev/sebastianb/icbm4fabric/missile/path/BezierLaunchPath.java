package dev.sebastianb.icbm4fabric.missile.path;

import dev.sebastianb.icbm4fabric.missile.Missile;

public class BezierLaunchPath extends AbstractEquationPath {

    double height;

    double yChange;
    double groundChange;

    public BezierLaunchPath(Missile missile, double height) {
        super(missile);

        groundChange = Math.sqrt(change.getX() * change.getX() + change.getZ() * change.getZ()); // get total ground distance to travel

        this.height = height;

        yChange = change.getY();

        double maxHeight = evaluateFunctionAtValue(groundChange * (height - Math.sqrt(height * height - yChange * height)) / yChange); // calculate the current max height

        if (yChange == 0) { // if the yChange is 0 the above stuff doesn't work, but it would be in the middle
            maxHeight = evaluateFunctionAtValue(groundChange / 2);
        }

        this.height *= height / maxHeight; // change height to actually make the highest point at the desired height
    }

    @Override
    public double evaluateFunctionAtValue(double value) {
        value = value / groundChange; // normalize time to between 0 and 1 across the rocket path

        return yChange * value * value * value + 3 * height * value - 3 * height * value * value; // equation for a bezier curve
    }
}
