import java.util.ArrayList;
import java.util.List;

//Declares ColumnElevator, an object used to store a Column and an Elevator
class ColumnElevator {
    Column column;
    Elevator elevator;
}

// Declares each Battery
public class Battery {
    public char columnID = 'A';
    public int floorRequestButtonID = 1;
    public int ID;
    public String status;
    public List<Column> columnsList;
    public List<FloorRequestButtons> floorRequestButtonsList;

    // Function used to create new Batteries with the desired properties
    public Battery(int _ID, int _amountOfColumns, int _amountOfFloors, int _amountOfBasements,
            int _amountOfElevatorPerColumn) {
        this.ID = _ID;
        this.status = "online";
        this.columnsList = new ArrayList<Column>();
        this.floorRequestButtonsList = new ArrayList<FloorRequestButtons>();

        if (_amountOfBasements > 0) {
            createBasementColumn(_amountOfBasements, _amountOfElevatorPerColumn);
            createBasementFloorRequestButtonss(_amountOfBasements);
            _amountOfColumns--;
        }

        createFloorRequestButtonss(_amountOfFloors);
        createColumns(_amountOfColumns, _amountOfFloors, _amountOfElevatorPerColumn);
    }

    // Method used by the Battery to create a basement Column
    public void createBasementColumn(int _amountOfBasement, int _amountOfElevatorPerColumn) {
        List<Integer> servedFloors = new ArrayList<Integer>();
        int floor = -1;
        for (int i = 0; i < _amountOfBasement; i++) {
            servedFloors.add(floor);
            floor--;
        }

        Column basementColumn = new Column(columnID, _amountOfElevatorPerColumn, servedFloors, true);
        columnsList.add(basementColumn);
        columnID++;
    }

    // Method used to create Columns
    public void createColumns(double _amountOfColumns, double _amountOfFloors, int _amountOfElevatorPerColumn) {
        double amountOfFloorsPerColumn = Math.ceil(_amountOfFloors / _amountOfColumns);
        int floor = 1;

        for (int i = 0; i < _amountOfColumns; i++) {
            List<Integer> servedFloors = new ArrayList<Integer>();

            for (int j = 0; j < amountOfFloorsPerColumn; j++) {
                if (floor <= _amountOfFloors) {
                    servedFloors.add(floor);
                    floor++;
                }
            }
            Column column = new Column(columnID, _amountOfElevatorPerColumn, servedFloors, false);
            columnsList.add(column);
            columnID++;
        }
    }

    // Method used to create FloorRequestButtons
    public void createFloorRequestButtonss(double _amountOfFloors) {
        int buttonFloor = 1;
        for (int i = 0; i < _amountOfFloors; i++) {
            FloorRequestButtons floorRequestButton = new FloorRequestButtons(floorRequestButtonID, buttonFloor, "up");
            floorRequestButtonsList.add(floorRequestButton);
            buttonFloor++;
            floorRequestButtonID++;
        }
    }

    // Method used to create basement FloorRequestButtons
    public void createBasementFloorRequestButtonss(int _amountOfBasements) {
        int buttonFloor = -1;
        for (int i = 0; i < _amountOfBasements; i++) {
            FloorRequestButtons floorRequestButton = new FloorRequestButtons(floorRequestButtonID, buttonFloor, "down");
            floorRequestButtonsList.add(floorRequestButton);
            buttonFloor--;
            floorRequestButtonID++;
        }
    }

    // Method used to assign the best Elevator according to the best Column from the
    // user's inputs
    public ColumnElevator assignElevator(int _requestedFloor, String _direction) {
        String str1 = String.format("An elevator has been requested from the lobby to the floor #%d\n", _requestedFloor);
        System.out.println(str1);
        ColumnElevator columnElevator = new ColumnElevator();
        columnElevator.column = findBestColumn(_requestedFloor);
        columnElevator.elevator = columnElevator.column.findElevator(1, _direction);
        String str2 = String.format("Elevator %s on the floor #%d has been selected as the best elevator\n", columnElevator.elevator.ID, columnElevator.elevator.currentFloor);
        System.out.println(str2);
        columnElevator.elevator.addNewRequest(1);
        columnElevator.elevator.move();

        columnElevator.elevator.addNewRequest(_requestedFloor);
        columnElevator.elevator.move();
        return columnElevator;
    }

    // Simulate when a user press a button at the lobby
    public Column findBestColumn(int _requestedFloor) {
        for (Column column : columnsList) {
            if (column.servedFloors.contains(_requestedFloor)) {
                return column;
            }
        }
        return null;
    }
}