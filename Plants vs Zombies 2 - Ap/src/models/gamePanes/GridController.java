package models.gamePanes;

import java.util.ArrayList;
import java.util.List;
import models.entity.*;

public class GridController {

    private final List<GridItem> gridItems = new ArrayList<>();

    public void addGridItem(GridItem item) {
        gridItems.add(item);
    }

    public void removeGridItem(GridItem item) {
        gridItems.remove(item);
    }

    public GridItem getGridItem(int row, int col) {
        for (GridItem item : gridItems) {
            if (item.getRow() == row && item.getCol() == col) {
                return item;
            }
        }
        return null;
    }

    public GridItem getGridItemInFront(Zombie zombie, String type) {
        int row = zombie.getRow();
        int col = (int) ((zombie.getX() - 100) / 80) + 1;
        if (col >= 9) return null;

        GridItem item = getGridItem(row, col);
        if (item != null && item.getType().equals(type) && item.isPushable()) {
            return item;
        }
        return null;
    }

    public List<GridItem> getGridItemsByType(String type) {
        List<GridItem> result = new ArrayList<>();
        for (GridItem item : gridItems) {
            if (item.getType().equals(type)) {
                result.add(item);
            }
        }
        return result;
    }

    public void pushItem(GridItem item, int newCol) {
        if (newCol >= 9) {
            removeGridItem(item);
            return;
        }
        item.setCol(newCol);
    }

    public void clear() {
        gridItems.clear();
    }
}