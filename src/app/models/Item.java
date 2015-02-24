package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Item extends Model {
    @Id
    public Integer itemId;

    @Constraints.Required
    public String itemName;
	  @Constraints.Required
	  public String storeName;
    @Constraints.Required
    public Long storeZip;

    public Item(String item, String store, Long zip) {
      this.itemName = item;
      this.storeName = store;
      this.storeZip = zip;
    }

    public static Finder<Integer,Item> find = new Finder<Integer,Item>(
        Integer.class, Item.class
    );
}
