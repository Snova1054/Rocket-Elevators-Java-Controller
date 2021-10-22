
//Button on a floor or basement to go back to lobby
public class CallButton {
    public int ID;
    public String status;
    public int floor;
    public String direction;

    public CallButton(int _id, int _floor, String _direction) {
        this.ID = _id;
        this.status = "off";
        this.floor = _floor;
        this.direction = _direction;
    }
}