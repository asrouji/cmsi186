import java.nio.charset.UnmappableCharacterException;

/**
 * Filename: SkateRamp.java
 * Description: SkatRamp Object
 * Author: Aidan Srouji
 * Date: 3/17/2021
 */
public class SkateRamp {

    // TODO: define the instance variables
    private double percentage = 1.0;
    private double lowerBound;
    private double upperBound;
    private Function function;
    private double[] coefficients;

    /**
     *  The interface Function will be implemented by Polynomial and Sine.
     *  The concrete classes must implement the getY() method.
     */
    interface Function {
        double getY(double x);
    }

    class Polynomial implements Function {
        /**
         * Calculates y, given x, for a polynomial curve.
         */
        public double getY(double x) {
            double value = 0.0;
            for (int i = 0; i < coefficients.length; i++) {
                value += coefficients[i] * Math.pow(x, i);
            }
            return value;
        }
    }

    class Sine implements Function {
        /**
         * Calculates y, given x, for a sine curve.
         */
        public double getY(double x) {
            return Math.sin(x);
        }
    }

    /**
     * SkateRamp constructor: validates and sets up instance variables from args.
     */
    public SkateRamp(String... args) throws NumberFormatException, IllegalArgumentException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Must provide at least 3 args");
        } 
        int endingArgsLength = args[args.length - 1].indexOf("%") == -1 ? 2 : 3;
        if (args[0].equals("poly")) {
            function = new Polynomial();
            if (args.length < 2 + endingArgsLength) {
                throw new IllegalArgumentException("Need at least 1 coeff for poly");
            }
        } else {
            function = new Sine();
        }
        lowerBound = Double.parseDouble(args[args.length - endingArgsLength]);
        upperBound = Double.parseDouble(args[args.length - endingArgsLength + 1]);
        if (endingArgsLength == 3) {
            percentage = Double.parseDouble(args[args.length - 1].substring(0, args[args.length - 1].length() - 1));
        }
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Upper bound must be > lower bound");
        }
        if (percentage <= 0.0) {
            throw new IllegalArgumentException("% must be positive");
        }
        coefficients = new double[args.length - endingArgsLength - 1];
        for (int i = 0; i < args.length - endingArgsLength - 1; i++) {
            coefficients[i] = Double.parseDouble(args[i + 1]);
        }
    }

    /**
     * Estimates the area under the curve by calculating the area under an
     * increasing number of rectangles, until 2 areas are within 1% of each other.
     *
     * @return the estimated area
     */
    public double estimateAreaUnderRamp() {
        int rectangles = 1;
        double currentArea = 0;
        double previousArea = 0;
        double rectangleWidth;
        while (Math.abs(currentArea - previousArea) >= previousArea * percentage * 0.01) {
            rectangleWidth = (upperBound - lowerBound) / rectangles;
            previousArea = currentArea;
            currentArea = 0;
            for (double i = rectangleWidth / 2 + lowerBound; i < upperBound; i += rectangleWidth) {
                currentArea += rectangleWidth * function.getY(i);
            }
            rectangles++;
        }
        System.out.println("Iterations: " + (rectangles - 1));
        return currentArea;
    }

    /**
     * main() creates a new SkateRamp object and calls estimateAreaUnderRamp().
     * You should wrap your code inside a try/catch block.
     */
    public static void main(String[] args) {
        try {
            var ramp = new SkateRamp(args);
            System.out.println(ramp.estimateAreaUnderRamp());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
