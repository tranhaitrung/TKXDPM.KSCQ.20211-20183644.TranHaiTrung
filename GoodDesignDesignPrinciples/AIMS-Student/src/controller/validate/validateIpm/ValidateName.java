package controller.validate.validateIpm;

import controller.validate.ValidateInterface;
import org.junit.platform.commons.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateName implements ValidateInterface {
    @Override
    public boolean validateString(String name) {
        // TODO: your work
        if (StringUtils.isBlank(name)) return false;

        //Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        //Pattern eight = Pattern.compile (".{8}");


        //Matcher hasLetter = letter.matcher(name);
        Matcher hasDigit = digit.matcher(name);
        Matcher hasSpecial = special.matcher(name);

        return !hasDigit.find() && !hasSpecial.find();
    }
}
