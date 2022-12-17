package nl.levy.COCPlugin.COCItems;
public class COCLocation {
    public int x;
    public int z;
    public int y;

    public COCLocation(int x, int z) {
        this.x = x;
        this.z = z;
    }
    public COCLocation(int x, int z, int y) {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(x: " + x + " z: " + z + " y: " + y + ")";
    }
}
