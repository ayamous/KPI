package ma.itroad.ram.kpi.utils;

import java.time.LocalDateTime;
import java.util.UUID;

public class RandomsUtils {

    public static String randomPassword() {

        String randomUUID = UUID.randomUUID().toString();
        System.out.println(randomUUID + " of length " + randomUUID.length());
        // Remove dashes
        String randomPassword = randomUUID.replaceAll("-", "");
        System.out.println(randomPassword);

        return randomPassword;

    }

    public static String randomUsername(String lastName, String firstName) {
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int minute = now.getMinute();
        int second = now.getSecond();
//        String username = lastname+"@"+day+minute+second;
      /*  return new StringBuilder(lastname)
                .append("@").append(day).append(minute).append(second).toString();*/
        return new StringBuilder(lastName).append(day).append(minute).append(second).toString();

    }

}
