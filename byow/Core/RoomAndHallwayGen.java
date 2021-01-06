package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomAndHallwayGen {
    private static final double PARTITION_TO_ROOM_CHANCE = 0.66;
    private final int WIDTH;
    private final int HEIGHT;
    private final int ITERATIONS;
    private Random random;
    private TETile[][] world;
    private List<Node> listPartitions;
    private List<Room> rooms;
    private List<Position> stationaryLights;
    public RoomAndHallwayGen(long seed, int width, int height) {
        random = new Random(seed);
        WIDTH = width;
        HEIGHT = height - 1;
        world = new TETile[WIDTH][HEIGHT];
        ITERATIONS = Math.min(WIDTH, HEIGHT) / 5 + 1;
        listPartitions = new ArrayList<>((int) Math.pow(2, ITERATIONS));
        rooms = new ArrayList<>();
        init();
    }

    public List<Position> getStationaryLights() {
        return stationaryLights;
    }

    private class Node {
        private int minX;
        private int maxX;
        private int minY;
        private int maxY;
        private int level;
        private Node left;
        private Node right;
        Node(int level) {
            minX = 0;
            maxX = WIDTH - 1;
            minY = 0;
            maxY = HEIGHT - 1;
            this.level = level;
            left = null;
            right = null;
        }

        private int getMinLine() {
            if (level % 2 == 0) { //horz line -> vert coords
                return minY;
            } else {
                return minX;
            }
        }

        private int getMaxLine() {
            if (level % 2 == 0) { //horz line -> vert coords
                return maxY;
            } else {
                return maxX;
            }
        }

        private void copy(Node parent) {
            this.minX = parent.minX;
            this.maxX = parent.maxX;
            this.minY = parent.minY;
            this.maxY = parent.maxY;
        }

        private void cut(int line) {
            left = new Node(level + 1);
            right = new Node(level + 1);
            left.copy(this);
            right.copy(this);
            if (level % 2 == 0) { //horz line cut
                left.maxY = line;
                right.minY = Math.min(line + 1, right.maxY);
            } else { //vert line cut
                left.maxX = line;
                right.minX = Math.min(line + 1, right.maxX);
            }
        }

        private boolean canGenerateChild() {
            if (minX + 4 > maxX || minY + 4 > maxY) { //needs to have padding for walls
                return false;
            }
            return true;
        }

        private Node getNode() {
            return this;
        }

        private int getWidth() {
            return maxX - minX + 1;
        }

        private int getHeight() {
            return maxY - minY + 1;
        }
    }

    private void init() {
        stationaryLights = new ArrayList<>();
        initEmptyWorld();
        makePartitions();
        makeRooms();
        makeHallways();
        makeLockedDoor();
    }

    private void makePartitions() {
        Node root = new Node(0);
        helpMakePartitions(root, ITERATIONS);
    }

    private void helpMakePartitions(Node parent, int remIters) {
        if (remIters <= 0) {
            listPartitions.add(parent);
            return;
        }
        //int cutLine = RandomUtils.uniform(random, parent.getMinLine(), parent.getMaxLine() + 1);
        int cutLine = getCut2(parent.getMinLine(), parent.getMaxLine());
        parent.cut(cutLine);
        helpMakePartitions(parent.left, remIters - 1);
        helpMakePartitions(parent.right, remIters - 1);
    }

    private int getCut2(int min, int max) {
        if (min + 3 >= max - 3) {
            return max;
        } else {
            return RandomUtils.uniform(random, min + 3, max - 3);
        }
    }

    private void makeRooms() {
        for (Node partition: listPartitions) {
            if (partition.canGenerateChild()) {
                addRoom(partition);
            }
        }
    }

    private void addRoom(Node partition) {
        if (RandomUtils.uniform(random) < PARTITION_TO_ROOM_CHANCE) {
            Position tempPos = new Position(partition.minX, partition.minY);
            Room temp = new Room(partition.getWidth(), partition.getHeight(), tempPos);
            fillWorld(temp);
            rooms.add(temp);
        }
    }

    private void fillWorld(Room room) {
        paintFloors(room.getAllFloors());
        paintHallwayWalls(room.getAllWalls());
    }

    private void makeHallways() {
        /*Iterator<Room> it = rooms.iterator();
        Room first = it.next();
        Room second;
        Hallway hw;
        while (it.hasNext()) {
            second = it.next();
            hw = new Hallway(first.getDeterministicFloor(), second.getDeterministicFloor());
            paintFloors(hw.getFloors());
            paintHallwayWalls(hw.getWalls());
            first = second;
        }*/
        if (rooms.size() == 0) {
            return;
        }
        Room first = rooms.get(0);
        Room second;
        Hallway hw;
        for (int i = 1; i < rooms.size(); i++) {
            second = rooms.get(i);
            hw = new Hallway(first.getDeterministicFloor(), second.getDeterministicFloor());
            paintFloors(hw.getFloors());
            paintHallwayWalls(hw.getWalls());
            first = second;
        }
    }

    private void makeLockedDoor() {
        Position tempFloor = getRandomRoom().getDeterministicFloor();
        while (world[tempFloor.getX()][tempFloor.getY()] != Tileset.WALL) {
            tempFloor = new Position(tempFloor.getX() - 1, tempFloor.getY());
        }
        stationaryLights.add(tempFloor);
        world[tempFloor.getX()][tempFloor.getY()] = Tileset.LOCKED_DOOR;
    }

    public Room getRandomRoom() {
        int index = RandomUtils.uniform(random, 0, rooms.size());
        return rooms.get(index);
    }

    private void paintHallwayWalls(List<Position> walls) {
        for (Position tile : walls) {
            if (world[tile.getX()][tile.getY()].equals(Tileset.NOTHING)) {
                world[tile.getX()][tile.getY()] = Tileset.WALL;
            }
        }
    }

    private void paintFloors(List<Position> floors) {
        for (Position floor: floors) {
            world[floor.getX()][floor.getY()] = Tileset.FLOOR;
        }
    }


    private void initEmptyWorld() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }

    public static void main(String[] args) {
        RoomAndHallwayGen test = new RoomAndHallwayGen(122, 20, 20);
        System.out.println("test");
    }
}
