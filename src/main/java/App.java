import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
  ProcessBuilder processBuilder = new ProcessBuilder();
  if (processBuilder.environment().get("PORT") != null) {
    return Integer.parseInt(processBuilder.environment().get("PORT"));
  }
  return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
      staticFileLocation("/public");
      String layout = "templates/layout.vtl";

      port(getHerokuAssignedPort());
      staticFileLocation("/public");

        get("/", (request, response)-> {
        Map<String, Object>model = new HashMap<String, Object>();
        model.put("template","templates/index.vtl");
        return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

        get("/clients", (request, response)-> {
        Map<String, Object> model = new HashMap<String , Object>();
        model.put("clients",Client.all());
        model.put("template","templates/clients.vtl");
        return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/clients")

    }

}
