/**
 * A command-line game for rolling dice.
 */
public class HighRollerGame {
    public static void main(String[] args) {
        var console = System.console();
        var playing = true;
        var highest = 0;
        DiceSet dice = new DiceSet(4, 2);
        System.out.println("Welcome to the High Roller Game! " + Die.SIX_SIDED_DIE_EMOJI);
        while (playing) {
            try {
                var userInput = console.readLine("\nEnter a command (h for help): ");
                String[] arguments = userInput.split(" ");
                switch (arguments[0].substring(0, 1)) {
                    case "h":
                        if (arguments[0].equals("high") || arguments[0].equals("highest")) {
                            if (dice.getTotal() > highest) {
                                highest = dice.getTotal();
                            }
                            System.out.println("Highest score so far is " + highest);
                        } else {
                            System.out.println("h or help       : Prints this message");
                            System.out.println("q or quit       : Quits the program");
                            System.out.println("use <s> <n>     : Get a new dice set with n dice of s sides each");
                            System.out.println("roll all        : Roll all the dice in your current dice set");
                            System.out.println("roll <i>        : Roll the ith die of your current dice set");
                            System.out.println("high or highest : Prints the highest roll so far");
                        }                        
                        break;
                    case "q":
                        System.out.println("Thanks for Playing!" + Die.SIX_SIDED_DIE_EMOJI);
                        playing = false;
                        break;
                    case "u":
                        dice = new DiceSet(Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
                        dice.rollAll();
                        System.out.println("Now using a " + dice.getDescriptor() + " " + Die.SIX_SIDED_DIE_EMOJI);
                        System.out.println(dice.toString());
                        break;
                    case "r":
                        if (arguments[1].equals("all")) {
                            dice.rollAll();
                        } else {
                            if (Integer.parseInt(arguments[1]) < 1
                                    || Integer.parseInt(arguments[1]) > dice.getCurrentValues().size()) {
                                throw new IllegalArgumentException("Rolled die not in range!");
                            }
                            dice.rollDie(Integer.parseInt(arguments[1]) - 1);
                        }
                        System.out.println(dice.toString());
                        break;
                    default:
                        System.out.println("I don't understand!");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
