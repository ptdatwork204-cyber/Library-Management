import ui.LibraryMenu;

import java.nio.charset.Charset;
import java.util.TimeZone;



public class Main {

    public static void main(String[] args) {

        System.out.println("=== ENVIRONMENT INFO ===");
        System.out.println("Default Charset: " + Charset.defaultCharset());
        System.out.println("file.encoding: " + System.getProperty("file.encoding"));

        TimeZone.setDefault(
                TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        );

        System.out.println("Timezone: " +
                TimeZone.getDefault().getID());

        System.out.println("========================");

        LibraryMenu menu = new LibraryMenu();

        menu.start();
    }
}