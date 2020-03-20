import javax.swing.*;
import java.awt.*;

public class WeatherFrame extends JFrame {
    
    private GraphPanel graphPanel;

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

    public GraphPanel getGraphPanel() {
        return this.graphPanel;
    }

    public void plotData(Integer[] temperatures, Integer year) {
        this.graphPanel.plotTemperatures(temperatures, year);
    }
}
