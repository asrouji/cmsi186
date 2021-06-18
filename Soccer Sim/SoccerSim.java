/**
 * Filename: SoccerSim.java Description: Soccer Simulation Author: Aidan Srouji
 * Date: 2/16/20
 */
public class SoccerSim {

    private Ball[] balls;
    private double timeSlice = 1.0;
    private double fieldWidth = 1000;
    private double fieldHeight = 1000;

    /**
     * Constructor to create a new soccer simulation with a field and soccer balls.
     * Are there zero arguments? Are there 4 args per ball? Can the args be
     * converted to doubles? Are the args within range? Are there customizations:
     * One extra argument represents a custom time slice. Two extra arguments
     * represent a custom field size, width followed by height. Three extra
     * arguments represent a custom field size followed by a custom time slice.
     *
     * @param args String array of the arguments supplied to the program
     */
    public SoccerSim(String... args) throws NumberFormatException, IllegalArgumentException {
        if (args.length <= 0) {
            throw new IllegalArgumentException("At least four arguments per ball required (x, y, dx, dy)");
        }
        if (args.length < 4) {
            throw new IllegalArgumentException("Requires 4 args per ball");
        }
        balls = new Ball[args.length / 4];
        try {
            for (int i = 3; i < args.length; i += 4) {
                double[] location = { Double.parseDouble(args[i - 3]), Double.parseDouble(args[i - 2]) };
                double[] velocity = { Double.parseDouble(args[i - 1]), Double.parseDouble(args[i]) };
                balls[i / 4] = new Ball(location, velocity);
            }
            if (args.length % 4 == 1) {
                timeSlice = Double.parseDouble(args[args.length - 1]);
                if (timeSlice <= 0) {
                    throw new IllegalArgumentException("Timeslice must be positive");
                }
            } else if (args.length % 4 == 2) {
                fieldWidth = Double.parseDouble(args[args.length - 2]);
                fieldHeight = Double.parseDouble(args[args.length - 1]);
            } else if (args.length % 4 == 3) {
                fieldWidth = Double.parseDouble(args[args.length - 3]);
                fieldHeight = Double.parseDouble(args[args.length - 2]);
                timeSlice = Double.parseDouble(args[args.length - 1]);
            }
        } catch (Exception e) {
            throw new NumberFormatException(e.getMessage());
        }
        if (fieldWidth <= 0) {
            throw new IllegalArgumentException("Field width must be positive");
        }
        if (fieldHeight <= 0) {
            throw new IllegalArgumentException("Field height must be positive");
        }
    }

    /**
     * Optional method for reporting the state of the simulation to System.out. (for
     * use with a visualization program like SoccerSim2D)
     */
    public void report() {
        // TODO: optional code goes here
    }

    /**
     * Moves the balls in the soccerBall array by 1 timeslice. Set ball out of
     * bounds depending on the new location.
     */
    public void simUpdate() {
        for (int i = 0; i < balls.length; i++) {
            balls[i].move(timeSlice);
            balls[i].checkBallOutOfBounds(fieldWidth, fieldHeight);
        }
    }

    /**
     * Checks if any balls collided with each other. If so, the simulation is over.
     *
     * @return int array of ballsCollided
     */
    public int[] collisionCheck() {
        int[] collidedBalls = new int[2];
        for (int i = 0; i < balls.length; i++) {
            for (int j = i + 1; j < balls.length; j++) {
                if (isCollision(balls[i], balls[j])) {
                    collidedBalls[0] = i;
                    collidedBalls[1] = j;
                    return collidedBalls;
                }
            }
        }
        return null;
    }

    /**
     * Helper method to determine if two balls are colliding.
     *
     * @return boolean flag (true if there is a collision, else false)
     */
    public boolean isCollision(Ball ball1, Ball ball2) {
        double[] ball1Location = ball1.getCurrentLocation();
        double[] ball2Location = ball2.getCurrentLocation();
        double horizontalDistance = (ball1Location[0] - ball2Location[0]);
        double verticalDistance = (ball1Location[1] - ball2Location[1]);
        double ballDistance = Math.sqrt(Math.pow(horizontalDistance, 2) + Math.pow(verticalDistance, 2));
        return ballDistance <= (Ball.BALL_RADIUS) * 2;
    }

    /**
     * Checks whether at least one ball is still moving. The simulation ends when
     * there's a collision, or all balls have stopped moving.
     *
     * @return boolean flag (true if at least one ball is moving, else false)
     */
    public boolean atLeastOneBallStillMoving() {
        for (int i = 0; i < balls.length; i++) {
            if (balls[i].isStillMoving()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Runs the simulation.
     *
     * @return String result (if there's a collision, and how many iterations)
     */
    public static String runSimulation(String... args) {
        int iterations = 0;
        SoccerSim sim = new SoccerSim(args);
        while (sim.atLeastOneBallStillMoving()) {
            iterations++;
            sim.simUpdate();
            if (sim.collisionCheck() != null) {
                return "Collision occurred between ball " + sim.collisionCheck()[0] + " and ball "
                        + sim.collisionCheck()[1] + ". Number of iterations: " + iterations;
            }
        }
        return "NO COLLISION POSSIBLE! Number of iterations: " + iterations;
    }

    /**
     * Main method of the simulation.
     *
     * @param args String array of the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(runSimulation(args));
    }
}
