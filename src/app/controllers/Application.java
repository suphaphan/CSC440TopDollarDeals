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
     * @return result of user
     */
    public static Result doLogin(Form<User> data) {
        User potentialUser = data.get();
        User user = User.find.byId(potentialUser.email);

        /* Check if correct password was supplied. */
        if (!user.isPassword(potentialUser.password)) {
            flash("login_status", "Invalid username or password specified!");
            return badRequest(login.render(data, Form.form(User.class)));
        }

        session().clear();
        session("user_email", user.email);


        if ((flash("login_status") == "") || (flash("login_status") == null))
            flash("login_status", "Successfully logged in!");
        else
            flash("login_status", "Successfully registered!");

        return redirect(routes.Application.user());
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

        flash("login_status", "Successfully registered!");

        return doLogin(data);
    }

    /**
     * updateUser
     * this is called then user hit update button
     * @return result
     */
    public static Result updateUser(){
    	User existingUser = null;
    	 /* Get data from HTML form. */
        Form<User> data = Form.form(User.class).bindFromRequest();

        /* Error checking. */
        if (data.hasErrors())
            return badRequest(login.render(Form.form(User.class), data));

        /* Get data from form. */
        existingUser = data.get();

        /* Change user password and save to database. */
        existingUser.changePassword(existingUser.password);
        existingUser.changeName(existingUser.name);
        existingUser.save();
		return user();
    }

    /**
     * logout
     * user logout of the system
     * @return result
     */
    public static Result logout() {
        session().clear();
        return redirect(routes.Application.index());
    }

    /**
     * user
     * render user profile
     * @return result
     */
    public static Result user() {
        return ok(user.render(Form.form(User.class)));
    }

}
