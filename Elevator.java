import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elevator
    {
        public String ID;
        public String status;
        public int currentFloor;
        public String direction;
        public Door door;
        public List<Integer> floorRequestsList;
        public List<Integer> completedRequestsList;

        public Elevator(char _elevatorID)
        {
            this.ID = Character.toString(_elevatorID);
            this.status = "idle";
            this.currentFloor = 1;
            this.direction = "null";
            this.door = new Door(1, "off");
            this.floorRequestsList = new ArrayList<Integer>();
            this.completedRequestsList = new ArrayList<Integer>();
            
        }
        public void move()
        {

            while (floorRequestsList.size() != 0)
            {
                int destination = floorRequestsList.get(0);
                status = "moving";
                if (currentFloor < destination)
                {
                    direction = "up";
                    sortFloorList();
                    while (currentFloor < destination)
                    {
                         currentFloor++;
                    }
                }
                else if (currentFloor > destination)
                {
                    direction = "down";
                    sortFloorList();
                    while (currentFloor > destination)
                    {
                        currentFloor--;
                    }
                }
                status = "stopped";
                operateDoors();
                floorRequestsList.remove(0); 
            }
            status = "idle";
        }

        public void sortFloorList()
        {
            Collections.sort(floorRequestsList); // NEED TESTING NOW
            if (direction == "down")
            {
                Collections.sort(floorRequestsList, Collections.reverseOrder());
            }
        }

        public void operateDoors()
        {
            door.status = "opened";
            //Wait 5 secondes
            door.status = "closed";
        }

        public void addNewRequest(int _requestedFloor)
        {
            completedRequestsList.add(_requestedFloor);
            if (!floorRequestsList.contains(_requestedFloor))
            {
                floorRequestsList.add(_requestedFloor);
            }

            if (currentFloor < _requestedFloor)
            {
                direction = "up";
            }
            else if (currentFloor > _requestedFloor)
            {
                direction = "down";
            }   
        }        
    }