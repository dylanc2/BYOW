package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {
    private TETile[][] world;
    private Position current;
    private TETile oldTile;
    private static final TETile[] INACCESSIBLE_TERRAIN = {Tileset.LOCKED_DOOR, Tileset.WALL};

    public Avatar(TETile[][] world, Position start) {
        this.world = world;
        current = start;
        init();
    }

    private void init() {
        oldTile = world[current.getX()][current.getY()];
        world[current.getX()][current.getY()] = Tileset.AVATAR;
    }

    private boolean isInaccessible(TETile terrain) {
        for (TETile t : INACCESSIBLE_TERRAIN) {
            if (terrain.equals(t)) {
                return true;
            }
        }
        return false;
    }

    private void update(int dx, int dy) {
        world[current.getX()][current.getY()] = oldTile;
        current.setX(current.getX() + dx);
        current.setY(current.getY() + dy);
        oldTile = world[current.getX()][current.getY()];
        world[current.getX()][current.getY()] = Tileset.AVATAR;
    }

    public void moveUp() {
        if (isInaccessible(world[current.getX()][current.getY() + 1])) {
            return;
        }
        update(0, 1);
    }

    public void moveDown() {
        if (isInaccessible(world[current.getX()][current.getY() - 1])) {
            return;
        }
        update(0, -1);
    }

    public void moveLeft() {
        if (isInaccessible(world[current.getX() - 1][current.getY()])) {
            return;
        }
        update(-1, 0);
    }

    public void moveRight() {
        if (isInaccessible(world[current.getX() + 1][current.getY()])) {
            return;
        }
        update(1, 0);
    }

    public Position getCurrentPosition() {
        return current;
    }
}
