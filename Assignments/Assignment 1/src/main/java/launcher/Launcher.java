package launcher;

import database.Boostraper;
import java.sql.SQLException;

public class Launcher {

    public static boolean BOOTSTRAP = true;

    public static void main(String[] args) {

        bootstrap();
        ComponentFactory componentFactory = ComponentFactory.instance(false);


        componentFactory.getLoginView().setVisible();
       //if(componentFactory.getLoginView().chooseUser().equals("A")){
         //   componentFactory.getAdministratorView().setVisible();
        //}
        //else if (componentFactory.getLoginView().chooseUser().equals("E")){
            //componentFactory.getEmployeeView().setVisible(); }

        //else {componentFactory.getLoginView().setVisible();
        //System.out.println("am ajuns aici");}


    }

    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Boostraper().execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
