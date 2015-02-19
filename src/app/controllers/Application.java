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

        if (!user.isPassword(potentialUser.password))
            return badRequest(login.render(data, Form.form(User.class)));

        session().clear();
        session("user_email", user.email);
        return redirect(routes.Application.index());
    }

    public static Result authenticate() {
        Form<User> data = Form.form(User.class).bindFromRequest();

        if (data.hasErrors())
            return badRequest(login.render(data, Form.form(User.class)));
        else
            return doLogin(data);
    }

    public static Result register() {
        User newUser = null;
        User existingUser = null;
        Form<User> data = Form.form(User.class).bindFromRequest();

        if (data.hasErrors())
            return badRequest(login.render(Form.form(User.class), data));

        newUser = data.get();

        if (User.find.byId(newUser.email) != null)
            return badRequest(login.render(Form.form(User.class), data));

        newUser.changePassword(newUser.password);
        newUser.save();

        return doLogin(data);
    }

    public static Result logout() {
        session().clear();
        return redirect(routes.Application.index());
    }

}
