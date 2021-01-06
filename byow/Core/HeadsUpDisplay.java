package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

public class HeadsUpDisplay {
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private String tileName;
    private boolean hasName;
    private String name;

    public HeadsUpDisplay(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        hasName = false;
        tileName = "";
    }

    public void update(String newTileName) {
        tileName = newTileName;
    }

    public void drawTileName() {
        StdDraw.setPenColor(StdDraw.RED);
        writeTileName();
        StdDraw.show();
    }

    public void drawName() {
        StdDraw.setPenColor(StdDraw.RED);
        if (hasName) {
            writeAvatarName();
        }
        StdDraw.show();
    }

    public void setName(String in) {
        hasName = true;
        name = in;
    }

    private void writeAvatarName() {
        StdDraw.text(xMax - 15, yMin + 1, name);
    }

    private void writeTileName() {
        StdDraw.text(xMin + 5, yMin + 1, tileName);
    }
}
