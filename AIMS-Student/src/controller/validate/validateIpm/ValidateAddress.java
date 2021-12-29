package controller.validate.validateIpm;

import controller.validate.ValidateInterface;
import org.junit.platform.commons.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateAddress implements ValidateInterface {
    @Override
    public boolean validateString(String address) {
        // TODO: your work
        if (StringUtils.isBlank(address)) return false;

        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]///~]");

        Matcher hasSpecial = special.matcher(address);

        return !hasSpecial.find();
    }
}
