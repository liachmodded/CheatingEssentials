package com.luna.lib.util.math;

@SuppressWarnings("unused")
public class PolarCoords {
    /**
     * Theta is in degrees
     *
     * @param x
     * @param y
     * @return new double[] {r, theta}
     */
    public static double[] cartesianToPolar(double x, double y) {
        double r = Math.sqrt((x * x) + (y * y));
        // Math#atan returns radians
        double theta = Math.toDegrees(Math.atan2(x, y));
        return new double[]{r, theta};
    }
}
