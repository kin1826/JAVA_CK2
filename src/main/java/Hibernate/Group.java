package Hibernate;

import jakarta.persistence.*;

@Entity
@Table(name = "Groupschat")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GROUP")
    private int id;

    @Column(name = "GR_NAME")
    private String groupName;

    @Lob
    @Column(name = "GR_AVT", nullable = true)
    private byte[] groupAvtUrl;

    @Column(name = "CREATE_BY")
    private int createBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public byte[] getGroupAvtUrl() {
        return groupAvtUrl;
    }

    public void setGroupAvtUrl(byte[] groupAvtUrl) {
        this.groupAvtUrl = groupAvtUrl;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public Group(int id, String groupName, byte[] groupAvtUrl, int createBy) {
        this.id = id;
        this.groupName = groupName;
        this.groupAvtUrl = groupAvtUrl;
        this.createBy = createBy;
    }

    public Group() {
    }
}
