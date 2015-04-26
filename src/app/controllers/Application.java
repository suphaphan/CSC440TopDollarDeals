package controllers;

import play.mvc.*;
import play.data.*;

import views.html.*;
import models.*;

/**
 * Application 
 * Where all the actions happen
 * @author Group 5
 *
 */
public class Application extends Controller {

	/**
	 * index
	 * default render homepage
	 * @return result
	 */
    public static Result index() {
        return ok(index.render());
    }

    /**
     * login
     * if user email is null or empty then stay at login/register page
     * with error message
     * else login successful then redirect to homepage
     * @return result
     */
    public static Result login() {
        if (session("user_email") == null)
            return ok(login.render(Form.form(User.class), Form.form(User.class)));
        else
            return redirect(routes.Application.index());
    }

    /**
     * doLogin
     * where we validate inputs for login
     * @param data
     * @param isRegistration
     * @return result of user
     */
    public static Result doLogin(Form<User> data) {
        User potentialUser = data.get();
        User user = User.find.byId(potentialUser.email);

        /* Check if correct password was supplied. */
        if ( (user == null) || (!user.isPassword(potentialUser.password)) ) {
            flash("login_status", "Invalid username or password specified!");
            return badRequest(login.render(data, Form.form(User.class)));
        }

        /* Clear old session data. */
        session().clear();

        /* Add simple variables here to use in views. */
        session("user_email", user.email);
        session("user_name", user.name);

        flash("login_status", "Successfully logged in!");

        return redirect(routes.Application.index());
    }

    /**
     * authenticate
     * where we check bad input from login_form
     * @return result
     */
    public static Result authenticate() {
        Form<User> data = Form.form(User.class).bindFromRequest();

        if (data.hasErrors()) {
            flash("login_status", "Please enter data into all fields in the Login form.");
            return badRequest(login.render(data, Form.form(User.class)));
        } else {
            return doLogin(data);
        }
    }

    /**
     * register
     * This is called when user registers.
     * @return result
     */
    public static Result register() {
        User newUser = null;

        /* Get data from HTML form. */
        Form<User> data = Form.form(User.class).bindFromRequest();

        /* Error checking. */
        if (data.hasErrors()) {
            flash("login_status", "Please enter data into all fields in the Register form.");
            return badRequest(login.render(Form.form(User.class), data));
        }

        /* Get data from form. */
        newUser = data.get();

        /* Check to see if there's already a user for this email. */
        if (User.find.byId(newUser.email) != null) {
            flash("login_status", "Account already exists for email!");
            return badRequest(login.render(Form.form(User.class), data));
        }

        /* Change user password and save to database. */
        newUser.changePassword(newUser.password);
        newUser.save();

        /* Display success to user, prompt to log in. */
        flash("login_status", "Successfully registered!");
        return ok(login.render(data, Form.form(User.class)));
    }

    /**
     * updateUser
     * this is called then user hit update button
     * @return result
     */
    public static Result updateUser(){
    	User existingUser = User.find.byId(session().get("user_email"));;
    	 /* Get data from HTML form. */
        Form<User> data = Form.form(User.class).bindFromRequest();

        /* Error checking. */
        if (data.hasErrors()) {
            flash("login_status", "Please enter data into all fields in the Update form.");
            return badRequest(user.render(data));
        }

        /* Get data from form. */
        existingUser = data.get();

        /* Change user password and save to database. */
        existingUser.changePassword(existingUser.password);
        existingUser.changeName(existingUser.name);
        existingUser.update((Object)existingUser.email);

        /* Clear old session data. */
        session().clear();

        /* Add simple variables here to use in views. */
        session("user_email", existingUser.email);
        session("user_name", existingUser.name);

        /* Display success to user that their profile has been updated. */
        flash("update_status", "Successfully udpated!");
		return redirect(routes.Application.user());
    }
    
    /**
     * deleteUser
     * this is called then user hit delete icon
     * @return result
     */
    public static Result deleteUser(){
    	User existingUser = User.find.byId(session().get("user_email"));;

        /* Delete user */
        existingUser.delete();

        /* Clear the user's session info. */
        session().clear();

        /* Display success to user that their profile has been updated. */
        flash("login_status", "Successfully Delete!");
		return redirect(routes.Application.index());
    }

    /**
     * logout
     * user logout of the system
     * @return result
     */
    public static Result logout() {
        /* Clear old session data. */
        session().clear();

        return redirect(routes.Application.index());
    }

    /**
     * user
     * render user profile
     * @return result
     */
    public static Result user() {
        User existingUser;
        Form<User> data;

        /* Check if user is logged in. */
        if ((session().get("user_email") == null) || (session().get("user_email") == ""))
            return redirect(routes.Application.index());

        /* Get the existing user info and fill the form. */
        existingUser = User.find.byId(session().get("user_email"));
        data = Form.form(User.class).fill(existingUser);

        /* Display it. */
        return ok(user.render(data));
    }
    
    /**
     * addItem
     * load add item page
     * @return result
     */
    public static Result addItem() {
        /* If there's an item referrer, remove it. */
        if (session("item_referrer") != null)
            session().remove("item_referrer");

        /* Check if user is logged in. */
        if ((session().get("user_email") == null) || (session().get("user_email") == ""))
        	 return redirect(routes.Application.index());

        /* Display it. */
        return ok(addItem.render(Form.form(Item.class), false));
    }

    /**
     * searchItem
     * load item(s) list
     * @return result
     */
    public static Result searchItem() {

    	//need work here
    	
        /* Display it. */
        //return ok(serachItem.render(Form.form(Item.class), false));
    	return redirect(routes.Application.index());
    }

    
    /**
     * deleteItem
     * delete item
     * @return result
     */
    public static Result deleteItem() {
    	Item existingItem = Item.find.byId(Long.valueOf(session("item_referrer")));
        
        /* Delete the old item. */
        existingItem.delete();    

        /* Display success to user that their item has been deleted. */
        flash("additem_status", "Successfully Delete!");
		return redirect(routes.Application.index());
    }

    
    /**
     * newItem
     * create new item in database
     * @return result
     */
    public static Result newItem() {
        Item newItem = null;

        /* Get data from HTML form. */
        Form<Item> data = Form.form(Item.class).bindFromRequest();

        /* Error checking. */
        if (data.hasErrors()) {
            flash("additem_status", "Please enter valid data into all fields.");

            if (session("item_referrer") == null)
                return badRequest(addItem.render(data, false));
            else
                return badRequest(addItem.render(data, true));
        }

        /* Get data from form. */
        newItem = data.get();

        /* If there is an item referrer, delete the reference to the old item, since
           items are immutable. */
        if (session("item_referrer") != null) {
            /* Get value of the item referrer. */
            Long id = Long.valueOf(session("item_referrer"));

            /* Get the old item. */
            Item oldItem = Item.find.byId(id);

            /* Delete the old item. */
            oldItem.delete();
        }

        /* Save new item. */
        newItem.save();

        /* Display success to user and redirect to item info page. */
        if (session("item_referrer") == null) {
            flash("additem_status", "Item added to catalog, thank you!");
        } else {
            flash("additem_status", "Item updated, thank you!");

            /* Unset the item referrer. */
            session().remove("item_referrer");
        }

        return redirect(routes.Application.viewItem(newItem.id));
    }

    /**
     * viewItem
     * view item info
     * @return result
     */
    public static Result viewItem(Long id) {
        Form<Item> data;

        /* Get item info. */
        Item existingItem = Item.find.byId(id);

        /* Check if the item exists. */
        if (existingItem == null) {
            flash("additem_status", "That item does not exist!");
            return redirect(routes.Application.addItem());
        }

        /* Record the item referrer for updating. */
        session("item_referrer", String.valueOf(existingItem.id));

        /* Fill in data for view. */
        data = Form.form(Item.class).fill(existingItem);
        return ok(addItem.render(data, true));
    }
}
