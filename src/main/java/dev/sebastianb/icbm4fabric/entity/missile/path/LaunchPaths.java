package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;


public enum LaunchPaths {

    // All values inside the path ARE temporary values that get replaced on rocket set path
    MissingsPath(),
    VariableHeightPath(),
    BezierPath();

    public AbstractLaunchPath getLaunchPath(AbstractMissileProjectile rocket, LaunchPaths pathType, double maxHeight) {
        AbstractLaunchPath path = null;
        switch (pathType) {
            case MissingsPath -> {
                path = new MissingsPath(rocket);
            }
            case VariableHeightPath -> {
                path = new VariableHeightPath(rocket, maxHeight);
            }
            case BezierPath -> {
                path = new BezierLaunchPath(rocket, maxHeight);
            }
        }
        return path;
    }
}
