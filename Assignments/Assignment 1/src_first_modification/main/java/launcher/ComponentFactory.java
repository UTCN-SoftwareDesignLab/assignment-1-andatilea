package launcher;
import controller.AdministratorController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;
import view.AdministratorView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final LoginView loginView;
    private final AdministratorView administratorView;
    private final EmployeeView employeeView;

    private final LoginController loginController;
    private final AdministratorController adminController;
    private final EmployeeController employeeController;

    private final AuthenticationService authenticationService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final UserService userService;

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepositoryMySQL clientRepositoryMySQL;
    private final AccountRepositoryMySQL accountRepositoryMySQL;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientService = new ClientServiceMySQL(clientRepository);
        this.accountService = new AccountServiceMySQL();
        this.userService = new UserServiceMySQL(userRepository, rightsRolesRepository);
        this.loginView = new LoginView();
        this.administratorView = new AdministratorView();
        this.employeeView = new EmployeeView();
        this.loginController = new LoginController(loginView, authenticationService, administratorView, employeeView);
        this.adminController = new AdministratorController(administratorView, userService);
        this.employeeController = new EmployeeController(employeeView, clientService, accountService);

        this.clientRepositoryMySQL = new ClientRepositoryMySQL(connection);
        this.accountRepositoryMySQL = new AccountRepositoryMySQL(connection);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }


    public AdministratorView getAdministratorView(){
        return administratorView;
    }

    public EmployeeView getEmployeeView(){
        return employeeView;
    }

    public ClientRepositoryMySQL getClientRepositoryMySQL() {
        return clientRepositoryMySQL;
    }

    public ClientService getClientService() { return clientService;}

    public AccountRepositoryMySQL getAccountRepositoryMySQL() {
        return accountRepositoryMySQL;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AdministratorController getAdminController() {
        return adminController;
    }

    public EmployeeController getEmployeeController(){ return employeeController;}
}
