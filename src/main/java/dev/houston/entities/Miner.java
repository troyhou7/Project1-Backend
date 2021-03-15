package dev.houston.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "miner")
public class Miner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "miner_id")
    private int minerId;

    @Column(name = "first_name")
    private String fname;

    @Column(name = "last_name")
    private String lname;

    @Column(name = "username")
    private String username;

    @Column(name = "pass")
    private String pass;

    @Column(name = "foreman")
    private boolean foreman;

    @OneToMany(mappedBy = "expenseId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Expense> expenses = new HashSet<>();


    public Miner(){}

    public Miner(int minerId, String fname, String lname, String username, String pass, boolean foreman) {
        this.minerId = minerId;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.pass = pass;
        this.foreman = foreman;
    }

    public int getMinerId() {
        return minerId;
    }

    public void setMinerId(int minerId) {
        this.minerId = minerId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isForeman() {
        return foreman;
    }

    public void setForeman(boolean foreman) {
        this.foreman = foreman;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return "Miner{" +
                "minerId=" + minerId +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", foreman=" + foreman +
                ", expenses=" + expenses +
                '}';
    }
}
