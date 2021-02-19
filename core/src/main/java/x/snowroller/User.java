package x.snowroller;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User {
  @Id
  private String ID;

  private String UserName;
  private String Password;
  private String FirstName;
  private String LastName;
  private String Email;
  private String Phone;

  @Override
  public String toString() {
    return "User [id=" + ID +
            ", username=" + UserName + ", password=" + Password + "]\n";
  }

  //leave empty to avoid this error
  //"No default constructor for entity"
  public User() {

  }

  // it doesn't crash if you have both constructors
  public User(String ID, String userName, String password, String firstName, String lastName, String email, String phone) {
    this.ID = ID;
    UserName = userName;
    Password = password;
    FirstName = firstName;
    LastName = lastName;
    Email = email;
    Phone = phone;
  }

  public String getID() {
    return ID;
  }
  public void setID(String ID) {
    this.ID = ID;
  }

  public String getUserName() {
    return UserName;
  }
  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getPassword() {
    return Password;
  }
  public void setPassword(String password) {
    Password = password;
  }

  public String getFirstName() {
    return FirstName;
  }
  public void setFirstName(String firstName) {
    FirstName = firstName;
  }

  public String getLastName() {
    return LastName;
  }
  public void setLastName(String lastName) {
    LastName = lastName;
  }

  public String getEmail() {
    return Email;
  }
  public void setEmail(String email) {
    Email = email;
  }

  public String getPhone() {
    return Phone;
  }
  public void setPhone(String phone) {
    Phone = phone;
  }

}
