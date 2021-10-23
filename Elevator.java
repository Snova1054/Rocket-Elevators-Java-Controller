import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Declares each Elevator
public class Elevator {
    public String ID;
    public String status;
    public int currentFloor;
    public String direction;
    public Door door;
    public List<Integer> floorRequestsList;
    public List<Integer> completedRequestsList;

    // Function used to create new Elevators with the desired properties
    public Elevator(char _elevatorID) {
        this.ID = Character.toString(_elevatorID);
        this.status = "idle";
        this.currentFloor = 1;
        this.direction = "null";
        this.door = new Door(1, "off");
        this.floorRequestsList = new ArrayList<Integer>();
        this.completedRequestsList = new ArrayList<Integer>();

    }

    // Method used by the Column or the Battery to move the Elevator
    public void move() {

        while (floorRequestsList.size() != 0) {
            int destination = floorRequestsList.get(0);
            status = "moving";
            if (currentFloor < destination) {
                direction = "up";
                sortFloorList();
                while (currentFloor < destination) {
                    String str1 = String.format("Elevator %s is on the floor #%d", ID, currentFloor);
                    System.out.println(str1);            
                    currentFloor++;
                }
            } else if (currentFloor > destination) {
                direction = "down";
                sortFloorList();
                while (currentFloor > destination) {
                    String str1 = String.format("Elevator %s is on the floor #%d", ID, currentFloor);
                    System.out.println(str1);            
                    currentFloor--;
                }
            }
            String str1 = String.format("Elevator %s is on the floor #%d\n", ID, currentFloor);
            System.out.println(str1);
            status = "stopped";
            operateDoors();
            floorRequestsList.remove(0);
        }
        status = "idle";
    }

    // Method used to sort the floor requests list
    public void sortFloorList() {
        Collections.sort(floorRequestsList);
        if (direction == "down") {
            Collections.sort(floorRequestsList, Collections.reverseOrder());
        }
    }

    // Method used by the Elevator to operate its Doors
    public void operateDoors() {
        door.status = "opened";
        System.out.println("Elevator's doors have opened");
        // Wait 5 secondes
        door.status = "closed";
        System.out.println("Elevator's doors have closed\n");
    }

    // Method used to add new floor requests
    public void addNewRequest(int _requestedFloor) {
        completedRequestsList.add(_requestedFloor);
        if (!floorRequestsList.contains(_requestedFloor)) {
            floorRequestsList.add(_requestedFloor);
        }

        if (currentFloor < _requestedFloor) {
            direction = "up";
        } else if (currentFloor > _requestedFloor) {
            direction = "down";
        }
    }
}