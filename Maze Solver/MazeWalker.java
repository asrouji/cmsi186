/**
 * MazeWalker is the object that is responsible for staking out a path down some
 * maze. Given a 2D array of maze cells and a starting location, it calculates
 * the next "legal" move such that the walker can eventually cover every square
 * in the maze that is reachable from that starting location.
 */
public class MazeWalker {
    /**
     * The possible states of the current "walk" through a maze.
     */
    public enum WalkerState {
        /**
         * Indicates that the maze walker has reached its assigned destination.
         */
        THERE_ALREADY,

        /**
         * Indicates that the maze walker has concluded that it is impossible to
         * reach its destination.
         */
        IMPOSSIBLE_TO_GET_THERE,

        /**
         * Indicates that the maze walker would like to move left.
         */
        MOVE_LEFT,

        /**
         * Indicates that the maze walker would like to move up.
         */
        MOVE_UP,

        /**
         * Indicates that the maze walker would like to move right.
         */
        MOVE_RIGHT,

        /**
         * Indicates that the maze walker would like to move down.
         */
        MOVE_DOWN
    }

    /**
     * The data structure for maintaining the current path.
     */
    private WalkerState[] path;

    /**
     * The index for the current node in the path.
     */
    private int pathIndex;

    /**
     * The data structure for keeping track of "passed" squares.
     */
    private boolean[][] beenThere;

    /**
     * The maze that is being walked.
     */
    private Maze maze;

    /**
     * The column of the walker's destination.
     */
    private int destinationColumn;

    /**
     * The row of the walker's destination.
     */
    private int destinationRow;

    /**
     * Initializes the MazeWalker, providing it with the maze to use and the
     * walker's destination.
     */
    public MazeWalker(Maze maze, int destinationRow, int destinationColumn) {
        this.maze = maze;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;

        // The path stack starts out empty.
        path = new WalkerState[this.maze.getMazeWidth() * this.maze.getMazeHeight()];
        pathIndex = -1;

        // The "been-there" array starts off completely clear.
        beenThere = new boolean[this.maze.getMazeHeight()][this.maze.getMazeWidth()];
        for (int row = 0; row < beenThere.length; row++) {
            for (int column = 0; column < beenThere[row].length; column++) {
                beenThere[row][column] = false;
            }
        }
    }

    /**
     * Takes a step toward reaching the given destination from the given current
     * location, and returns either the direction of the next step, whether or
     * not that destination has been reached, or whether that destination is
     * impossible to reach. Directions are in this oder: left, up, right, down.
     * As an optional extension, pick a direction with a customizable approach.
     */
    public WalkerState areWeThereYet(int currentRow, int currentColumn) {
        beenThere[currentRow][currentColumn] = true;
        var currentLocation = maze.getLocation(currentRow, currentColumn);
        // TODO: Update beenThere with where we've been.
        pathIndex++;
        // TODO: If we're THERE_ALREADY, return that right away.
        if (currentRow == destinationRow && currentColumn == destinationColumn) {
            return WalkerState.THERE_ALREADY;
        }
        // TODO: Otherwise, find an adjacent, available square.
        // By default, we go in order from left, up, right, down.
        // (Optional) You can implement other algorithms if you want!
        if (currentLocation.getLeft().isOpen() 
                && !beenThere[currentLocation.getLeft().getRow()][currentLocation.getLeft().getColumn()]) {
            path[pathIndex] = WalkerState.MOVE_LEFT;
            return path[pathIndex];
        }
        if (currentLocation.getAbove().isOpen() 
                && !beenThere[currentLocation.getAbove().getRow()][currentLocation.getAbove().getColumn()]) {
            path[pathIndex] = WalkerState.MOVE_UP;
            return path[pathIndex];
        }
        if (currentLocation.getRight().isOpen() 
                && !beenThere[currentLocation.getRight().getRow()][currentLocation.getRight().getColumn()]) {
            path[pathIndex] = WalkerState.MOVE_RIGHT;
            return path[pathIndex];
        }
        if (currentLocation.getBelow().isOpen() 
                && !beenThere[currentLocation.getBelow().getRow()][currentLocation.getBelow().getColumn()]) {
            path[pathIndex] = WalkerState.MOVE_DOWN;
            return path[pathIndex];
        }
        // TODO: If no such square, then backtrack (i.e., return the reverse of
        // the direction we took to get here.
        // If we can't backtrack, then it's IMPOSSIBLE_TO_GET_THERE.
        if (pathIndex < 0) {
            return WalkerState.IMPOSSIBLE_TO_GET_THERE;
        }
        if (pathIndex != 0 && path[pathIndex - 1] != null) {
            switch (path[pathIndex - 1]) {
                case MOVE_LEFT:
                    path[pathIndex] = null;
                    pathIndex -= 2;
                    return WalkerState.MOVE_RIGHT;
                case MOVE_UP: 
                    path[pathIndex] = null;
                    pathIndex -= 2;
                    return WalkerState.MOVE_DOWN;
                case MOVE_RIGHT:
                    path[pathIndex] = null;
                    pathIndex -= 2;
                    return WalkerState.MOVE_LEFT;
                case MOVE_DOWN:
                    path[pathIndex] = null;
                    pathIndex -= 2;
                    return WalkerState.MOVE_UP;
                default:
                    return WalkerState.IMPOSSIBLE_TO_GET_THERE;
            }
        }
        // TODO: If there is an adjacent square, return the direction to get there.

        // Tip: always update pathIndex and path accordingly!

        return WalkerState.IMPOSSIBLE_TO_GET_THERE;

    }

    /**
     * Returns a representation of the locations which the walker has visited.
     * The 2D array's dimensions should correspond to those of the walker's
     * assigned maze.
     */
    public boolean[][] getBeenThere() {
        return beenThere;
    }

    /**
     * Returns the current path taken by the walker.
     */
    public WalkerState[] getCurrentPath() {
        WalkerState[] currentPath = new WalkerState[pathIndex + 1];
        for (int i = 0; i < pathIndex + 1; i++) {
            currentPath[i] = path[i];
        }
        return currentPath;
    }
}
