//Declares each Door
public class Door {
    public int ID = 1;
    public String status;

    // Function used to create new Doors with the desired properties
    public Door(int _ID, String _status) {
        this.ID = _ID;
        this.status = _status;
    }
}