import java.util.ArrayList;
import java.util.List;

//Declares BestElevatorInformations
class BestElevatorInformations {
    public Elevator bestElevator;
    public int bestScore;
    public int referenceGap;
}

// Declares each Column
public class Column {
    public int callButtonID = 1;

    public char elevatorID = 'A';

    public String ID;
    public String status;
    public List<Integer> servedFloors;
    public boolean isBasement;
    public List<Elevator> elevatorsList;
    public List<CallButton> callButtonsList;

    // Function used to create new Columns with the desired properties
    public Column(char _ID, int _amountOfElevators, List<Integer> _servedFloors, Boolean _isBasement) {
        this.ID = Character.toString(_ID);
        this.status = "online";
        this.servedFloors = _servedFloors;
        this.isBasement = _isBasement;
        this.elevatorsList = new ArrayList<Elevator>();
        this.callButtonsList = new ArrayList<CallButton>();

        createElevators(servedFloors.size(), _amountOfElevators);
        createCallButtons(servedFloors.size(), isBasement);
    }

    // Method used by the Column to create Call Buttons
    public void createCallButtons(int _amountOfFloors, Boolean _isBasement) {
        if (_isBasement) {
            int buttonFloor = -1;
            for (int i = 0; i < _amountOfFloors; i++) {
                CallButton callButton = new CallButton(1, buttonFloor, "up");
                callButtonsList.add(callButton);
                buttonFloor--;
                callButtonID++;
            }
        } else {
            int buttonFloor = 1;
            for (int i = 0; i < _amountOfFloors; i++) {
                CallButton callButton = new CallButton(1, buttonFloor, "down");
                callButtonsList.add(callButton);
                buttonFloor++;
                callButtonID++;
            }
        }
    }

    // Method used to create Elevators
    public void createElevators(int _amountOfFloors, int _amountOfElevators) {
        for (int i = 0; i < _amountOfElevators; i++) {
            Elevator elevator = new Elevator(elevatorID);
            elevatorsList.add(elevator);
            elevatorID++;
        }
    }

    // Simulate when a user press a button on a floor to go back to the first floor
    public Elevator requestElevator(int _userPosition, String _direction) {
        String str1 = String.format("An elevator has been requested from the lobby to the floor #%d\n", _userPosition);
        System.out.println(str1);
        Elevator bestElevator = findElevator(_userPosition, _direction);
        String str2 = String.format("Elevator %s on the floor #%d has been selected as the best elevator\n", bestElevator.ID, bestElevator.currentFloor);
        System.out.println(str2);
        bestElevator.addNewRequest(_userPosition);
        bestElevator.move();

        bestElevator.addNewRequest(1);
        bestElevator.move();

        return bestElevator;
    }

    // Method used to find the best Elevator possible
    public Elevator findElevator(int _requestedFloor, String _requestedDirection) {
        BestElevatorInformations bestElevatorInformations = new BestElevatorInformations();
        bestElevatorInformations.bestElevator = elevatorsList.get(0);
        bestElevatorInformations.bestScore = 6;
        bestElevatorInformations.referenceGap = servedFloors.size() * 2;

        if (_requestedFloor == 1) {
            for (Elevator elevator : elevatorsList) {
                if (elevator.currentFloor == 1 && elevator.status == "stopped") {
                    bestElevatorInformations = checkIfElevatorIsBetter(1, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else if (elevator.currentFloor == 1 && elevator.status == "idle") {
                    bestElevatorInformations = checkIfElevatorIsBetter(2, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else if (elevator.currentFloor < 1 && elevator.direction == "up") {
                    bestElevatorInformations = checkIfElevatorIsBetter(3, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else if (elevator.currentFloor > 1 && elevator.direction == "down") {
                    bestElevatorInformations = checkIfElevatorIsBetter(3, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else if (elevator.status == "idle") {
                    bestElevatorInformations = checkIfElevatorIsBetter(4, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else {
                    bestElevatorInformations = checkIfElevatorIsBetter(5, elevator, bestElevatorInformations,
                            _requestedFloor);
                }
            }
        } else {
            for (Elevator elevator : elevatorsList) {
                if (elevator.currentFloor == _requestedFloor && elevator.status == "stopped"
                        && elevator.direction == _requestedDirection) {
                    bestElevatorInformations = checkIfElevatorIsBetter(1, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else if (elevator.currentFloor < _requestedFloor && elevator.direction == "up"
                        && _requestedDirection == "up") {
                    bestElevatorInformations = checkIfElevatorIsBetter(2, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else if (elevator.currentFloor > _requestedFloor && elevator.direction == "down"
                        && _requestedDirection == "down") {
                    bestElevatorInformations = checkIfElevatorIsBetter(2, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else if (elevator.status == "idle") {
                    bestElevatorInformations = checkIfElevatorIsBetter(4, elevator, bestElevatorInformations,
                            _requestedFloor);
                } else {
                    bestElevatorInformations = checkIfElevatorIsBetter(5, elevator, bestElevatorInformations,
                            _requestedFloor);
                }
            }
        }
        return bestElevatorInformations.bestElevator;
    }

    // Method used to compared a new Elevator's information with the bestElevator's
    public BestElevatorInformations checkIfElevatorIsBetter(int _scoreToCheck, Elevator _newElevator,
            BestElevatorInformations bestElevatorInformations, int _floor) {
        if (_scoreToCheck < bestElevatorInformations.bestScore) {
            bestElevatorInformations.bestScore = _scoreToCheck;
            bestElevatorInformations.bestElevator = _newElevator;
            bestElevatorInformations.referenceGap = Math.abs(_newElevator.currentFloor - _floor);
        } else if (bestElevatorInformations.bestScore == _scoreToCheck) {
            int gap = Math.abs(_newElevator.currentFloor - _floor);
            if (bestElevatorInformations.referenceGap > gap)
                bestElevatorInformations.bestElevator = _newElevator;
            bestElevatorInformations.referenceGap = gap;
            {

            }
        }
        return bestElevatorInformations;
    }
}