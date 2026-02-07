package Hibernate;

import jakarta.persistence.*;

@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FRSHIP")
    private int ID_FRSHIP;

    @Column(name = "ID_USER_FK")
    private int ID_USER_FK;

    @Column(name = "ID_FRIEND")
    private int ID_FRIEND;

    @Column(name = "STATUS")
    private String STATUS;

    public int getID_FRSHIP() {
        return ID_FRSHIP;
    }

    public void setID_FRSHIP(int ID_FRSHIP) {
        this.ID_FRSHIP = ID_FRSHIP;
    }

    public int getID_USER_FK() {
        return ID_USER_FK;
    }

    public void setID_USER_FK(int ID_USER_FK) {
        this.ID_USER_FK = ID_USER_FK;
    }

    public int getID_FRIEND() {
        return ID_FRIEND;
    }

    public void setID_FRIEND(int ID_FRIEND) {
        this.ID_FRIEND = ID_FRIEND;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public Friend(int ID_FRSHIP, int ID_USER_FK, int ID_FRIEND, String STATUS) {
        this.ID_FRSHIP = ID_FRSHIP;
        this.ID_USER_FK = ID_USER_FK;
        this.ID_FRIEND = ID_FRIEND;
        this.STATUS = STATUS;
    }
    public Friend() {
    }
}
