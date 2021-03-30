import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.ParseException;
import java.util.*;

public class AdminMenu {

    Scanner scanner;
    AdminResource adminResource = AdminResource.getInstance();

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() throws ParseException {
        this.displayMenu();
        Boolean returnMain = false;

        while (!returnMain){
            String option = scanner.nextLine();
            switch (option){
                case "1":
                    System.out.println("You chose to see all customers:");
                    adminResource.getAllCustomers().forEach(System.out::println);
                    break;
                case "2":
                    System.out.println("You chose to see all rooms:");
                    Set<IRoom> rooms = adminResource.getAllRooms();
                    rooms.forEach(System.out::println);
                    break;
                case "3":
                    System.out.println("You chose to see all reservations:");
                    adminResource.displayReservations();
                    break;
                case "4":
                    System.out.println("You chose to add a room:");
                    this.addARoom();
                    break;
                case "5":
                    System.out.println("You chose to return to main menu:");
                    MainMenu mainMenu = new MainMenu(scanner);
                    mainMenu.menu();
                    break;
            }
        }
    }

    private void displayMenu(){
        System.out.println("Welcome to the ADMIN section of the system!");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu");
        System.out.println("Please select your choice number from the menu: ");
    }

    private void addARoom() throws ParseException {
        Set<IRoom> addedRooms = new HashSet<>();
        String answer = null;
        do {
            System.out.println("Add room number: ");
            String roomNumber = scanner.nextLine();
            Set<IRoom> existedRooms = adminResource.getAllRooms();
            for (IRoom r : existedRooms) {
                while (r.getRoomNumber().equals(roomNumber)) {
                    System.out.println("This room number already exists, enter another one please!");
                    roomNumber = scanner.nextLine();
                }
            }
            for (IRoom r : addedRooms) {
                while (r.getRoomNumber().equals(roomNumber)) {
                    System.out.println("This room number already exists, enter another one please!");
                    roomNumber = scanner.nextLine();
                }
            }
            System.out.println("Add room price per night: ");
            Double roomPrice = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Add room type (1 for single - 2 for double): ");
            String roomType = scanner.nextLine();
            while (!roomType.equalsIgnoreCase("1") && !roomType.equalsIgnoreCase("2")) {
                System.out.println("Please enter 1 or 2");
                roomType = scanner.nextLine();
            }
            RoomType type = null;
            if (roomType.equalsIgnoreCase("1")) {
                type = RoomType.SINGLE;
            } else if (roomType.equalsIgnoreCase("2")) {
                type = RoomType.DOUBLE;
            }
            IRoom room = new Room(roomNumber, roomPrice, type);
            addedRooms.add(room);

            System.out.println("Do you want to add another room? y/n: ");
            answer = scanner.nextLine();
            while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                System.out.println("please enter y or n!");
                answer = scanner.nextLine();
            }
            if (answer.equalsIgnoreCase("n")) {
                break;
            }
        }
        while (answer.equalsIgnoreCase("y"));
        adminResource.addRoom(addedRooms);
        this.menu();

    }

}
