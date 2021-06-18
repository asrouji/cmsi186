/**
 * Filename: PiEstimator.java Description: Estimate Pi via dart throws Author:
 * Aidan Srouji Date: 1/25/20
 */
public class PiEstimator {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Exactly one argument required");
            }
            int dartCount = Integer.parseInt(args[0]);
            if (dartCount < 1) {
                System.err.print("At least one dart required");
            }
            System.out.println(estimate(dartCount));
        } catch (NumberFormatException e) {
            System.err.print("Argument must be an integer");
        } catch (IllegalArgumentException e) {
            System.err.print("Exactly one argument required");
        }
    }

    public static double estimate(int darts) {
        if (darts < 1) {
            throw new NumberFormatException("At least one dart required");
        }
        int hits = 0;
        for (int i = 0; i < darts; i++) {
            double x = Math.random();
            double y = Math.random();
            if (isHit(x, y)) {
                hits++;
            }
        }
        return (hits * 4) / (double) darts;
    }

    public static boolean isHit(double x, double y) {
        return (Math.pow(x, 2) + Math.pow(y, 2) <= 1);
    }

}
