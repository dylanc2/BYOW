package byow.Core;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Position> walls;
    private List<Position> floors;
    private final int WIDTH;
    private final int HEIGHT;

    /**
     * Guaranteed to have height of at least 4.
     * @param widthWithWalls
     * @param heightWithWalls
     * @param bottomLeft
     */
    public Room(int widthWithWalls, int heightWithWalls, Position bottomLeft) {
        WIDTH = widthWithWalls;
        HEIGHT = heightWithWalls;
        int x = bottomLeft.getX();
        int y = bottomLeft.getY();
        walls = new ArrayList<>();
        floors = new ArrayList<>();
        bottom(x, y);
        for (int i = 0; i < HEIGHT - 2; i++) {
            middle(x, y + 1 + i);
        }
        top(x, y + HEIGHT - 1);
    }

    private void bottom(int x, int y) {
        for (int i = x; i < WIDTH + x; i++) {
            walls.add(new Position(i, y));
        }
    }

    private void middle(int x, int y) {
        walls.add(new Position(x, y));
        for (int i = x + 1; i < WIDTH + x - 1; i++) {
            floors.add(new Position(i, y));
        }
        walls.add(new Position(x + WIDTH - 1, y));
    }

    private void top(int x, int y) {
        bottom(x, y);
    }

    public List<Position> getAllWalls() {
        return walls;
    }

    public List<Position> getAllFloors() {
        return floors;
    }

    public Position getDeterministicFloor() {
        return floors.get(floors.size() / 2);
    }
}
