package byow.Core;

public class Position {
    private int x;
    private int y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x * 1000 + y;
    }

    @Override
    public boolean equals(Object in) {
        if (this == in) {
            return true;
        }
        if (in == null) {
            return false;
        }
        if (in.getClass() == this.getClass()) {
            if (((Position) in).getX() == this.x && ((Position) in).y == this.y) {
                return true;
            }
        }
        return false;
    }
}
