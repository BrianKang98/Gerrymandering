package gerrymandering;

import grid.GridObject;

import javax.swing.*;
import java.awt.*;

// Define precinct and grid visual components
public class Region extends GridObject {
    public static final Color ACTIVE_COLOR = new Color(220, 220, 220);
    protected Party party;
    private int population;
    private JLabel label;

    public Region() {
        party = Party.values()[(int) (Math.random() * Party.values().length)];
        population = (int) (Math.random() * 10);
        setBorderPainted(true);
        setOpaque(false);
        setLayout(new GridBagLayout());
        setFocusable(false);
        setContentAreaFilled(false);
        label = new JLabel(this.party.abbr);
        label.setFont(new Font("Comic Sans", Font.BOLD, 20));
        label.setForeground(Color.BLUE);
        add(label);
        if (party == Party.Republican) {
            label.setForeground(Color.RED);
        }
    }

    public Region(Party party, int population) {
        this.party = party;
        this.population = population;
    }

    public void paint(Graphics g) {
        if (isInGroup()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (getModel().isPressed()) {
            g.setColor(ACTIVE_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (active()) {
            g.setColor(ACTIVE_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paint(g);
    }

    public boolean isGroupable(GridObject object) {
        if (object instanceof Region) {
            Region region = (Region) object;
            return true;
        }
        return false;
    }
}

