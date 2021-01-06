package byow.Core;

import java.util.ArrayList;
import java.util.List;

public class Hallway {
    private List<Position> walls;
    private List<Position> floors;
    public Hallway(Position one, Position two) {
        walls = new ArrayList<>();
        floors = new ArrayList<>();
        if (one.getX() < two.getX()) {
            one = pathHorizontally(one, two);
        } else {
            Position temp = one;
            one = pathHorizontally(two, one);
            two = temp;
        }
        if (one.getY() < two.getY()) {
            pathVertically(one, two);
        } else {
            pathVertically(two, one);
        }
    }

    public List<Position> getWalls() {
        return walls;
    }

    public List<Position> getFloors() {
        return floors;
    }

    private Position pathHorizontally(Position one, Position two) {
        Position toReturn = one;
        for (int i = one.getX(); i <= two.getX(); i++) {
            toReturn = new Position(i, one.getY());
            walls.add(new Position(i, one.getY() - 1));
            floors.add(toReturn);
            walls.add(new Position(i, one.getY() + 1));
        }
        walls.add(new Position(two.getX() + 1, one.getY() - 1));
        walls.add(new Position(two.getX() + 1, one.getY() + 1));
        return toReturn;
    }

    private void pathVertically(Position one, Position two) {
        for (int i = one.getY(); i <= two.getY(); i++) {
            walls.add(new Position(one.getX() - 1, i));
            floors.add(new Position(one.getX(), i));
            walls.add(new Position(one.getX() + 1, i));
        }
    }

}
