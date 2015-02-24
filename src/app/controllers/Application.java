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
        if (!user.isPassword(potentialUser.password))
            return badRequest(login.render(data, Form.form(User.class)));

        session().clear();
        session("user_email", user.email);
        return redirect(routes.Application.user());
    }

    public static Result authenticate() {
        Form<User> data = Form.form(User.class).bindFromRequest();

        if (data.hasErrors())
            return badRequest(login.render(data, Form.form(User.class)));
        else
            return doLogin(data);
    }

    /* This is called for registering users. */
    public static Result register() {
        User newUser = null;
        User existingUser = null;

        /* Get data from HTML form. */
        Form<User> data = Form.form(User.class).bindFromRequest();

        /* Error checking. */
        if (data.hasErrors())
            return badRequest(login.render(Form.form(User.class), data));

        /* Get data from form. */
        newUser = data.get();

        /* Check to see if there's already a user for this email. */
        if (User.find.byId(newUser.email) != null)
            return badRequest(login.render(Form.form(User.class), data));

        /* Change user password and save to database. */
        newUser.changePassword(newUser.password);
        newUser.save();

        return doLogin(data);
    }

    public static Result logout() {
        session().clear();
        return redirect(routes.Application.index());
    }

    public static Result user() {
        return ok(user.render(Form.form(User.class)));
    }

}
