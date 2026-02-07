package Hibernate;

import jakarta.persistence.*;

@Entity
@Table(name = "Groupmember")
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEM_ID")
    private int id;

    @Column(name = "ID_GROUP_PKMEM")
    private int groupId;

    @Column(name = "ID_USER_PKMEM")
    private int userId;

    @Column(name = "NICKNAME")
    private String nickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public GroupMember(int id, int groupId, int userId, String nickname) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.nickname = nickname;
    }

    public GroupMember() {
    }
}
