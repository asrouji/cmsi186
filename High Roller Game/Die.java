import java.util.Random;

/**
 * A simple class for representing die objects. A die has a given number of
 * sides (at least) four, set when the die is constructed and which can never
 * be changed. The die's value can be changed, but only by calling its roll()
 * method.
 */
public class Die {

    private static Random random = new Random();
    public static String SIX_SIDED_DIE_EMOJI = "🎲";
    private int value;
    private int sides;

    // TODO: Add fields

    /**
     * Constructs a die with the given starting value and number of sides.
     *
     * @throws IllegalArgumentException if the number of sides is less than 4 or
     *     if the starting value is not consistent with the number of sides.
     */
    public Die(int initialValue, int sides) {
        if (sides < 4) {
            throw new IllegalArgumentException("At least four sides required");
        }
        if (initialValue > sides || initialValue < 1) { 
            throw new IllegalArgumentException("Die value not legal for die shape");
        }
        this.value = initialValue;
        this.sides = sides;
    }

    /**
     * Simulates a roll by randomly updating the value of this die. In addition to
     * mutating the die's value, this method also returns the new updated value.
     */
    public int roll() {
        value = random.nextInt(getNumberOfSides()) + 1;
        return value;
    }

    /**
     * Returns the number of sides of this die.
     */
    public int getNumberOfSides() {
        return this.sides;
    }

    /**
     * Returns the current value of this die.
     */
    public int getCurrentValue() {
        return this.value;
    }

    /**
     * Returns a description of this die, which is its value enclosed in square
     * brackets, without spaces, for example "[5]".
     */
    @Override public String toString() {
        return "[" + getCurrentValue() + "]";
    }
}
