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
    public Long id;

    @Constraints.Required
    public String itemName;

    @Constraints.Required
    public Double itemPrice;
    
    @Constraints.Required
    public String storeName;

    @Constraints.Required
    public String storeAddressLine1;

    public String storeAddressLine2;

    @Constraints.Required
    public String storeCity;

    @Constraints.Required
    public String storeState;

    @Constraints.Required
    public Long storeZip; 

    /**
     * Item
     * Constructor
     * @param name
     * @param price
     */
    public Item(String itemName, Double itemPrice, String storeName, String storeAddressLine1,
    	String storeAddressLine2, String storeCity, String storeState, Long storeZip)
    {
    	this.itemName = itemName;
    	this.itemPrice = itemPrice;
    	this.storeName = storeName;
    	this.storeAddressLine1 = storeAddressLine1;
    	this.storeAddressLine2 = storeAddressLine2;
    	this.storeCity = storeCity;
    	this.storeState = storeState;
    	this.storeZip = storeZip;
    }

   
    public static Finder<Long,Item> find = new Finder<Long,Item>(Long.class, Item.class);
}
