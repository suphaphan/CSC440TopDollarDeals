package models;

import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class User extends Model {
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
}
