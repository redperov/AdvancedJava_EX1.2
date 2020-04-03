import javax.swing.*;

/**
 * Represents the GUI of the weather app.
 */
public class WeatherApp {
    private WeatherFrame frame;

    public WeatherApp() {
        this.frame = new WeatherFrame("Weather statistics");
        this.frame.setSize(1100,1100);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    /**
     * Receives input from the user and plots it on the graph.
     */
    public void start() {
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

            this.frame.plotData(temperatures, chosenYear);
        }
    }
}
