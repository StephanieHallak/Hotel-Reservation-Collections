import java.text.ParseException;
import java.util.Scanner;

public class HotelApplication {

    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu(scanner);
        try {
            mainMenu.menu();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
