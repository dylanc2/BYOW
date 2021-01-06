package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

/**
 * In order to figure out rendering I used the following source.
 * @source https://www.codeproject.com/Articles/15604/Ray-casting-in-a-2D-tile-based-environment.
 * Also see: https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm.
 */
public class RayCaster {
    private static final TETile[] SOLID = {Tileset.WALL, Tileset.LOCKED_DOOR, Tileset.NOTHING};

    public static boolean isSolid(TETile in) {
        for (TETile t : SOLID) {
            if (t.equals(in)) {
                return true;
            }
        }
        return false;
    }

    public static List<Position> getAllRenderedPoints(TETile[][] world, int length, int startPos) {
        return null;
    }

    /**
     * @source https://www.codeproject.com/Articles/15604/Ray-casting-in-a-2D-tile-based-environment
     * @param p1
     * @param p2
     * @return
     */
    /*private static List<Position> Bresenham(Position p1, Position p2) {
        List<Position> toReturn = new ArrayList<>();
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        boolean tooSteep = isTooSteep(x1, y1, x2, y2);
        if (tooSteep) {
            int temp = x1;
            x1 = y1;
            y1 = temp;
            temp = x2;
            x2 = y2;
            y2 = temp;
        }
        if (x1 < x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int dX = x2 - x1;
        int dY = Math.abs(y2 - y1);
        int error = 0;
        int yDir = Math.abs(y2 - y1) / (y2 - y1);
        while (x1 != x2 + 1) {
            if (tooSteep) {
                toReturn.add(new Position(y1, x1));
            } else {
                toReturn.add(new Position(x1, y1));
            }
            error += dY;
            if () {
                y1 += yDir;
            }
            x1++;
        }
        return toReturn;
    }*/

    private static boolean isTooSteep(int x1, int y1, int x2, int y2) {
        if (Math.abs(y1 - y2) > Math.abs(x1 - x2)) {
            return true;
        }
        return false;
    }

    private static double calculateSlope(int dx, int dy) {
        if (dx == 0) {
            return 1000;
        }
        else {
            return Math.abs((double) dx / (double) dy);
        }
    }
}
