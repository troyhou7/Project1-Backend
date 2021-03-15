package dev.houston.services;

import dev.houston.entities.Expense;

import java.util.Set;

public interface ExpenseService {
    Expense writeExpense(Expense expense);

    Set<Expense> getAllExpenses();
    Set<Expense> getExpensesByMinerId(int id);
    Expense getExpenseById(int id);

    Expense updateExpense(Expense expense);

    boolean deleteExpenseById(int id);
}
