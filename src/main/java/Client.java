import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Client {
    private String name;
    private int id;


    public Client(String name, int stylistId) {
    this.name = name;
    this.stylistId = stylistId;
    }

    public String getName() {
        return name;
    }
    public int getStylistId() {
        return stylistId;
    }
    public static List<Client> all() {
        String sql = "SELECT  id , name, stylistId FROM clients";
            try(Connection con = DB.sql2o.open()) {
                return con.createQuery(sql).executeAndFetch(Client.class);
            }
    }
    @Override
    public boolean equals(Object otherClient) {
        if (!(otherClient instanceof Client)) {
            return false;
        } else {
            Client newClient = (Client) otherClient;
            return this.getName().equals(newClient.getName()) && this.getId() == newClient.getId() && this.getStylistId();
        }
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT *FROM clients where id=:id";
            Client client = con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(Client.class);
            return client;
        }
    }

}
