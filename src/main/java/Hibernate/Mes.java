package Hibernate;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Mes")
public class Mes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MES")
    private int id;

    @Column(name = "ID_SENDER_FKMES")
    private int id_sender_fkmes;

    @Column(name = "ID_RECEIVER_FKMES", nullable = true)
    private Integer id_receiver_fkmes;

    @Column(name = "ID_GROUP_FKMES", nullable = true)
    private Integer id_group_fkmes;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CONTENT", columnDefinition = "LONGTEXT")
    private String content;

    @Lob
    @Column(name = "FILE_DATA")
    private byte[] file_data;

    @Column(name = "SENDAT")
    private Timestamp sendat;

    @Column(name = "STATUS")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_sender_fkmes() {
        return id_sender_fkmes;
    }

    public void setId_sender_fkmes(int id_sender_fkmes) {
        this.id_sender_fkmes = id_sender_fkmes;
    }

    public int getId_receiver_fkmes() {
        return id_receiver_fkmes;
    }

    public void setId_receiver_fkmes(Integer id_receiver_fkmes) {
        this.id_receiver_fkmes = id_receiver_fkmes;
    }

    public int getId_group_fkmes() {
        return id_group_fkmes;
    }

    public void setId_group_fkmes(Integer id_group_fkmes) {
        this.id_group_fkmes = id_group_fkmes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSendat() {
        return sendat;
    }

    public void setSendat(Timestamp sendat) {
        this.sendat = sendat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile_data() {
        return file_data;
    }

    public void setFile_data(byte[] file_data) {
        this.file_data = file_data;
    }

    public Mes(int id, int id_sender_fkmes, int id_receiver_fkmes, int id_group_fkmes, String content, Timestamp sendat, String status) {
        this.id = id;
        this.id_sender_fkmes = id_sender_fkmes;
        this.id_receiver_fkmes = id_receiver_fkmes;
        this.id_group_fkmes = id_group_fkmes;
        this.content = content;
        this.sendat = sendat;
        this.status = status;
    }

    public Mes() {

    }
}
