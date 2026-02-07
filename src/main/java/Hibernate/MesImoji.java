package Hibernate;

import jakarta.persistence.*;

@Entity
@Table(name = "Mesimoji")
public class MesImoji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMOJI_ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_MES_FKIMO")
    private Mes mes;

    @ManyToOne
    @JoinColumn(name = "ID_USER_FKIMO")
    private User user;

    @Column(name = "IMOJITYPE")
    private String imojiType;

    public MesImoji() {
    }

    public MesImoji(int id, Mes mes, User user, String imojiType) {
        this.id = id;
        this.mes = mes;
        this.user = user;
        this.imojiType = imojiType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImojiType() {
        return imojiType;
    }

    public void setImojiType(String imojiType) {
        this.imojiType = imojiType;
    }
}
