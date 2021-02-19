package x.snowroller;

import java.util.List;

public interface UserDAO {

  void create(User u);

  List<User> getByName(String username);

  boolean updatePassword(String ID, String newPassword);

  boolean remove(String ID);

}
