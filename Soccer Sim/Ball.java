import java.text.DecimalFormat;

/**
 * Filename: Ball.java Description: Ball object class Author: Aidan Srouji Date:
 * 2/16/20
 */
public class Ball {

    private static final double FRICTION_COEFFICIENT = 0.99;
    public static final double BALL_RADIUS = 4.45;
    public static final int X_INDEX = 0;
    public static final int Y_INDEX = 1;

    // instance variables
    private boolean isInBounds = true; // all balls will start in bounds by default
    private double[] centerLocation = new double[2]; // ball location (inches)
    private double[] currentVelocity = new double[2]; // in inches per second

    /**
     * Constructor to make a new Ball.
     *
     * @param location location of the ball
     * @param velocity the initial velocity of the ball
     */
    public Ball(double[] location, double[] velocity) {
        centerLocation = location;
        currentVelocity = velocity;
    }

    /**
     * Returns the current velocity of the ball.
     */
    public double[] getCurrentVelocity() {
        return currentVelocity;
    }

    /**
     * Returns the current coordinates of the ball's center.
     */
    public double[] getCurrentLocation() {
        return centerLocation;
    }

    /**
     * Returns the ball's current speed, which is defined as the magnitude of the
     * current velocity vector.
     */
    public double getCurrentSpeed() {
        return Math.sqrt(Math.pow(currentVelocity[0], 2) + Math.pow(currentVelocity[1], 2));
    }

    /**
     * Determines if the ball is still moving.
     *
     * @return boolean true if ball is moving, false if at rest Note: at rest is
     *         defined as speed <= 1.0
     */
    public boolean isStillMoving() {
        return getCurrentSpeed() > 1.0;
    }

    /**
     * Assuming a field of the given width and height, checks whether the ball would
     * be out of bounds at its current position. Use the official soccer definition
     * of this term: the _entire_ ball must be outside the field in order to be
     * considered as out of bounds.
     *
     * <p>
     * The field's center is assumed to be the origin. Thus, its x coordinates range
     * from -fieldWidth / 2 to fieldWidth / 2 and its y coordinates range from
     * -fieldHeight / 2 to fieldHeight / 2.
     * </p>
     *
     * <p>
     * If the ball is out of bounds, this sets its velocity to (0.0, 0.0) and its
     * "isInBounds" value to false, effectively taking it out of the simulation.
     * </p>
     *
     * @param fieldWidth  double-precision value of the designated field width
     * @param fieldHeight double-precision value of the designated field height
     */
    public void checkBallOutOfBounds(double fieldWidth, double fieldHeight) {
        if (Math.abs(centerLocation[0]) - BALL_RADIUS > fieldWidth / 2
                || Math.abs(centerLocation[1]) - BALL_RADIUS > fieldHeight / 2) {
            currentVelocity[0] = 0.0;
            currentVelocity[1] = 0.0;
            isInBounds = false;
        }
    }

    /**
     * Updates the velocity of the ball after the given timeslice, using FRICTION.
     *
     * @param timeSlice double-precision value of the elapsed time, in seconds
     *
     * @return double[] of new velocity, in inches per second
     */
    public double[] updateSpeedsForOneTick(double timeSlice) {
        currentVelocity[0] = currentVelocity[0] * Math.pow(FRICTION_COEFFICIENT, timeSlice);
        currentVelocity[1] = currentVelocity[1] * Math.pow(FRICTION_COEFFICIENT, timeSlice);
        return currentVelocity;
    }

    /**
     * Updates the ball's location and velocity.
     *
     * @param timeSlice double-precision value of the elapsed time, in seconds
     */
    public void move(double timeSlice) {
        centerLocation[0] += currentVelocity[0] * timeSlice;
        centerLocation[1] += currentVelocity[1] * timeSlice;
        updateSpeedsForOneTick(timeSlice);
    }

    /**
     * Our venerable "toString()" representation.
     *
     * @return String-y version of what this Ball is
     */
    @Override
    public String toString() {
        DecimalFormat dfp = new DecimalFormat("#0.00");
        DecimalFormat dfv = new DecimalFormat("#0.0000");
        String output = "location <" + dfp.format(centerLocation[X_INDEX]) + ", " + dfp.format(centerLocation[Y_INDEX])
                + ">";

        // Additional tab in case the location string so far is too short.
        if (output.indexOf(">") <= 23) {
            output += "\t";
        }

        if (!isInBounds) {
            output += "\t<out of bounds>";
        } else if (!isStillMoving()) {
            output += "\t<at rest>";
        } else {
            output += "\tvelocity <" + dfv.format(currentVelocity[X_INDEX]) + " X and "
                    + dfv.format(currentVelocity[Y_INDEX]) + " Y> in/sec";
        }

        return output;
    }
}
