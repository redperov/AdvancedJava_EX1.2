import javax.swing.*;
import java.awt.*;

/**
 * An interactive frame which plots average yearly temperatures on a graph according to the users request.
 */
public class WeatherFrame extends JFrame {
    
    private GraphPanel graphPanel;

    /**
     * Constructor.
     * @param title frame's title
     */
    public WeatherFrame(String title) {
        super(title);

        // Set layout
        setLayout(new BorderLayout());

        // Components
        this.graphPanel = new GraphPanel();

        // Add components to content pane
        Container container = getContentPane();
        container.add(graphPanel, BorderLayout.CENTER);
    }

    /**
     * Plots average temperatures on a graph.
     * @param temperatures temperatures to plot
     * @param year corresponding year to the plotted temperatures
     */
    public void plotData(Integer[] temperatures, Integer year) {
        this.graphPanel.plotTemperatures(temperatures, year);
    }
}
