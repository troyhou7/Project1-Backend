package dev.houston.entities;

import dev.houston.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseId;


    @JoinColumn(name = "m_id")
    @Column(name = "m_id")
    private int minerId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "date_submitted")
    private long dateSubmitted;

    @Column(name = "date_resolved")
    private long dateResolved;

    @Column(name = "resolved_reason")
    private String resolvedReason = "";

    public Expense(){}
    public Expense(int expenseId, int minerId, double amount, String description, Status status, long dateSubmitted, long dateResolved) {
        this.expenseId = expenseId;
        this.minerId = minerId;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateResolved = dateResolved;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getMinerId() {
        return minerId;
    }

    public void setMinerId(int minerId) {
        this.minerId = minerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(long dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public long getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(long dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getResolvedReason() {
        return resolvedReason;
    }

    public void setResolvedReason(String resolvedReason) {
        this.resolvedReason = resolvedReason;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", minerId=" + minerId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status=" + status.name() +
                ", dateSubmitted=" + dateSubmitted +
                ", dateResolved=" + dateResolved +
                ", resolvedReason='" + resolvedReason + '\'' +
                '}';
    }
}
