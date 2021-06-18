import java.util.ArrayList;
import java.util.List;

/**
 * Filename: GinormInt.java
 * Description:
 * Author:
 * Date:
*/

public class GinormInt implements Comparable<GinormInt> {
    // TODO: define your static and instance variables here
    public static final GinormInt ONE = new GinormInt("1");
    public static final GinormInt ZERO = new GinormInt("0");
    public static final GinormInt TEN = new GinormInt("10");
    private int[] digits;
    private int[] reversedDigits;
    private boolean isNegative = false;

    /**
     * Constructor takes in a string, saves it, checks for a sign character,
     * checks to see if it's all valid digits, and reverses it for later use.
     *
     * @param  value  String value to make into a GinormInt
     */
    public GinormInt(String value) {
        if (value.substring(0, 1).equals("-")) {
            isNegative = true;
            value = value.substring(1);
        }
        char[] chars = value.toCharArray();
        digits = new int[value.length()];
        reversedDigits = new int[value.length()];
        for (int i = 0; i < digits.length; i += 1) {
            if (!Character.isDigit(chars[i])) {
                throw new IllegalArgumentException("Sorry, all characters must be decimal digit or sign characters.");
            }
            digits[i] = Integer.parseInt(String.valueOf(chars[i]));
            reversedDigits[reversedDigits.length - i - 1] = digits[i];
        }
    }

    /**
     * Method to add a GinormInt value passed in as an argument to this GinormInt.
     *
     * @param  otherInt other GinormInt to add to this GinormInt
     * @return GinormInt that's the sum of this GinormInt and the one passed in
     */
    public GinormInt plus(GinormInt otherInt) {
        if (isNegative && !otherInt.isNegative) {
            return otherInt.minus(this);
        } else if (!isNegative && otherInt.isNegative) {
            return minus(otherInt);
        }
        List<Integer> digits1 = new ArrayList<Integer>();
        List<Integer> digits2 = new ArrayList<Integer>();
        for (int i = 0; i < digits.length; i += 1) {
            digits1.add(digits[i]);
        }
        for (int i = 0; i < otherInt.digits.length; i += 1) {
            digits2.add(otherInt.digits[i]);
        }
        int longest = digits1.size() > digits2.size() ? digits1.size() : digits2.size();
        while (digits1.size() < longest) {
            digits1.add(0, 0);
        }
        while (digits2.size() < longest) {
            digits2.add(0, 0);
        }
        String result = "";
        int carry = 0;
        for (int i = digits1.size() - 1; i >= 0; i -= 1) {
            int sum = digits1.get(i) + digits2.get(i) + carry;
            if (sum > 9) {
                sum -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            result = String.valueOf(sum) + result;
            if (i == 0 && carry == 1) {
                result = "1" + result;
            }
        }
        if (isNegative) {
            result = "-" + result;
        }
        return new GinormInt(result);
    }

    /**
     * Method to subtract a GinormInt passed in as an argument to this GinormInt.
     *
     * @param  otherInt other GinormInt to subtract from this GinormInt
     * @return GinormInt that's the difference of this GinormInt and the one passed in
     */
    public GinormInt minus(GinormInt otherInt) {
        if (isNegative && !otherInt.isNegative) {
            otherInt.isNegative = true;
            return plus(otherInt);
        } else if (!isNegative && otherInt.isNegative) {
            otherInt.isNegative = false;
            return plus(otherInt);
        }
        String sign = "";
        GinormInt absInt = new GinormInt(this.toString().substring(this.isNegative ? 1 : 0));
        GinormInt absOtherInt = new GinormInt(otherInt.toString().substring(otherInt.isNegative ? 1 : 0));
        List<Integer> digits1 = new ArrayList<Integer>();
        List<Integer> digits2 = new ArrayList<Integer>();
        String result = "";
        if (absInt.compareTo(absOtherInt) > 0) {
            if (this.isNegative) {
                sign = "-";
            }
            for (int i = 0; i < digits.length; i += 1) {
                digits1.add(digits[i]);
            }
            for (int i = 0; i < otherInt.digits.length; i += 1) {
                digits2.add(otherInt.digits[i]);
            }
        }
        if (absInt.compareTo(absOtherInt) < 0) {
            if (!otherInt.isNegative) {
                sign = "-";
            }
            for (int i = 0; i < digits.length; i += 1) {
                digits2.add(digits[i]);
            }
            for (int i = 0; i < otherInt.digits.length; i += 1) {
                digits1.add(otherInt.digits[i]);
            }
        }
        int longest = digits1.size() > digits2.size() ? digits1.size() : digits2.size();
        while (digits1.size() < longest) {
            digits1.add(0, 0);
        }
        while (digits2.size() < longest) {
            digits2.add(0, 0);
        }
        int borrow = 0;
        for (int i = digits1.size() - 1; i >= 0; i -= 1) {
            if (digits1.get(i) < digits2.get(i)) {
                digits1.set(i, digits1.get(i) + 10);
                if (i != 0) {
                    digits1.set(i - 1, digits1.get(i - 1) - 1);
                }
                borrow = 1;
            } else {
                borrow = 0;
            }
            if (i == 0 && borrow == 1) {
                result = String.valueOf(digits1.get(i) - digits2.get(i) - 1) + result;
            } else {
                result = String.valueOf(digits1.get(i) - digits2.get(i)) + result;
            }
        }
        return new GinormInt(sign + result);
    }

    /**
     * Method to multiply a GinormInt passed in as an argument to this GinormInt.
     *
     * @param  otherInt other GinormInt to multiply by this GinormInt
     * @return GinormInt that's the product of this GinormInt and the one passed in
     */
    public GinormInt times(GinormInt otherInt) {
        // (Optional) your code here
        throw new UnsupportedOperationException("Sorry, that operation is not yet implemented.");
    }

    /**
     * Method to divide a GinormInt passed in as an argument from this GinormInt.
     *
     * @param  otherInt other GinormInt to divide into this GinormInt
     * @return GinormInt that's the (truncated integer) ratio of this GinormInt and the one passed in
     */
    public GinormInt div(GinormInt otherInt) {
        // (Optional) your code here
        throw new UnsupportedOperationException("Sorry, that operation is not yet implemented.");
    }

    /**
     * Method to find the remainder after dividing by a GinormInt passed in.
     *
     * @param  otherInt other GinormInt to divide into this GinormInt to compute the remainder
     * @return GinormInt that's the remainder after dividing the two BigInts
     */
    public GinormInt mod(GinormInt otherInt) {
        // (Optional) your code here
        throw new UnsupportedOperationException("Sorry, that operation is not yet implemented.");
    }

    /**
     * Method to compare this GinormInt to another GinormInt passed in.
     *
     * @param  otherInt other GinormInt to compare to
     * @return 1 if this GinormInt is larger, 0 if equal, -1 if it's smaller
     */
    @Override
    public int compareTo(GinormInt otherInt) {
        int negation = 1;
        if (isNegative && otherInt.isNegative) {
            negation *= -1;
        } else if (isNegative && !otherInt.isNegative) {
            return -1;
        } else if (!isNegative && otherInt.isNegative) {
            return 1;
        }
        if (digits.length > otherInt.digits.length) {
            return 1 * negation;
        } else if (digits.length < otherInt.digits.length) {
            return -1 * negation;
        }
        for (int i = 0; i < digits.length; i += 1) {
            if (digits[i] > otherInt.digits[i]) {
                return 1 * negation;
            } else if (digits[i] < otherInt.digits[i]) {
                return -1 * negation;
            }
        }
        return 0;
    }

    /**
     * Method to check if this GinormInt equals another GinormInt passed in.
     *
     * @param  otherInt other GinormInt to compare to
     * @return true if they're equal, false otherwise
     */
    public boolean equals(GinormInt otherInt) {
        if (isNegative != otherInt.isNegative) {
            return false;
        }
        for (int i = 0; i < digits.length; i += 1) {
            if (digits[i] != otherInt.digits[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to return the string representation of this GinormInt.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        String str = "";
        if (isNegative) {
            str += "-";
        }
        for (int i = 0; i < digits.length; i += 1) {
            str += String.valueOf(digits[i]);
        }
        return str;
    }
}
