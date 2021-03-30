import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class MainMenu {

    private  Scanner scanner;
    private  HotelResource hotelResource = HotelResource.getInstance();


    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() throws ParseException {
        displayMenu();
        Boolean exit = false;
        while(!exit){
            String option = scanner.nextLine();
            switch (option){
                case "1":
                    System.out.println("You selected to make a new reservation: ");
                    findAndReserveARoom();
                    break;
                case "2":
                    System.out.println("You selected to see your reservations: ");
                    seeMyReservations();
                    break;
                case "3":
                    System.out.println("You selected to create a new account: ");
                    createAnAccount();
                    displayMenu();
                    break;
                case "4":
                    System.out.println("You selected the admin menu: ");
                    AdminMenu adminMenu = new AdminMenu(scanner);
                    adminMenu.menu();
                    break;
                case "5":
                    exit = true;
                    scanner.close();
                    break;
                default:
                    System.out.println("Invalid input: Choose a number between 1 and 5 included");
                    break;
            }
        }
    }

    private  void displayMenu(){
        System.out.println("Welcome to the hotel reservation system!");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("Please select your choice number from the menu: ");
    }

    private void reserveRoomWithAccount(Date checkInDate, Date checkOutDate){
        System.out.println("Enter you email address in format 'abc@domain.com':");
        String email = scanner.nextLine();
        //Customer customer = hotelResource.getCustomer(email);
        System.out.println("Enter the room number to book: ");
        String roomNumber = scanner.nextLine();
        IRoom room = hotelResource.getRoom(roomNumber);
        if (room == null){
            System.out.println("This room number is not available");
            displayMenu();
        }
        else{
            hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
            hotelResource.getCustomersReservations(email).forEach(System.out::println);
            displayMenu();
        }
    }

    private void findAndReserveARoom() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date checkOutDate, checkInDate;
        do {
            System.out.println("Enter CheckIn Date dd/MM/yyyy: ");
            checkInDate = dateFormat.parse(scanner.nextLine());
            System.out.println("Enter CheckOut Date dd/MM/yyyy: ");
            checkOutDate = dateFormat.parse(scanner.nextLine());
            if(!checkInDate.before(checkOutDate)){
                System.out.println("Please insure the check in date is before the check out date!");
            }
        }
        while (!checkInDate.before(checkOutDate));

        System.out.println("Available rooms for the dates: ");
        Set<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (availableRooms.size() == 0){
            System.out.println("Sorry, no rooms are available between those date! But we can provide you with\nrecommendation 1 week apart from your choice");
            Calendar c = Calendar.getInstance();
            c.setTime(checkInDate);
            c.add(Calendar.DATE, 7);
            checkInDate = c.getTime();
            c.setTime(checkOutDate);
            c.add(Calendar.DATE, 7);
            checkOutDate = c.getTime();
            System.out.println("Between " + checkInDate + " and " + checkOutDate);
            availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            if (availableRooms.size()==0){
                System.out.println("Sorry again, no availabilities!");
                this.displayMenu();

            }
            else {
                availableRooms.forEach(System.out::println);
                this.reserveARoom(checkInDate, checkOutDate);
            }
        }
        else {
            availableRooms.forEach(System.out::println);
            this.reserveARoom(checkInDate, checkOutDate);
        }

    }

    private void reserveARoom(Date checkInDate, Date checkOutDate) throws ParseException {
        System.out.println("Would you like to book a room? (y/n)");
        String answerRoom = scanner.nextLine();
        while ((answerRoom.equalsIgnoreCase("y") == false) && (answerRoom.equalsIgnoreCase("n") == false)) {
            System.out.println("Please answer by 'y' or 'n':");
            answerRoom = scanner.nextLine();
        }
        if (answerRoom.equalsIgnoreCase("y")) {
            System.out.println("Do you have an account with us? (y/n)");
            String answerAccount = scanner.nextLine();
            while (!answerAccount.equalsIgnoreCase("y") && !answerAccount.equalsIgnoreCase("n")) {
                System.out.println("Please answer by 'y' or 'n':");
                answerAccount = scanner.nextLine();
            }
            if (answerAccount.equalsIgnoreCase("y")) {
                reserveRoomWithAccount(checkInDate, checkOutDate);
            } else if (answerAccount.equalsIgnoreCase("n")) {
                createAnAccount();
                reserveRoomWithAccount(checkInDate, checkOutDate);
            }
        } else if (answerRoom.equalsIgnoreCase("n")) {
            menu();
        }
    }

    private void seeMyReservations(){
        System.out.println("Enter you email address: ");
        String email = scanner.nextLine();
        Set<Reservation> reservations = hotelResource.getCustomersReservations(email);
        if (reservations.size() == 0){
            System.out.println("You have no reservations yet!");
        }
        else {
            System.out.println(reservations);
        }
        displayMenu();
    }
    private void createAnAccount() throws ParseException {
        System.out.println("Enter you email address (format abc@domain.com): ");
        String email = scanner.nextLine();
        System.out.println("First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last name: ");
        String lastName = scanner.nextLine();
        hotelResource.createACustomer(email, firstName, lastName);
    }

}
