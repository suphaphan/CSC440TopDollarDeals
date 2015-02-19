package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import controllers.BCrypt;

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
    public Long zip;

    public User(String email, String password, Long zip) {
      this.email = email;
      this.password = password;
      this.zip = zip;
    }

    public static Finder<String,User> find = new Finder<String,User>(
        String.class, User.class
    );

    public String validate() {
      Matcher validEmail = EMAIL_PATTERN.matcher(email);
      if (!validEmail.matches())
        return "Email specified is not a valid email.";
      else
        return null;
    }

    public boolean isPassword(String potentialPassword) {
      return BCrypt.checkpw(potentialPassword, password);
    }

    public void changePassword(String newPassword) {
      password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }
}
