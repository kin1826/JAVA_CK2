package KINKEN_CHAT;

import Hibernate.Group;
import Hibernate.User;

import java.util.ArrayList;

public interface ChoiceUserChat {
    void choice(User user, String mail, String name, String status, byte[] userAvatar);
    void choiceGroup(Group group, String name, ArrayList<String> users, byte[] grAvatar);
}
