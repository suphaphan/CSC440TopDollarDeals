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
    public final Long id;

    @Constraints.Required
    public final String itemName;

    @Constraints.Required
    public final Double itemPrice;
    
    @Constraints.Required
    public final String storeName;

    @Constraints.Required
    public final String storeAddressLine1;

    @Constraints.Required
    public final String storeAddressLine2;

    @Constraints.Required
    public final String storeCity;

    @Constraints.Required
    public final Long storeZip; 

    /**
     * Item
     * Constructor
     * @param name
     * @param price
     */
    public Item(String itemName, Double itemPrice, String storeName, String storeAddressLine1,
    	String storeAddressLine2, String storeCity, Long storeZip)
    {
    	this.itemName = itemName;
    	this.itemPrice = itemPrice;
    	this.storeName = storeName;
    	this.storeAddressLine1 = storeAddressLine1;
    	this.storeAddressLine2 = storeAddressLine2;
    	this.storeCity = storeCity;
    	this.storeZip = storeZip;
    }

   
    public static Finder<Long,Item> find = new Finder<Long,Item>(Long.class, Item.class);
}
