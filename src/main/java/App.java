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
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("template","templates/index.vtl");
        return new ModelAndView(model,layout);
        }, new VelocityTemplateEngine());

        get("/clients", (request, response)-> {
        Map<String, Object> model = new HashMap<String , Object>();
        model.put("clients",Client.all());
        model.put("template","templates/clients.vtl");
        return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/clients", (request,response)-> {
            Map<String, Object> model = new HashMap<String , Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
            String name = request.queryParams("name");
            Client newClient = new Client(name,stylist.getId());
            newClient.save();
            model.put("stylist", stylist);
            model.put("template","templates/client-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        // get method for the add new stylist
        get("/stylists", (request, response) -> {
            Map<String, Object> model = new HashMap<String , Object>();
            model.put("stylists", Stylist.all());
            model.put("template","templates/add-stylist.vtl");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());

        //   post method for the add stylist form
          post("/stylists",(request, response) -> {
              Map<String , Object> model = new HashMap<String , Object>();
              String name = request.queryParams("name");
              String specialty = request.queryParams("specialty");
              Stylist newStylist = new Stylist(name,specialty);
              newStylist.save();
              model.put("template","templates/stylist-success.vtl");
              return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());

            get("clients/:id", (request, response)-> {
                Map<String, Object> model = new HashMap<String,Object>();
                Client client = Client.find(Integer.parseInt(request.params(":id")));
                model.put("client",client);
                model.put("template","templates/clients.vtl");return new ModelAndView(model, layout);
            },new VelocityTemplateEngine());

            get("/stylist/:id", (request, response)-> {
                Map<String, Object> model = new HashMap<String,Object>();
                Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
                model.put("stylist",stylist);
                model.put("template", "templates/stylists.vtl");
                return new ModelAndView(model, layout);
              }, new VelocityTemplateEngine());

             post("/stylists/:stylist_id/clients/:id/delete" , (request, response)-> {
                 Map<String,Object> model = new HashMap<String ,Object>();
                 Client client = Client.find(Integer.parseInt(request.params(":id")));
                 Stylist stylist = Stylist.find(client.getStylistId());
                 client.delete();
                 model.put("stylist", stylist);
                 model.put("template", "templates/stylists.vtl");
                return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());

    }

}
