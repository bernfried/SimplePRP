package de.webertise.simpleprp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }), @UniqueConstraint(columnNames = { "email" }) })
@XmlRootElement
public class User extends AbstractEntityObject {

    // *******************************************************
    // * Constants
    // *******************************************************
    public final static String RELATION_TYPE_MEMBER = "member";
    public final static String RELATION_TYPE_PRJMGR = "prjmgr";
    public final static String RELATION_TYPE_ADMIN = "admin";

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************
    @Column(name = "LOGIN", nullable = false, length = 20)
    private String login;

    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    private String firstName;

    @Column(name = "LASTNAME", length = 100)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "FAILED_LOGIN_ATTEMPTS")
    private int failedLoginAttempts;

    @Column(nullable = false, name = "ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;

    @Column(nullable = false, name = "ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;

    @Column(nullable = false, name = "CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired = true;

    @Column(nullable = false, name = "ENABLED")
    private boolean enabled = true;

    @Column(nullable = false, name = "SALT", length = 100)
    private String salt;

    // *******************************************************
    // * Relationships
    // *******************************************************
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PRP_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
    private Set<Role> authorities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PRP_USER_CLIENT", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID") })
    private Set<Client> clients;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PRP_PROJECT_MEMBER", joinColumns = { @JoinColumn(name = "MEMBER_USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID") })
    private Set<Project> projectsAsMember;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PRP_PROJECT_PRJMGR", joinColumns = { @JoinColumn(name = "PRJMGR_USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID") })
    private Set<Project> projectsAsPrjMgr;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PRP_PROJECT_ADMIN", joinColumns = { @JoinColumn(name = "ADMIN_USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID") })
    private Set<Project> projectsAsAdmin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PRP_USER_RESOURCEROLE", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "RESOURCEROLE_ID", referencedColumnName = "ID") })
    private Set<ResourceRole> resourceRoles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<ResourceReservation> resourceReservations;

    // *******************************************************
    // * Transient fields
    // *******************************************************
    @Transient
    private String plainPassword;

    // *******************************************************
    // * Constructors
    // *******************************************************
    public User() {
        this.setChangedAt(new Date());
        this.setCreatedAt(new Date());
        this.setChangedBy("anonymous");
        this.setCreatedBy("anonymous");
        this.setAccountNonExpired(true);
        this.setAccountNonLocked(true);
        this.setCredentialsNonExpired(false);
        this.setEnabled(false);
        this.setPassword("");
    }

    // *******************************************************
    // * Getter & Setter
    // *******************************************************

    /**
     * @return the login
     */
    @XmlElement
    public String getLogin() {
        return login;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the email
     */
    @XmlElement
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    @XmlTransient
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    @XmlElement
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the failedLoginAttempts
     */
    @XmlElement
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    /**
     * @param failedLoginAttempts
     *            the failedLoginAttempts to set
     */
    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    /**
     * @return the accountNonExpired
     */
    @XmlElement
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * @param accountNonExpired
     *            the accountNonExpired to set
     */
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * @return the accountNonLocked
     */
    @XmlElement
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * @param accountNonLocked
     *            the accountNonLocked to set
     */
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * @return the credentialsNonExpired
     */
    @XmlElement
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * @param credentialsNonExpired
     *            the credentialsNonExpired to set
     */
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    /**
     * @return the enabled
     */
    @XmlElement
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the salt
     */
    @XmlTransient
    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     *            the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the plainPassword
     */
    @XmlTransient
    @JsonIgnore
    public String getPlainPassword() {
        return plainPassword;
    }

    /**
     * @param plainPassword
     *            the plainPassword to set
     */
    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    /**
     * @return the roles
     */
    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    public Set<Role> getAuthorities() {
        return authorities;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setAuthorities(Set<Role> roles) {
        this.authorities = roles;
    }

    /**
     * @return the clients
     */
    @XmlElementWrapper(name = "clients")
    @XmlElement(name = "client")
    public Set<Client> getClients() {
        return this.clients;
    }

    /**
     * @param clients
     *            the clients to set
     */
    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    /**
     * @return the projectsAsMember
     */
    @XmlElementWrapper(name = "projectsasmember")
    @XmlElement(name = "projectasmember")
    public Set<Project> getProjectsAsMember() {
        return this.projectsAsMember;
    }

    /**
     * @param projectsAsMember
     *            the projectsAsMember to set
     */
    public void setProjectsAsMember(Set<Project> projectsAsMember) {
        this.projectsAsMember = projectsAsMember;
    }

    /**
     * @return the projectsAsPrjMgr
     */
    @XmlElementWrapper(name = "projectsasprjmgr")
    @XmlElement(name = "projectasprjmgr")
    public Set<Project> getProjectsAsPrjMgr() {
        return this.projectsAsPrjMgr;
    }

    /**
     * @param projectsAsPrjMgr
     *            the projectsAsPrjMgr to set
     */
    public void setProjectsAsPrjMgr(Set<Project> projectsAsPrjMgr) {
        this.projectsAsPrjMgr = projectsAsPrjMgr;
    }

    /**
     * @return the projectsAsAdmin
     */
    @XmlElementWrapper(name = "projectsasadmin")
    @XmlElement(name = "projectasadmin")
    public Set<Project> getProjectsAsAdmin() {
        return this.projectsAsAdmin;
    }

    /**
     * @param projectsAsAdmin
     *            the projectsAsAdmin to set
     */
    public void setProjectsAsAdmin(Set<Project> projectsAsAdmin) {
        this.projectsAsAdmin = projectsAsAdmin;
    }

    /**
     * @return the resourceRoles
     */
    @XmlElementWrapper(name = "resourceroles")
    @XmlElement(name = "resourcerole")
    public Set<ResourceRole> getResourceRoles() {
        return this.resourceRoles;
    }

    /**
     * @param resourceRoles
     *            the resourceRoles to set
     */
    public void setResourceRoles(Set<ResourceRole> resourceRoles) {
        this.resourceRoles = resourceRoles;
    }

    /**
     * @return the resourceReservations
     */
    @XmlElementWrapper(name = "resourcereservations")
    @XmlElement(name = "resourcereservation")
    public Set<ResourceReservation> getResourceReservations() {
        return this.resourceReservations;
    }

    /**
     * @param resourceReservations
     *            the resourceReservations to set
     */
    public void setResourceReservations(Set<ResourceReservation> resourceReservations) {
        this.resourceReservations = resourceReservations;
    }

    // *******************************************************
    // * Custom Methods
    // *******************************************************

    /**
     * Adds a role to the authorities set
     * 
     * @param role
     */
    public void addAuthority(Role role) {
        if (this.authorities == null) {
            this.authorities = new HashSet<Role>();
        }
        if (!this.authorities.contains(role)) {
            this.authorities.add(role);
        }
    }

    /**
     * Adds a client to the clients set
     * 
     * @param client
     */
    public void addClient(Client client) {
        if (this.clients == null) {
            this.clients = new HashSet<Client>();
        }
        if (!this.clients.contains(client)) {
            this.clients.add(client);
        }
    }

    /**
     * Adds a project as member to the projects set
     * 
     * @param project
     */
    public void addProjectAsMember(Project project) {
        if (this.projectsAsMember == null) {
            this.projectsAsMember = new HashSet<Project>();
        }
        if (!this.projectsAsMember.contains(project)) {
            this.projectsAsMember.add(project);
        }
    }

    /**
     * Adds a project as prjmgr to the projects set
     * 
     * @param project
     */
    public void addProjectAsPrjMgr(Project project) {
        if (this.projectsAsPrjMgr == null) {
            this.projectsAsPrjMgr = new HashSet<Project>();
        }
        if (!this.projectsAsPrjMgr.contains(project)) {
            this.projectsAsPrjMgr.add(project);
        }
    }

    /**
     * Adds a project as admin to the projects set
     * 
     * @param project
     */
    public void addProjectAsAdmin(Project project) {
        if (this.projectsAsAdmin == null) {
            this.projectsAsAdmin = new HashSet<Project>();
        }
        if (!this.projectsAsAdmin.contains(project)) {
            this.projectsAsAdmin.add(project);
        }
    }

    /**
     * Adds a resource role to the resource role set
     * 
     * @param client
     */
    public void addResourceRole(ResourceRole resourceRole) {
        if (this.resourceRoles == null) {
            this.resourceRoles = new HashSet<ResourceRole>();
        }
        if (!this.resourceRoles.contains(resourceRole)) {
            this.resourceRoles.add(resourceRole);
        }
    }

    /**
     * Adds a resource role to the resource role set
     * 
     * @param client
     */
    public void addResourceReservation(ResourceReservation resourceReservation) {
        if (this.resourceReservations == null) {
            this.resourceReservations = new HashSet<ResourceReservation>();
        }
        if (!this.resourceReservations.contains(resourceReservation)) {
            this.resourceReservations.add(resourceReservation);
        }
    }

    // *******************************************************
    // * General Methods
    // *******************************************************

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.accountNonExpired ? 1231 : 1237);
        result = prime * result + (this.accountNonLocked ? 1231 : 1237);
        result = prime * result + (this.credentialsNonExpired ? 1231 : 1237);
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + (this.enabled ? 1231 : 1237);
        result = prime * result + this.failedLoginAttempts;
        result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        result = prime * result + ((this.login == null) ? 0 : this.login.hashCode());
        result = prime * result + ((this.resourceRoles == null) ? 0 : this.resourceRoles.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (this.accountNonExpired != other.accountNonExpired)
            return false;
        if (this.accountNonLocked != other.accountNonLocked)
            return false;
        if (this.credentialsNonExpired != other.credentialsNonExpired)
            return false;
        if (this.email == null) {
            if (other.email != null)
                return false;
        } else if (!this.email.equals(other.email))
            return false;
        if (this.enabled != other.enabled)
            return false;
        if (this.failedLoginAttempts != other.failedLoginAttempts)
            return false;
        if (this.firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!this.firstName.equals(other.firstName))
            return false;
        if (this.lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!this.lastName.equals(other.lastName))
            return false;
        if (this.login == null) {
            if (other.login != null)
                return false;
        } else if (!this.login.equals(other.login))
            return false;
        if (this.resourceRoles == null) {
            if (other.resourceRoles != null)
                return false;
        } else if (!this.resourceRoles.equals(other.resourceRoles))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "User [login=" + this.login + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", failedLoginAttempts=" + this.failedLoginAttempts
                + ", accountNonExpired=" + this.accountNonExpired + ", accountNonLocked=" + this.accountNonLocked + ", credentialsNonExpired=" + this.credentialsNonExpired + ", enabled="
                + this.enabled + ", toString()=" + super.toString() + "]";
    }

}
