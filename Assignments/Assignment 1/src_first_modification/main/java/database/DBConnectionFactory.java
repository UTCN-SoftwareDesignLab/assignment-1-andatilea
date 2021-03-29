package database;

public class DBConnectionFactory {

    public static JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if (test) {
            return new JDBConnectionWrapper(Constants.Schemas.TEST);
        }
        return new JDBConnectionWrapper(Constants.Schemas.PRODUCTION);
    }

}
