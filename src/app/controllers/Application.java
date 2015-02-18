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
        return ok(login.render(Form.form(User.class)));
    }

    public static Result authenticate() {
    	return internalServerError("Not implemented yet");
    }

    public static Result register() {
        return internalServerError("Not implemented yet");
    }

}
