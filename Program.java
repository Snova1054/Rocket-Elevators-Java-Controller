public class Program {
    public static void main(String[] args) {
        System.out.println("Hello");
        Battery battery = new Battery(1, 4, 60, 6, 5);
        System.out.print(battery.ID);

        Column column = battery.columnsList.get(2);

        column.elevatorsList.get(0).currentFloor = 1;
        column.elevatorsList.get(0).direction = "up";
        column.elevatorsList.get(0).status = "stopped";
        column.elevatorsList.get(0).floorRequestsList.add(21);

        column.elevatorsList.get(1).currentFloor = 23;
        column.elevatorsList.get(1).direction = "up";
        column.elevatorsList.get(1).status = "moving";
        column.elevatorsList.get(1).floorRequestsList.add(28);

        column.elevatorsList.get(2).currentFloor = 33;
        column.elevatorsList.get(2).direction = "down";
        column.elevatorsList.get(2).status = "moving";
        column.elevatorsList.get(2).floorRequestsList.add(1);

        column.elevatorsList.get(3).currentFloor = 40;
        column.elevatorsList.get(3).direction = "down";
        column.elevatorsList.get(3).status = "moving";
        column.elevatorsList.get(3).floorRequestsList.add(24);

        column.elevatorsList.get(4).currentFloor = 39;
        column.elevatorsList.get(4).direction = "down";
        column.elevatorsList.get(4).status = "moving";
        column.elevatorsList.get(4).floorRequestsList.add(1);

        ColumnElevator columnElevator = battery.assignElevator(36, "up");
        columnElevator.column = moveAllElevators(columnElevator.column);
        System.out.println("Elevator ");
        System.out.println(column.elevatorsList.get(0).currentFloor);
        System.out.println("Elevator ");
        System.out.println(column.elevatorsList.get(1).currentFloor);
        System.out.println("Elevator ");
        System.out.println(column.elevatorsList.get(2).currentFloor);
        System.out.println("Elevator ");
        System.out.println(column.elevatorsList.get(3).currentFloor);
        System.out.println("Elevator ");
        System.out.println(column.elevatorsList.get(4).currentFloor);

    }

    public static Column moveAllElevators(Column column) {
        for (int i = 0; i < column.elevatorsList.size(); i++) {
            while (column.elevatorsList.get(i).floorRequestsList.size() != 0) {
                column.elevatorsList.get(i).move();
            }
        }
        return column;
    }
}