package common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Shariar (Shawn) Emami
 */
public class Test {

    public static void main(String[] arg) {
        Date date1 = new Date();

        Map<String, String[]> map = new HashMap<>();
        map.put("DATE_KEY",
                new String[]{
                    Long.toString(date1.getTime())});

        Date date2 = new Date(
                Long.parseLong(
                        map.get("DATE_KEY")[0]));

        if (date1.equals(date2)) {
            System.out.println("dates are the same");
        } else {
            System.out.println("dates are diffrent");
        }
    }
}
