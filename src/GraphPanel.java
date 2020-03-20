import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GraphPanel extends JPanel {

    // Y axis parameters
    private static final int Y_AXIS_STARTING_X = 50;
    private static final int Y_AXIS_STARTING_Y = 50;
    private static final int Y_AXIS_LENGTH = 900;

    // X axis parameters
    private static final int X_AXIS_STARTING_X = Y_AXIS_STARTING_X;
    private static final int X_AXIS_STARTING_Y = Y_AXIS_STARTING_X + (Y_AXIS_LENGTH / 2);
    private static final int X_AXIS_LENGTH = 900;

    private static final int NUM_OF_RECTANGLES = 12;
    private static final int AXIS_PADDING = 20;
    private static final int RECTANGLE_HEIGHT_MULTIPLIER = 10;
    private static final int RECTANGLE_WIDTH = 11;

    private int[] rectanglesXCoordinates;
    private Integer[] temperatures;
    private boolean isFirstDisplay;
    private Integer previousYear;

    public GraphPanel() {
        Dimension size  = getPreferredSize();
        size.height     = 500;
        setPreferredSize(size);

        this.isFirstDisplay         = true;
        this.rectanglesXCoordinates = new int[NUM_OF_RECTANGLES];
        this.temperatures           = new Integer[NUM_OF_RECTANGLES];
        Arrays.fill(this.temperatures, 0);

        calculateRectanglesXCoordinates();
    }

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
        g.drawString(String.format("Showing data for: %s", this.previousYear),
                this.getWidth() / 2, AXIS_PADDING);

        // Draw y axis
        g.drawLine(Y_AXIS_STARTING_X, Y_AXIS_STARTING_Y,
                Y_AXIS_STARTING_X, Y_AXIS_STARTING_Y + Y_AXIS_LENGTH);

        // Draw x axis
        g.drawLine(X_AXIS_STARTING_X, X_AXIS_STARTING_Y,
                X_AXIS_STARTING_X + X_AXIS_LENGTH, X_AXIS_STARTING_Y);

        drawRectangles(g);
    }

    /**
     * Calculate the x coordinates at which the rectangles will be drawn.
     */
    private void calculateRectanglesXCoordinates() {
        int xCoordinateGapSize    = X_AXIS_LENGTH / rectanglesXCoordinates.length;
        rectanglesXCoordinates[0] = X_AXIS_STARTING_X + AXIS_PADDING;

        for (int i = 1; i < rectanglesXCoordinates.length; i++) {
            rectanglesXCoordinates[i] = rectanglesXCoordinates[i - 1] + xCoordinateGapSize;
        }
    }

    private void drawRectangles(Graphics g) {

        Integer maxTemperature = findMaxTemperature(temperatures);
        Integer minTemperature = findMinTemperature(temperatures);

        for (int i = 0; i < rectanglesXCoordinates.length; i++) {

            // Draw the indexes of the x axis
            g.drawString(String.valueOf(i + 1), rectanglesXCoordinates[i], X_AXIS_STARTING_Y + AXIS_PADDING);

            // Draw the indexes of the Y axis
            g.drawString(String.valueOf(temperatures[i]), Y_AXIS_STARTING_X - AXIS_PADDING,
                    X_AXIS_STARTING_Y + RECTANGLE_HEIGHT_MULTIPLIER * -temperatures[i]);

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
    }

    private int findMaxTemperature(Integer[] temperatures) {
        int currentMaxTemperature = temperatures[0];

        for (Integer temperature : temperatures) {

            if (temperature > currentMaxTemperature) {
                currentMaxTemperature = temperature;
            }
        }

        return currentMaxTemperature;
    }

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
