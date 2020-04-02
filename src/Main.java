import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        WeatherFrame frame = new WeatherFrame("Weather statistics");
        frame.setSize(1100,1100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        receiveUserInput(frame);
    }

    /**
     * Receives input from the user and plots it on the graph.
     * @param frame holds the plotted graph
     */
    private static void receiveUserInput(WeatherFrame frame) {
        String userInput;
        int previouslyChosenYear = Integer.MIN_VALUE;
        int chosenYear;
        Integer[] temperatures;

        while (true) {
            userInput = JOptionPane.showInputDialog("Choose a year:");

            // If the user entered an empty string, stop asking for input
            if ( userInput == null || userInput.equals("")) {
                break;
            }

            try {
                chosenYear = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Illegal value.");
                continue;
            }

            // Avoid refreshing the graph if the chosen year is the same as the previously chosen
            if (chosenYear == previouslyChosenYear) {
                continue;
            }
            temperatures = YearlyTemperature.getTemperature(chosenYear);

            // Check if records were found for the given year
            if (temperatures == null) {
                JOptionPane.showMessageDialog(null,
                        "No records for the given year.");
                continue;
            }

            frame.plotData(temperatures, chosenYear);
        }
    }
}
