import java.util.ArrayList;
import java.util.List;

class ColumnElevator {
    Column column;
    Elevator elevator;
}

public class Battery {
    public char columnID = 'A';
    public int floorRequestButtonID = 1;
    public int ID;
    public String status;
    public List<Column> columnsList;
    public List<FloorRequestButtons> floorRequestButtonsList;

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

    public void createBasementColumn(int _amountOfBasement, int _amountOfElevatorPerColumn) {
        List<Integer> servedFloors = new ArrayList<Integer>();
        int floor = -1;
        for (int i = 0; i < _amountOfBasement; i++) {
            servedFloors.add(floor);
            floor--;
        }

        System.out.print("Here's Basement Column ");
        System.out.println(columnID);
        Column basementColumn = new Column(columnID, _amountOfElevatorPerColumn, servedFloors, true);
        columnsList.add(basementColumn);
        columnID++;
    }

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
            System.out.print("=========Here's Column");
            System.out.println(columnID);
            Column column = new Column(columnID, _amountOfElevatorPerColumn, servedFloors, false);
            columnsList.add(column);
            columnID++;
        }
    }

    public void createFloorRequestButtonss(double _amountOfFloors) {
        int buttonFloor = 1;
        for (int i = 0; i < _amountOfFloors; i++) {
            FloorRequestButtons floorRequestButton = new FloorRequestButtons(floorRequestButtonID, buttonFloor, "up");
            floorRequestButtonsList.add(floorRequestButton);
            buttonFloor++;
            floorRequestButtonID++;
        }
    }

    public void createBasementFloorRequestButtonss(int _amountOfBasements) {
        int buttonFloor = -1;
        for (int i = 0; i < _amountOfBasements; i++) {
            FloorRequestButtons floorRequestButton = new FloorRequestButtons(floorRequestButtonID, buttonFloor, "down");
            floorRequestButtonsList.add(floorRequestButton);
            buttonFloor--;
            floorRequestButtonID++;
        }
    }

    public ColumnElevator assignElevator(int _requestedFloor, String _direction) {
        ColumnElevator columnElevator = new ColumnElevator();
        columnElevator.column = findBestColumn(_requestedFloor);
        columnElevator.elevator = columnElevator.column.findElevator(1, _direction);
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