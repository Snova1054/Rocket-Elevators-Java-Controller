
//Button on a floor or basement to go back to lobby
public class FloorRequestButtons {
    public int ID;
    public String status;
    public int floor;
    public String direction;

    // Function used to create new NewFloorRequestButtons with the desired
    // properties
    public FloorRequestButtons(int _ID, int _floor, String _direction) {
        this.ID = _ID;
        this.status = "off";
        this.floor = _floor;
        this.direction = _direction;
    }
}