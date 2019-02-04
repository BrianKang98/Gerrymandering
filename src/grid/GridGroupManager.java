package grid;

import java.util.HashSet;
import java.util.Set;

// Wrapper
public class GridGroupManager {
    private HashSet<GridGroup> groups = new HashSet<>();

    protected boolean add(GridGroup set) {
        return groups.add(set);
    }

    public int size() {
        return groups.size();
    }

    public void remove(GridGroup group) {
        this.groups.remove(group);
    }

    public Set getGroups() {
        return groups;
    }

    public GridGroup groupForObject(GridObject obj) {
        for (GridGroup group : this.groups) {
            if (group.contains(obj.getGridLocation())) {
                return group;
            }
        }
        return null;
    }
}
