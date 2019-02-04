package gerrymandering;

import grid.Grid;

// Define grid that represents a State with finalized precincts' majority party
public class PoliticalMap {
    private Region[][] map;
    private int height;
    private int width;

    PoliticalMap(int height, int width) {
        this.width = width;
        this.height = height;
        map = new Region[height][width];
        populateMap();
    }

    /* Fill grid with random political regions */
    // Make a NxN state
    private void populateMap() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                map[r][c] = new Region();
            }
        }
    }

    // Set up grid of state
    public void setUpGrid(Grid<Region> grid) {
        for (int r = 0; r < grid.height; r++) {
            for (int c = 0; c < grid.width; c++) {
                grid.put(map[r][c], c, r);
            }
        }
    }
}
