package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.*;

import controllers.BCrypt;

/**
 * User
 * @author Group 5
 *
 */
@Entity
public class User extends Model {
    /* Email regex from http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/ */
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Id
    public String email;

    @Constraints.Required
    public String password;
    
    @Constraints.Required
    public String name;

    @Constraints.Required
    public Long zip;

    /**
     * User
     * Constructor
     * @param email
     * @param password
     * @param zip
     * @param name
     */
    public User(String email, String password, Long zip, String name) {
      this.name = name;
      this.email = email;
      this.password = password;
      this.zip = zip;
    }

   
    public static Finder<String,User> find = new Finder<String,User>(String.class, User.class);

    /**
     * validate
     * validate email for user
     * @return msg
     */
    public String validate() {
      Matcher validEmail = EMAIL_PATTERN.matcher(email);
      String msg = null;
      if (!validEmail.matches()){
        msg = "Email specified is not a valid email.";
      }
      return msg;
    }

    /**
     * isPassowrd
     * where we check user's password
     * @param potentialPassword
     * @return boolean
     */
    public boolean isPassword(String potentialPassword) {
      return BCrypt.checkpw(potentialPassword, password);
    }

    /**
     * changePassword
     * when user click update button, save the password
     * @param newPassword
     */
    public void changePassword(String newPassword) {
      password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }
    
    /**
     * changeName
     * when user click update button, save the name
     * @param newName
     */
    public void changeName(String newName) {
        name = newName;
      }
}
