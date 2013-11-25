package de.webertise.simpleprp.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.webertise.simpleprp.model.general.AbstractEntityObject;

@Entity
@Table(name = "PRP_PROJECT")
@XmlRootElement(name = "project")
public class Project extends AbstractEntityObject {

    // *******************************************************
    // * Constants
    // *******************************************************
    public final static int STATUS_PLANNED = 1;
    public final static int STATUS_STARTED = 2;
    public final static int STATUS_ON_HOLD = 3;
    public final static int STATUS_FINISHED = 4;

    // *******************************************************
    // * Specific Entity Properties
    // *******************************************************

    @Basic(optional = false)
    @Column(name = "NAME", unique = true, nullable = false, length = 100)
    private String name;

    @Basic(optional = true)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Basic(optional = false)
    @Column(name = "STATUS")
    private int status;

    @Basic(optional = true)
    @Column(name = "STARTDATE")
    private Date startDate;

    @Basic(optional = true)
    @Column(name = "ENDDATE")
    private Date endDate;

    @Basic(optional = true)
    @Column(name = "BUDGET")
    private int budget;

    @Basic(optional = true)
    @Column(name = "ACCOUNT", length = 100)
    private String account;

    @Basic(optional = false)
    @Column(name = "STDRATEPERHOUR")
    private float stdRatePerHour;

    @Basic(optional = false)
    @Column(name = "STDHOURSPERDAY")
    private int stdHoursPerDay;

    // *******************************************************
    // * Relationships
    // *******************************************************
    @OneToMany(mappedBy = "project")
    private Set<Module> modules;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    protected Customer customer;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_ID", nullable = false)
    protected Currency currency;

    @ManyToMany
    @JoinTable(name = "PRP_PROJECT_MEMBER", joinColumns = { @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "MEMBER_USER_ID", referencedColumnName = "ID") })
    private Set<User> members;

    @ManyToMany
    @JoinTable(name = "PRP_PROJECT_ADMIN", joinColumns = { @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ADMIN_USER_ID", referencedColumnName = "ID") })
    private Set<User> adminUsers;

    @ManyToMany
    @JoinTable(name = "PRP_PROJECT_PRJMGR", joinColumns = { @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "PRJMGR_USER_ID", referencedColumnName = "ID") })
    private Set<User> pmUsers;

    // *************************************************************************
    // * Constructors
    // *************************************************************************

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    // *************************************************************************
    // * Getter & Setter
    // *************************************************************************

    /**
     * @return the name
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    @XmlElement
    public int getStatus() {
        return this.status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the description
     */
    @XmlElement
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startDate
     */
    @XmlElement
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    @XmlElement
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the budget
     */
    @XmlElement
    public int getBudget() {
        return this.budget;
    }

    /**
     * @param budget
     *            the budget to set
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * @return the account
     */
    @XmlElement
    public String getAccount() {
        return this.account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the stdRatePerHour
     */
    @XmlElement
    public float getStdRatePerHour() {
        return this.stdRatePerHour;
    }

    /**
     * @param stdRatePerHour
     *            the stdRatePerHour to set
     */
    public void setStdRatePerHour(float stdRatePerHour) {
        this.stdRatePerHour = stdRatePerHour;
    }

    /**
     * @return the stdHoursPerDay
     */
    @XmlElement
    public int getStdHoursPerDay() {
        return this.stdHoursPerDay;
    }

    /**
     * @param stdHoursPerDay
     *            the stdHoursPerDay to set
     */
    public void setStdHoursPerDay(int stdHoursPerDay) {
        this.stdHoursPerDay = stdHoursPerDay;
    }

    /**
     * @return the currency
     */
    @XmlElement(name = "currency")
    public Currency getCurrency() {
        return this.currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * @return the adminUsers
     */
    @XmlElement(name = "adminusers")
    public Set<User> getAdminUsers() {
        return this.adminUsers;
    }

    /**
     * @param adminUsers
     *            the adminUsers to set
     */
    public void setAdminUsers(Set<User> adminUsers) {
        this.adminUsers = adminUsers;
    }

    /**
     * @return the pmUsers
     */
    @XmlElement(name = "pmusers")
    public Set<User> getPmUsers() {
        return this.pmUsers;
    }

    /**
     * @param pmUsers
     *            the pmUsers to set
     */
    public void setPmUsers(Set<User> pmUsers) {
        this.pmUsers = pmUsers;
    }

    /**
     * @return the modules
     */
    @XmlElement(name = "modules")
    public Set<Module> getModules() {
        return this.modules;
    }

    /**
     * @param modules
     *            the modules to set
     */
    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    /**
     * @return the customer
     */
    @XmlElement(name = "customer")
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the users
     */
    @XmlElement(name = "members")
    public Set<User> getMembers() {
        return this.members;
    }

    /**
     * @param users
     *            the users to set
     */
    public void setMembers(Set<User> members) {
        this.members = members;
    }

    // *************************************************************************
    // * General Methods
    // *************************************************************************

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Project [name=" + this.name + ", description=" + this.description + ", status=" + this.status + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", budget="
                + this.budget + ", account=" + this.account + ", stdRatePerHour=" + this.stdRatePerHour + ", stdHoursPerDay=" + this.stdHoursPerDay + ", modules=" + this.modules + ", customer="
                + this.customer + ", currency=" + this.currency + ", members=" + this.members + ", adminUsers=" + this.adminUsers + ", pmUsers=" + this.pmUsers + ", toString()=" + super.toString()
                + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.account == null) ? 0 : this.account.hashCode());
        result = prime * result + ((this.adminUsers == null) ? 0 : this.adminUsers.hashCode());
        result = prime * result + this.budget;
        result = prime * result + ((this.currency == null) ? 0 : this.currency.hashCode());
        result = prime * result + ((this.customer == null) ? 0 : this.customer.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.endDate == null) ? 0 : this.endDate.hashCode());
        result = prime * result + ((this.members == null) ? 0 : this.members.hashCode());
        result = prime * result + ((this.modules == null) ? 0 : this.modules.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.pmUsers == null) ? 0 : this.pmUsers.hashCode());
        result = prime * result + ((this.startDate == null) ? 0 : this.startDate.hashCode());
        result = prime * result + this.status;
        result = prime * result + this.stdHoursPerDay;
        result = prime * result + Float.floatToIntBits(this.stdRatePerHour);
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
        Project other = (Project) obj;
        if (this.account == null) {
            if (other.account != null)
                return false;
        } else if (!this.account.equals(other.account))
            return false;
        if (this.adminUsers == null) {
            if (other.adminUsers != null)
                return false;
        } else if (!this.adminUsers.equals(other.adminUsers))
            return false;
        if (this.budget != other.budget)
            return false;
        if (this.currency == null) {
            if (other.currency != null)
                return false;
        } else if (!this.currency.equals(other.currency))
            return false;
        if (this.customer == null) {
            if (other.customer != null)
                return false;
        } else if (!this.customer.equals(other.customer))
            return false;
        if (this.description == null) {
            if (other.description != null)
                return false;
        } else if (!this.description.equals(other.description))
            return false;
        if (this.endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!this.endDate.equals(other.endDate))
            return false;
        if (this.members == null) {
            if (other.members != null)
                return false;
        } else if (!this.members.equals(other.members))
            return false;
        if (this.modules == null) {
            if (other.modules != null)
                return false;
        } else if (!this.modules.equals(other.modules))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (this.pmUsers == null) {
            if (other.pmUsers != null)
                return false;
        } else if (!this.pmUsers.equals(other.pmUsers))
            return false;
        if (this.startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!this.startDate.equals(other.startDate))
            return false;
        if (this.status != other.status)
            return false;
        if (this.stdHoursPerDay != other.stdHoursPerDay)
            return false;
        if (Float.floatToIntBits(this.stdRatePerHour) != Float.floatToIntBits(other.stdRatePerHour))
            return false;
        return true;
    }
}
