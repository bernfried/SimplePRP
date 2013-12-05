package de.webertise.simpleprp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.webertise.simpleprp.model.Client;
import de.webertise.simpleprp.model.Currency;
import de.webertise.simpleprp.model.Customer;
import de.webertise.simpleprp.model.Module;
import de.webertise.simpleprp.model.Project;
import de.webertise.simpleprp.model.ResourceReservation;
import de.webertise.simpleprp.model.ResourceRole;
import de.webertise.simpleprp.model.Role;
import de.webertise.simpleprp.model.User;
import de.webertise.simpleprp.service.ClientService;
import de.webertise.simpleprp.service.CurrencyService;
import de.webertise.simpleprp.service.CustomerService;
import de.webertise.simpleprp.service.ModuleService;
import de.webertise.simpleprp.service.ProjectService;
import de.webertise.simpleprp.service.ResourceReservationService;
import de.webertise.simpleprp.service.ResourceRoleService;
import de.webertise.simpleprp.service.RoleService;
import de.webertise.simpleprp.service.TestDataService;
import de.webertise.simpleprp.service.UserService;

@Service("testDataService")
public class TestDataServiceImpl implements TestDataService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ResourceRoleService resourceRoleService;

    @Autowired
    private ResourceReservationService resourceReservationService;

    @Autowired
    private CurrencyService currencyService;

    @Override
    public List<String> generate(int amountUsers) {

        // calculate amounts
        int amountProjects = (int) amountUsers / 5;
        int amountModules = amountProjects * 10;
        int amountRoles = 5;
        int amountResRoles = 5;
        int amountClients = 5;
        int amountResourceReservations = 20;

        // result string
        List<String> result = new ArrayList<String>();

        // generate roles
        List<Role> roles = new ArrayList<Role>();
        for (int i = 0; i < amountRoles; i++) {
            Role role = new Role("ROLE_USER" + i, "ROLE_USER" + i + " description");
            role.setCreatedAt(new Date());
            role.setCreatedBy("testdata-generator");
            role.setChangedAt(new Date());
            role.setChangedBy("testdata-generator");
            roles.add(roleService.save(role));
            result.add("Generated: " + role.toString());
        }

        // generate resource roles
        List<ResourceRole> resRoles = new ArrayList<ResourceRole>();
        for (int i = 0; i < amountResRoles; i++) {
            ResourceRole resRole = new ResourceRole("ResourceRole" + i);
            resRole.setCreatedAt(new Date());
            resRole.setCreatedBy("testdata-generator");
            resRole.setChangedAt(new Date());
            resRole.setChangedBy("testdata-generator");
            resRoles.add(resourceRoleService.save(resRole));
            result.add("Generated: " + resRole.toString());
        }

        // generate clients
        List<Client> clients = new ArrayList<Client>();
        for (int i = 0; i < amountClients; i++) {
            Client client = new Client("Client" + i);
            client.setCreatedAt(new Date());
            client.setCreatedBy("testdata-generator");
            client.setChangedAt(new Date());
            client.setChangedBy("testdata-generator");
            clients.add(clientService.save(client));
            result.add("Generated: " + client.toString());
        }

        // generate customer
        Customer customer = new Customer("Customer 1");
        customer.setCreatedAt(new Date());
        customer.setCreatedBy("testdata-generator");
        customer.setChangedAt(new Date());
        customer.setChangedBy("testdata-generator");
        customer = customerService.save(customer);

        // generate currency
        Currency currency = new Currency("Euro");
        currency.setToEuroRate(1);
        currency.setCreatedAt(new Date());
        currency.setCreatedBy("testdata-generator");
        currency.setChangedAt(new Date());
        currency.setChangedBy("testdata-generator");
        currency = currencyService.save(currency);

        // generate projects
        List<Project> projects = new ArrayList<Project>();
        for (int i = 0; i < amountProjects; i++) {
            Project project = new Project("Project" + i);
            project.setDescription("Project Description" + i);
            project.setAccount("Account" + i);
            project.setBudget(10000 + i);
            project.setCreatedAt(new Date());
            project.setCreatedBy("testdata-generator");
            project.setChangedAt(new Date());
            project.setChangedBy("testdata-generator");
            // add currency
            project.setCurrency(currency);
            // add customer
            project.setCustomer(customer);
            // save project
            projects.add(projectService.save(project));
            result.add("Generated: " + project.toString());
        }

        // generate modules
        List<Module> modules = new ArrayList<Module>();
        for (int i = 0; i < amountModules; i++) {
            Module module = new Module("Module" + i);
            module.setDescription("Module Description" + i);
            module.setCreatedAt(new Date());
            module.setCreatedBy("testdata-generator");
            module.setChangedAt(new Date());
            module.setChangedBy("testdata-generator");
            // assign to project
            int rdmIndex = (int) Math.floor((Math.random() * amountProjects));
            module.setProject(projects.get(rdmIndex));
            // save module
            modules.add(moduleService.save(module));
            result.add("Generated: " + module.toString());
        }

        // generate users with relationships to roles, clients
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < amountUsers; i++) {
            User user = new User();
            user.setFirstName("Hans" + i);
            user.setLastName("Meier" + i);
            user.setEmail("hans.meier." + i + "@me.com");
            user.setLogin("HansMeier" + i);
            user.setPlainPassword("password" + i);
            user.setCreatedAt(new Date());
            user.setCreatedBy("testdata-generator");
            user.setChangedAt(new Date());
            user.setChangedBy("testdata-generator");

            // add roles
            int rdmIndex = (int) Math.floor((Math.random() * amountRoles));
            user.addAuthority(roles.get(rdmIndex));
            rdmIndex = (int) Math.floor((Math.random() * amountRoles));
            user.addAuthority(roles.get(rdmIndex));

            // add clients
            rdmIndex = (int) Math.floor((Math.random() * amountClients));
            user.addClient(clients.get(rdmIndex));

            // add modules and projects as members
            rdmIndex = (int) Math.floor((Math.random() * amountModules));
            user.addProjectAsMember(modules.get(rdmIndex).getProject());
            rdmIndex = (int) Math.floor((Math.random() * amountModules));
            user.addProjectAsMember(modules.get(rdmIndex).getProject());
            rdmIndex = (int) Math.floor((Math.random() * amountModules));
            user.addProjectAsMember(modules.get(rdmIndex).getProject());

            // add projects as prjmgr/admin only to first user
            if (i == 0) {
                for (int i1 = 0; i1 < amountProjects; i1++) {
                    user.addProjectAsPrjMgr(projects.get(i1));
                    user.addProjectAsAdmin(projects.get(i1));
                }
            }

            // add resource roles
            rdmIndex = (int) Math.floor((Math.random() * amountResRoles));
            user.addResourceRole(resRoles.get(rdmIndex));
            rdmIndex = (int) Math.floor((Math.random() * amountResRoles));
            user.addResourceRole(resRoles.get(rdmIndex));

            // save user
            userService.save(user);
            users.add(user);
            result.add("Generated: " + user.toString());
        }

        // generate resource reservation
        List<ResourceReservation> resReservations = new ArrayList<ResourceReservation>();
        for (int i = 0; i < amountResourceReservations; i++) {
            ResourceReservation resReservation = new ResourceReservation("ResourceReservation" + i);
            resReservation.setCreatedAt(new Date());
            resReservation.setCreatedBy("testdata-generator");
            resReservation.setChangedAt(new Date());
            resReservation.setChangedBy("testdata-generator");
            resReservation.setCalYearWeek("53");
            resReservation.setDay(new Date());

            // assign user or resourceRole to resource reservation
            int rdmIndex = 0;
            if (i % 2 == 0) {
                // set user
                rdmIndex = (int) Math.floor((Math.random() * amountUsers));
                resReservation.setUser(users.get(rdmIndex));
            } else {
                // set resource role
                rdmIndex = (int) Math.floor((Math.random() * amountResRoles));
                resReservation.setResourceRole(resRoles.get(rdmIndex));
            }

            // set module
            rdmIndex = (int) Math.floor((Math.random() * amountModules));
            resReservation.setModule(modules.get(rdmIndex));

            resReservations.add(resourceReservationService.save(resReservation));
            result.add("Generated: " + resReservation.toString());
        }

        return result;
    }

}
