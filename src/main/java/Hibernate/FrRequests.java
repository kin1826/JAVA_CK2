package Hibernate;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "frrequests")
public class FrRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_ID")
    private int REQUEST_ID;

    @Column(name = "ID_USER")
    private int ID_USER;

    @Column(name = "ID_RECEIVER")
    private int ID_RECEIVER;

    @Column(name = "STATUS")
    private String STATUS;

    @Column(name = "SEND_AT")
    private Timestamp SEND_AT;

    public int getREQUEST_ID() {
        return REQUEST_ID;
    }

    public void setREQUEST_ID(int REQUEST_ID) {
        this.REQUEST_ID = REQUEST_ID;
    }

    public int getID_USER() {
        return ID_USER;
    }

    public void setID_USER(int ID_USER) {
        this.ID_USER = ID_USER;
    }

    public int getID_RECEIVER() {
        return ID_RECEIVER;
    }

    public void setID_RECEIVER(int ID_RECEIVER) {
        this.ID_RECEIVER = ID_RECEIVER;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public Timestamp getSEND_AT() {
        return SEND_AT;
    }

    public void setSEND_AT(Timestamp SEND_AT) {
        this.SEND_AT = SEND_AT;
    }

    public FrRequests(int REQUEST_ID, int ID_USER, int ID_RECEIVER, String STATUS, Timestamp SEND_AT) {
        this.REQUEST_ID = REQUEST_ID;
        this.ID_USER = ID_USER;
        this.ID_RECEIVER = ID_RECEIVER;
        this.STATUS = STATUS;
        this.SEND_AT = SEND_AT;
    }
    public FrRequests() {
    }
}
