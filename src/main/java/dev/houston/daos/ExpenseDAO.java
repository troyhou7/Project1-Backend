package dev.houston.daos;

import dev.houston.entities.Expense;

import java.util.Set;

public interface ExpenseDAO {
    // CRUD

    Expense createExpense(Expense expense);

    Set<Expense> getAllExpenses();
    Expense getExpenseById(int id);

    Expense updateExpense(Expense expense);

    boolean deleteExpenseById(int id);
}
