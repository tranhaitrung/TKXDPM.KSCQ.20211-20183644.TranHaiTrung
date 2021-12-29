package controller.validate.validateIpm;

import controller.validate.ValidateInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatePhone implements ValidateInterface {
    @Override
    public boolean validateString(String phoneNumber) {
        if (phoneNumber.length() != 10) return false;

        if(!phoneNumber.startsWith("0")) return false;

        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasLetter = letter.matcher(phoneNumber);
        Matcher hasSpecial = special.matcher(phoneNumber);

        if (hasLetter.find() || hasSpecial.find()) return false;

        try {
            Integer.parseInt(phoneNumber);
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
}
