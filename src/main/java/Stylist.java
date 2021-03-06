import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Stylist {
    private String name;
    private int id;
    private String specialty;

    public Stylist(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public static List<Stylist> all() {
        String sql = "SELECT * FROM stylists";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Stylist.class);
        }
    }

    public int getId() {
        return id;
    }

    public static Stylist find(int id) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM stylists WHERE id=:id";
          Stylist stylist = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Stylist.class);
          return stylist;
        }
    }
    @Override
    public boolean equals(Object otherStylist) {
      if (!(otherStylist instanceof Stylist)) {
        return false;
      } else {
        Stylist newStylist = (Stylist) otherStylist;
        return this.getName().equals(newStylist.getName()) &&
                this.getSpecialty().equals(newStylist.getSpecialty());
      }
    }

     public void save() {
       try(Connection con = DB.sql2o.open()) {
         String sql = "INSERT INTO stylists (name,specialty) VALUES (:name,:specialty)";
         this.id = (int) con.createQuery(sql, true)
           .addParameter("name", this.name)
           .addParameter("specialty", this.specialty)
           .executeUpdate()
           .getKey();
       }
     }
}
