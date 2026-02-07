package INCHAT;

import java.util.Date;

public class UserInfor {
    private int id;
    private String name;
    private String nickname;
    private Date birthday;
    private String mail;
    private byte[] avatarImg;
    private String status;
    private Date createAt;
    private byte[] coverImg;
    private String sex;
    private String bio;
    private String sizeAtv;
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

    public UserInfor() {

    }

    public UserInfor(int id, String name, String nickname, Date birthday, String mail, byte[] avatarImg, String status, Date createAt, byte[] coverImg, String sex, String bio, String sizeAtv, String sizeCover) {
        this.id = id;
        this.name = name;
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
}
