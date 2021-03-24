package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final String NAME_VALIDATION_REGEX = "^[a-zA-Z\\s]+";
    private static final long CNP_LENGTH = 9;
    private static final int CARD_ID_LENGTH = 5;
    private static final String ADDRESS_VALIDATION_REGEX = "^[a-zA-Z\\s]+";

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateName(client.getName());
        validateCNP(String.valueOf(client.getCNP()));
        validateIdentityCard(String.valueOf(client.getIdentityCardNb()));
        validateAddress(client.getAddress());
        return errors.isEmpty();
    }

    private void validateName(String name) {
        if (!Pattern.compile(NAME_VALIDATION_REGEX).matcher(name).matches()) {
            errors.add("Invalid name!");
        }
    }

    private void validateCNP(String cnp) {
        if (cnp.length() !=  CNP_LENGTH) {
            errors.add("Invalid CNP!");
        }

        if (!containsDigit(cnp)) {
            errors.add("CNP must contain only digits!");
        }
    }

    private void validateIdentityCard(String identityNb) {
        if (identityNb.length() !=  CARD_ID_LENGTH) {
            errors.add("Invalid IDENTITY CARD Number!");
        }

        if (!containsDigit(identityNb)) {
            errors.add("Identity Card Number must contain only digits!");
        }
    }

    private void validateAddress(String address) {
        if (!Pattern.compile(ADDRESS_VALIDATION_REGEX).matcher(address).matches()) {
            errors.add("Invalid address!");
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
