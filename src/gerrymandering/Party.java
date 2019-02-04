package gerrymandering;

import java.awt.*;

// Define political party node
public enum Party {
    Democrat("Democrat", "D", Color.BLUE),
    Republican("Republican", "R", Color.RED);

    public final String abbr;
    public final String name;
    public final Color color;

    // Construct branch node
    Party(String name, String abbr, Color color) {
        this.abbr = abbr;
        this.color = color;
        this.name = name;
    }
}