package model.builder;
import model.Account;

import java.time.LocalDate;
import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setClient_id(Long client_id) {
        account.setClient_id(client_id);
        return this;
    }

    public AccountBuilder setIdentification_nb(Long identification_nb) {
        account.setIdentification_nb(identification_nb);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmount_of_money(Integer amount_of_money) {
        account.setAmount_of_money(amount_of_money);
        return this;
    }

    public AccountBuilder setDate_of_creation(String date_of_creation) {
        account.setDate_of_creation(date_of_creation);
        return this;
    }

    public Account build() {
        return account;
    }
}
