package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.List;
import java.util.Set;

public class AdminResource {
    private static AdminResource adminResource = null;

    private AdminResource() {
    }

    public static AdminResource getInstance() {
        if (null == adminResource) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRoom(Set<IRoom> rooms){
        for (IRoom r : rooms){
            reservationService.addRoom(r);
        }
    }

    public Set<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Set<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayReservations(){
        reservationService.printAllReservation();
    }
}
