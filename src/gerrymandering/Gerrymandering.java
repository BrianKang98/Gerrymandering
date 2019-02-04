/*
 Brian Kang
 MATH 381
 Project 1: Gerrymandering
 Original from GitHub
 */

package gerrymandering;

import grid.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

// Interactive demonstration of Gerrymandering
/*
 3/5 Axioms of Political Districting Criteria are defined in code
 1. Integrity
 2. Contiguity
 3. No Enclaves

 2/5 Criteria not necessarily met. Assume satisfied
 4. Compactness
 5. Population Equality

 "Region" = precinct
 "Group" = district
 "Map" = State
 */
public class Gerrymandering extends JFrame implements GridDelegate {
    private PoliticalMap map;
    private Grid<Region> grid;

    // Very easy to alter the edge lengths
    // nRow not need equal to be nColumn
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        System.out.println("Define the edge length of the State: ");
        System.out.println("Recommend: 5 or 10");
        System.out.println("(Note: District size is 5)");
        int n = kb.nextInt();
        Gerrymandering gerrymandering = new Gerrymandering(n, n);
    }

    public Gerrymandering(int height, int width) {
        super();
        setTitle("Gerrymandering Demonstration");
        map = new PoliticalMap(height, width);
        grid = new Grid(height, width);
        // Can change district size
        // If change,
        //  make new method to check that (nRow*nCol)%GroupSize==0 && ((nRow*nCol)/GroupSize)%2!=0,
        //  if not, throw new IllegalArgumentException();
        //  Set constraints to GroupSize if desired.
        grid.setGroupSize(5);
        map.setUpGrid(grid);
        // Can change pop-up tab size
        setSize(new Dimension(500, 500));
        grid.setDelegate(this);
        grid.setSize(getSize());
        getContentPane().add(grid);

        // Can change pop-up tab size
        setSize(new Dimension(600, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private EnumMap<Party, Integer> tallyAllVotes() {
        Set<GridGroup> groups = grid.getGroups();
        EnumMap<Party, Integer> groupTally = new EnumMap<>(Party.class);
        for (Party party : Party.values()) {
            groupTally.put(party, 0);
        }
        for (GridGroup group : groups) {
            Party regionVote = majorityParty(group);
            groupTally.put(regionVote, groupTally.get(regionVote) + 1);
        }
        return groupTally;
    }

    private EnumMap<Party, Integer> districtTally(GridGroup group) {
        EnumMap<Party, Integer> groupTally = new EnumMap<>(Party.class);
        for (Party party : Party.values()) {
            groupTally.put(party, 0);
        }
        for (Location l : group.contents) {
            Region r = grid.get(l);
            groupTally.put(r.party, 1 + groupTally.get(r.party));
        }
        return groupTally;
    }

    public void groupCreated(GridGroup group) {
        Color groupColor = colorForGroup(group);
        for (Location location : group) {
            GridObject object = grid.get(location);
            object.setBackground(groupColor);
        }
        if (gameIsOver()) {
            Map.Entry<Party, Integer> winner = determineWinner();
            String endGameMessage = "The " + winner.getKey().name() + " party wins this state.";
            JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), endGameMessage);
        }
    }

    private Party majorityParty(GridGroup group) {
        EnumMap<Party, Integer> groupTally = districtTally(group);
        Map.Entry<Party, Integer> maxEntry = null;
        for (Map.Entry<Party, Integer> entry : groupTally.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    private boolean gameIsOver() {
        return grid.getGroups().size() >= (grid.width * grid.height) / grid.getGroupSize();
    }

    private Map.Entry<Party, Integer> determineWinner() {
        EnumMap<Party, Integer> results = tallyAllVotes();
        Map.Entry<Party, Integer> maxEntry = null;
        for (Map.Entry<Party, Integer> entry : results.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    private Color colorForGroup(GridGroup group) {
        EnumMap<Party, Integer> tally = districtTally(group);
        Party majorityParty = majorityParty(group);
        float strength = (float) tally.get(majorityParty) / grid.getGroupSize();
        Color partyColor = majorityParty.color;
        int newRed = (int) ((float) partyColor.getRed() * strength);
        int newGreen = (int) ((float) partyColor.getGreen() * strength);
        int newBlue = (int) ((float) partyColor.getBlue() * strength);
        return new Color(255 - (newBlue + newGreen) / 2, 255 - (newBlue + newRed) / 2, 255 - (newGreen + newRed) / 2);
    }
}
