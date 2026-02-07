package Hibernate;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "PASS", length = 255)
    private String pass;

    @Column(name = "NICKNAME", length = 100)
    private String nickname;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "MAIL", length = 45)
    private String mail;

    @Lob
    @Column(name = "AVATAR_IMG")
    private byte[] avatarImg;

    @Column(name = "STATUS", length = 20)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_AT")
    private Date createAt;

    @Lob
    @Column(name = "COVER_IMG")
    private byte[] coverImg;

    @Column(name = "SEX", length = 45)
    private String sex;

    @Lob
    @Column(name = "BIO")
    private String bio;

    @Column(name = "SIZEATV", length = 45)
    private String sizeAtv;

    @Column(name = "SIZECOVER", length = 45)
    private String sizeCover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public byte[] getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(byte[] avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public byte[] getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(byte[] coverImg) {
        this.coverImg = coverImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSizeAtv() {
        return sizeAtv;
    }

    public void setSizeAtv(String sizeAtv) {
        this.sizeAtv = sizeAtv;
    }

    public String getSizeCover() {
        return sizeCover;
    }

    public void setSizeCover(String sizeCover) {
        this.sizeCover = sizeCover;
    }

    public User(int id, String name, String pass, String nickname, Date birthday, String mail, byte[] avatarImg, String status, Date createAt, byte[] coverImg, String sex, String bio, String sizeAtv, String sizeCover) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.nickname = nickname;
        this.birthday = birthday;
        this.mail = mail;
        this.avatarImg = avatarImg;
        this.status = status;
        this.createAt = createAt;
        this.coverImg = coverImg;
        this.sex = sex;
        this.bio = bio;
        this.sizeAtv = sizeAtv;
        this.sizeCover = sizeCover;
    }

    public User() {

    }
}
