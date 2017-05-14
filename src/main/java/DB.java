// import org.sql2o.*;
//
// public class DB {
//   public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon", "bubbles", "enter");
// }
// CODE ABOVE ALLOWS USER TO RUN THE APPLICATION LOCALLY





// CODE BELOW ALLOWS THE APPLICATION TO RUN ON HEROKU
import org.sql2o.*;

public class DB {
  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-23-21-220-167.compute-1.amazonaws.com:5432/d11oo1pign6kj7", "beyhnumpkawopw", "b04614dba5b13737d90cf05f1034502b7e2ee463de000d1a9f1afacf2bae8189");
}
