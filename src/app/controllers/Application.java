package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;
import models.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result login() {
        if (session("user_email") == null)
            return ok(login.render(Form.form(User.class), Form.form(User.class)));
        else
            return redirect(routes.Application.index());
    }

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

    public static Result authenticate() {
        Form<User> data = Form.form(User.class).bindFromRequest();

        if (data.hasErrors()) {
            flash("login_status", "Please enter data into all fields in the Login form.");
            return badRequest(login.render(data, Form.form(User.class)));
        } else {
            return doLogin(data);
        }
    }

    /* This is called for registering users. */
    public static Result register() {
        User newUser = null;
        User existingUser = null;

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

    /*this is called then user hit update button*/
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

    public static Result logout() {
        session().clear();
        return redirect(routes.Application.index());
    }

    public static Result user() {
        return ok(user.render(Form.form(User.class)));
    }

}
