# Rocket-Elevators-Java-Controller
## Description

This controller's whole purpose is to handle a personalized amount of elevators with a personalized amount of floors in a personalized amount of columns in a battery.

It can be controller from any floor from the outside of the elevators, but mainly in the lobby.

When used from the lobby, the battery choose the column that serves the floor selected by the user and then sends the best elevator possible for that spec floor and direction. Then, when used from the inside of the elevator that was selected by the column, the elevator is moved to the to the user's destination.

Otherwise, when called from another floor than the lobby, the column selects again the best elevator possible

Elevator selection is based on the elevator's status, current floor, direction and floor request list and on the user's floor and direction.

## Dependencies

As long as you have **VS Code's Coding Pack for Java and Extension Pack for Java** installed on your computer and then you've restarted your computer, nothing more needs to be installed