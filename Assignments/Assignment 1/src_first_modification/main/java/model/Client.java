package model;

public class Client {

    private Long id;
    private String name;
    private long identity_cardNr;
    private long cnp;
    private String address;

    public Long getId() { return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdentityCardNb() {
        return identity_cardNr;
    }

    public void setIdentity_cardNr(long identity_cardNr) {
        this.identity_cardNr = identity_cardNr;
    }

    public long getCNP() { return cnp;}

    public void setCNP(long cnp) { this.cnp = cnp;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
