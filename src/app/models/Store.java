package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.*;
import com.avaje.ebean.annotation.*;

import controllers.BCrypt;

/**
 * Store
 * @author Group 5
 *
 */
@Entity
public class Store extends Model {
    

    @Id
    public int storeId;

    @Constraints.Required
    public String storeName;
    
    @Constraints.Required
    public String storeAddress;

    /**
     * Store
     * Constructor
     * @param name
     * @param address
     */
    public Store(String name, String address) 
    {
      this.storeName = name;
      this.storeAddress = address;
    }

   
    public static Finder<String,Store> find = new Finder<String,Store>(String.class, Store.class);   
    
    /**
     * getStore
     * get store id
     * @return id;
     */
    public int getStoreId()
    {
		return this.storeId;    	
    }
    
}
