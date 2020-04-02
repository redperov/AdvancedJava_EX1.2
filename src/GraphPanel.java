import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Represents a panel which contains a temperatures graph.
 */
public class GraphPanel extends JPanel {

    // Y axis parameters
    private static final int Y_AXIS_STARTING_X = 50;
    private static final int Y_AXIS_STARTING_Y = 50;
    private static final int Y_AXIS_LENGTH = 900;
    private static final int TEMPERATURE_GAP_SIZE = 5;
    private static final int TEMPERATURE_RANGE = 45;

    // X axis parameters
    private static final int X_AXIS_STARTING_X = Y_AXIS_STARTING_X;
    private static final int X_AXIS_STARTING_Y = Y_AXIS_STARTING_X + (Y_AXIS_LENGTH / 2);
    private static final int X_AXIS_LENGTH = 900;

    // General parameters
    private static final int NUM_OF_RECTANGLES = 12;
    private static final int AXIS_PADDING = 20;
    private static final int RECTANGLE_HEIGHT_MULTIPLIER = 10;
    private static final int RECTANGLE_WIDTH = 11;
    private static final int DISPLAY_YEAR_FONT_SIZE = 18;

    // Data members
    private int[] rectanglesXCoordinates;
    private int[] temperaturesYCoordinates;
    private Integer[] temperatures;
    private boolean isFirstDisplay;
    private Integer previousYear;

    /**
     * Constructor.
     */
    public GraphPanel() {
        Dimension size  = getPreferredSize();
        size.height     = 500;
        setPreferredSize(size);

        this.isFirstDisplay         = true;
        this.temperaturesYCoordinates = new int[(TEMPERATURE_RANGE / TEMPERATURE_GAP_SIZE) * 2 + 1];
        this.rectanglesXCoordinates = new int[NUM_OF_RECTANGLES];
        this.temperatures           = new Integer[NUM_OF_RECTANGLES];
        Arrays.fill(this.temperatures, 0);

        calculateTemperatureYCoordinates();
        calculateRectanglesXCoordinates();
    }

    /**
     * Plots the graph.
     * @param temperatures temperatures values
     * @param year corresponding year for the temperatures
     */
    public void plotTemperatures(Integer[] temperatures, Integer year) {
        this.temperatures   = temperatures;
        this.isFirstDisplay = false;

        // Check if there is a point in repainting the panel
        if (!year.equals(this.previousYear)) {
            this.previousYear = year;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw current year
        String displayYear = "No year chosen";

        if (this.previousYear != null) {
            displayYear = this.previousYear.toString();
        }
        Font previousFont = g.getFont();
        g.setFont(new Font(previousFont.getFontName(), Font.PLAIN, DISPLAY_YEAR_FONT_SIZE));
        g.drawString(String.format("Showing data for: %s", displayYear),
                this.getWidth() / 2, AXIS_PADDING);
        g.setFont(previousFont);

        // Draw y axis
        g.drawString("Temperature", Y_AXIS_STARTING_X - AXIS_PADDING, Y_AXIS_STARTING_Y - AXIS_PADDING);
        g.drawLine(Y_AXIS_STARTING_X, Y_AXIS_STARTING_Y - RECTANGLE_HEIGHT_MULTIPLIER,
                Y_AXIS_STARTING_X, Y_AXIS_STARTING_Y + Y_AXIS_LENGTH);

        // Draw x axis
        g.drawString("Month", X_AXIS_STARTING_X + X_AXIS_LENGTH + AXIS_PADDING, X_AXIS_STARTING_Y);
        g.drawLine(X_AXIS_STARTING_X, X_AXIS_STARTING_Y,
                X_AXIS_STARTING_X + X_AXIS_LENGTH, X_AXIS_STARTING_Y);

        drawRectangles(g);
    }

    /**
     * Calculate the y coordinates at which the temperature values will be drawn.
     */
    private void calculateTemperatureYCoordinates() {
        this.temperaturesYCoordinates[0] = TEMPERATURE_RANGE;

        for (int i = 1; i < this.temperaturesYCoordinates.length; i++) {
            this.temperaturesYCoordinates[i] = this.temperaturesYCoordinates[i - 1] - TEMPERATURE_GAP_SIZE;
        }
    }

    /**
     * Calculate the x coordinates at which the rectangles will be drawn.
     */
    private void calculateRectanglesXCoordinates() {
        int xCoordinateGapSize    = X_AXIS_LENGTH / rectanglesXCoordinates.length;
        this.rectanglesXCoordinates[0] = X_AXIS_STARTING_X + AXIS_PADDING;

        for (int i = 1; i < this.rectanglesXCoordinates.length; i++) {
            this.rectanglesXCoordinates[i] = this.rectanglesXCoordinates[i - 1] + xCoordinateGapSize;
        }
    }

    /**
     * Draws the temperatures rectangles in the graph.
     * @param g graphics
     */
    private void drawRectangles(Graphics g) {

        Integer maxTemperature = findMaxTemperature(temperatures);
        Integer minTemperature = findMinTemperature(temperatures);

        for (int i = 0; i < rectanglesXCoordinates.length; i++) {

            // Draw the indexes of the x axis
            g.drawString(String.valueOf(i + 1), rectanglesXCoordinates[i], X_AXIS_STARTING_Y + AXIS_PADDING);

            if (temperatures[i].equals(maxTemperature) && !this.isFirstDisplay) {
                g.setColor(Color.RED);
            }
            else if (temperatures[i].equals(minTemperature) && !this.isFirstDisplay) {
                g.setColor(Color.BLUE);
            }
            else if (!this.isFirstDisplay) {
                g.setColor(Color.GRAY);
            }

            g.fillRect(rectanglesXCoordinates[i] + 15, X_AXIS_STARTING_Y,
                    RECTANGLE_WIDTH, RECTANGLE_HEIGHT_MULTIPLIER * -temperatures[i]);

            // Return to the original color
            g.setColor(Color.BLACK);
        }

        for (int temperaturesYCoordinate : this.temperaturesYCoordinates) {
            // Draw the indexes of the Y axis
            g.drawString(String.valueOf(temperaturesYCoordinate), Y_AXIS_STARTING_X - AXIS_PADDING,
                    X_AXIS_STARTING_Y - temperaturesYCoordinate * RECTANGLE_HEIGHT_MULTIPLIER);
        }
    }

    /**
     * Finds the max temperature.
     * @param temperatures temperatures to search max value at
     * @return max temperature
     */
    private int findMaxTemperature(Integer[] temperatures) {
        int currentMaxTemperature = temperatures[0];

        for (Integer temperature : temperatures) {

            if (temperature > currentMaxTemperature) {
                currentMaxTemperature = temperature;
            }
        }

        return currentMaxTemperature;
    }

    /**
     * Finds the min temperature.
     * @param temperatures temperatures to search min value at
     * @return min temperature
     */
    private int findMinTemperature(Integer[] temperatures) {
        int currentMinTemperature = temperatures[0];

        for (Integer temperature : temperatures) {

            if (temperature < currentMinTemperature) {
                currentMinTemperature = temperature;
            }
        }

        return currentMinTemperature;
    }
}
