import java.util.TimeZone;
import ui.LibraryMenu;

public class Main {

    public static void main(String[] args) {

        TimeZone.setDefault(
                TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        );

        System.out.println(
                TimeZone.getDefault().getID()
        );

        LibraryMenu menu = new LibraryMenu();

        menu.start();
    }
}