package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.*;
import com.avaje.ebean.annotation.*;

import controllers.BCrypt;

/**
 * Item
 * @author Group 5
 *
 */
@Entity
public class Item extends Model {
    

    @Id
    public int itemId;

    @Constraints.Required
    public String itemName;
    
    @Constraints.Required
    public double itemPrice;

    @Constraints.Required
    public int storeId;

    /**
     * Item
     * Constructor
     * @param name
     * @param price
     */
    public Item(String name, double price, String storeName, String storeAdd) 
    {
      Store st = new Store(storeName, storeAdd);
      this.itemName = name;
      this.itemPrice = price;
      this.storeId = st.getStoreId();
    }

   
    public static Finder<String,Item> find = new Finder<String,Item>(String.class, Item.class);

    /**
     * findDuplicate
     * validate duplicate items
     * @return msg
     */
    public String findDuplicate() {
    	
    	// we need to do search/compare name in the database here to see if there is any match.
      String msg = null;
     // if ()
      {
        msg = "This item is already in the system.";
      }
      return msg;
    }
}
