package model.validation;

import model.Account;
import model.Client;

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

    private final Account account;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateID_Number(String.valueOf(account.getIdentification_nb()));
        validateAmount(account.getAmount_of_money());
        return errors.isEmpty();
    }

    private void validateID_Number(String idNumber) {
        if (idNumber.length() !=  ID_NUMBER_LENGTH) {
            errors.add("Invalid Identification Number!");
        }
        if (containsSpecialCharacter(idNumber)) {
            errors.add("Invalid Identification Card Number! ID Card must not contain special characters!");
        }
        if (!containsDigit(idNumber)) {
            errors.add("ID Card must contain only digits!");
        }
    }

    private void validateAmount(Integer amount) {
        if (amount < MIN_AMOUNT) {
            errors.add("Amount of money not possible");
        }
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