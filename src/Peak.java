public class Peak {
    
    private int x;
    private int y;
    private int elevation;

    public Peak(int x, int y, int elevation) {
        this.x = x;
        this.y = y;
        this.elevation = elevation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getElevation() {
        return elevation;
    }

    @Override
    public String toString() {
        return "[" + y + "," + x + " elevation = " + elevation + "]";
    }
    
}
