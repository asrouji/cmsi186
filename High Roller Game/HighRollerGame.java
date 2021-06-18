import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A dice set holds a collection of Die objects. All of the die objects have the
 * same number of sides.
 */
public class DiceSet {

    private Die[] dice;
    private int sidesOnEachDie;
    private String descriptor;

    /**
     * Creates a DiceSet containing the given number of dice, each with the given
     * number of sides. All die values start off as 1. Throws an
     * IllegalArgumentException if either less than two dice were provided or if it
     * is asked to make dice with less than 4 sides.
     */
    public DiceSet(int sidesOnEachDie, int numberOfDice) {
        if (numberOfDice < 2) {
            throw new IllegalArgumentException("At least two dice required");
        }
        if (sidesOnEachDie < 4) {
            throw new IllegalArgumentException("Dice must have at least four sides");
        }
        this.sidesOnEachDie = sidesOnEachDie;
        dice = new Die[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dice[i] = new Die(1, sidesOnEachDie);
        }
        descriptor = numberOfDice + "d" + sidesOnEachDie;
    }

    /**
     * Creates a DiceSet where each die has the given number of sides, with the
     * given values.
     */
    public DiceSet(int sidesOnEachDie, int... values) {
        if (values.length < 2) {
            throw new IllegalArgumentException("At least two dice required");
        }
        dice = new Die[values.length];
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Die(values[i], sidesOnEachDie);
        }
        descriptor = values.length + "d" + sidesOnEachDie;
    }

    /**
     * Returns the descriptor of the dice set; for example "5d20" for a set with
     * five dice of 20 sides each; or "2d6" for a set of two six-sided dice.
     */
    public String getDescriptor() {
        return descriptor;
    }

    /**
     * Returns the total of the values of each die in the set.
     */
    public int getTotal() {
        int sum = 0;
        for (int i = 0; i < dice.length; i++) {
            sum += dice[i].getCurrentValue();
        }
        return sum;
    }

    /**
     * Rolls all the dice in the set.
     */
    public void rollAll() {
        for (int i = 0; i < dice.length; i++) {
            rollDie(i);
        }
    }

    /**
     * Rolls the i-th die, updating its value.
     */
    public void rollDie(int i) {
        dice[i].roll();
    }

    /**
     * Returns the value of the i-th die.
     */
    public int getDie(int i) {
        return dice[i].getCurrentValue();
    }

    /**
     * Returns the values of each of the dice in a list.
     */
    public List<Integer> getCurrentValues() {
        List<Integer> result = new ArrayList<Integer>(dice.length);
        for (int i = 0; i < dice.length; i++) {
            result.add(dice[i].getCurrentValue());
        }
        return result;
    }

    /**
     * Returns whether this dice set has the same distribution of values as another
     * dice set. The two dice sets must have the same number of dice and the same
     * number of sides per dice, and there must be the same number of each value in
     * each set.
     */
    public boolean matches(DiceSet diceSet) {
        if (diceSet.sidesOnEachDie != sidesOnEachDie) {
            return false;
        }
        List<Integer> sortedDice = getCurrentValues();
        List<Integer> sortedDiceSet = diceSet.getCurrentValues();
        Collections.sort(sortedDiceSet);
        Collections.sort(sortedDice);
        for (int i = 0; i < dice.length; i++) {
            if (!sortedDice.get(i).equals(sortedDiceSet.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation in which each of the die strings are joined
     * without a separator, for example: "[2][5][2][3]".
     */
    @Override
    public String toString() {
        String diceString = "";
        for (int i = 0; i < dice.length; i++) {
            diceString += "[" + dice[i].getCurrentValue() + "]";
        }
        return diceString;
    }
}
