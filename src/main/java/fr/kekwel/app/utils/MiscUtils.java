package fr.kekwel.app.utils;

import java.security.SecureRandom;

public class MiscUtils {
	public static String generateRandomString() {
 	    // You can customize the characters that you want to add into
 	    // the random strings
 	    String CHAR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 	    String NUMBER = "23456789";

 	    String DATA_FOR_RANDOM_STRING = CHAR_UPPER + NUMBER;
 	    SecureRandom random = new SecureRandom();

 	    StringBuilder sb = new StringBuilder(5);
 	    
 	    for (int i = 0; i < 5; i++) {
 	        int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
 	        char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

 	        sb.append(rndChar);
 	    }

 	    return sb.toString();
 	}
}
