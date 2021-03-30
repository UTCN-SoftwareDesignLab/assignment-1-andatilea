package model.validation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {

    private static final int MIN_AMOUNT = 0;
    private static final int ID_NUMBER_LENGTH = 5;


    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    public AccountValidator() {
        errors = new ArrayList<>();
    }

    public boolean validateID_Number(String idNumber) {
        if (idNumber.length() !=  ID_NUMBER_LENGTH) {
            errors.add("Invalid Identification Number!");
            return false;
        }
        if (containsSpecialCharacter(idNumber)) {
            errors.add("Invalid Identification Card Number! ID Card must not contain special characters!");
            return false;
        }
        if (!containsDigit(idNumber)) {
            errors.add("ID Card must contain only digits!");
            return false;
        }
        return true;
    }

    public boolean validateAmount(Integer amount) {
        if (amount < MIN_AMOUNT) {
            errors.add("Amount of money not possible");
            return false;
        }
        return true;
    }

    private boolean containsSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }


}