package model;

import java.time.LocalDate;
import java.util.Date;

public class Account {

    private Long id;
    private Long client_id;
    private Long identification_nb;
    private String type;
    private Integer amount_of_money;
    private String date_of_creation;

    public Long getId() { return id;}

    public void setId(Long id) {this.id = id;}

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Long getIdentification_nb() {
        return identification_nb;
    }

    public void setIdentification_nb(Long identification_nb) {
        this.identification_nb = identification_nb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount_of_money() {
        return amount_of_money;
    }

    public void setAmount_of_money(int amount_of_money) {
        this.amount_of_money = amount_of_money;
    }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }
}
