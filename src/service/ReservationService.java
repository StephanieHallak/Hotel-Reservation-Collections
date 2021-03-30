package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ReservationService {
    private static ReservationService reservationService = null;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (null == reservationService) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    private Set<IRoom> roomSet = new HashSet<>();
    private Set<Reservation> reservationSet = new HashSet<>();

    public void addRoom(IRoom room){
        roomSet.add(room);
    }

    public IRoom getRoom(String roomNumber){
        for(IRoom room : roomSet){
            if(room.getRoomNumber().equals(roomNumber)){
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationSet.add(reservation);
        return reservation;
    }

    public Set<Reservation> getCustomerReservation(Customer customer){
        Set<Reservation> set = new HashSet<>();
        for(Reservation reservation : reservationSet){
            if(reservation.getCustomer().equals(customer)){
                set.add(reservation);
            }
        }
        return set;
    }

    public void printAllReservation(){
        for (Reservation reservation : reservationSet){
            System.out.println(reservation);
        }
    }

    public Set<IRoom> getAllRooms(){
        return roomSet;
    }

    public Set<IRoom> findRooms (Date checkInDate, Date checkOutDate){
        Set<IRoom> availableRooms = new HashSet<>(roomSet);
        if (reservationSet.size()!=0) {
            for (IRoom room : roomSet) {
                for (Reservation reservation : reservationSet) {
                    if(room.getRoomNumber().equals(reservation.getRoom().getRoomNumber())) {

                        if((reservation.getCheckInDate().before(checkOutDate) && reservation.getCheckOutDate().after(checkInDate))){
                            //this means that the room is reserved and intersect with reservation date wanted
                            availableRooms.remove(room);
                        }
//                        else {
//                        }
                    }
                }
            }
        }
        return availableRooms;
    }
}