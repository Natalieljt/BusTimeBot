package logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Hex format : " + sb.toString());

        //convert the byte to hex format method 2
        StringBuilder hexString = new StringBuilder();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Get time difference of the input date from the current time
     *
     * @param date
     *            string that is in "yyyy-MM-dd'T'HH:mm:ssXXX" format
     * @return (if Calendar.SECOND, return in seconds otherwise in minutes), Long.MAX_VALUE if date is invalid
     */
    public static long getTimeFromNow(String date, int type) {
        long difference;
        try {
            long now = new Date().getTime();
            long designatedTime = format.parse(date).getTime();
            long divisor = (type == Calendar.SECOND) ? 1000 : (60 * 1000);
            difference = (designatedTime - now) / divisor;
        } catch (ParseException e) {
            difference = Long.MAX_VALUE;
        }
        return difference;
    }

    /**
     * Calculate the distance between 2 coordinates (lat+lon = 1 coordinate)
     *
     * @param lat1
     *            coordinate
     * @param lon1
     *            coordinate
     * @param lat2
     *            coordinate
     * @param lon2
     *            coordinate
     * @return distance in km
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double distance = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;
        //Convert to Km
        distance = distance * 1.609344;
        return distance;
    }

    /**
     * Convert degree to radian
     *
     * @param degree
     * @return radian
     */
    private static double deg2rad(double degree) {
        return (degree * Math.PI / 180.0);
    }

    /**
     * Convert radian to degree
     *
     * @param radian
     * @return degree
     */
    private static double rad2deg(double radian) {
        return (radian * 180 / Math.PI);
    }

    /**
     * Pads the string with spaces at the end until a certain length
     */
    public static String padEnd(String input, int length) {
        String paddedInput = input;
        while (paddedInput.length() < length) {
            paddedInput = paddedInput + " ";
        }
        return paddedInput;
    }

    /**
     * Pads the string with spaces at the front until a certain length
     */
    public static String padFront(String input, int length) {
        String paddedInput = input;
        while (paddedInput.length() < length) {
            paddedInput = " " + paddedInput;
        }
        return paddedInput;
    }


    public static String padBusTime(String input) {
        return padEnd(input, 3);
    }

    public static String padBusTitle(String input) {
        int lengthToPad = (input.length() <= 5) ? 5 : 13;
        return padEnd(input, lengthToPad);
    }
}
