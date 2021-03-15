package dev.houston.daos;

import dev.houston.entities.Expense;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExpenseDaoLocal implements ExpenseDAO{

    private static Map<Integer, Expense> expenseTable = new HashMap<Integer, Expense>();
    private static int idMaker = 0;
    private int id;

    @Override
    public Expense createExpense(Expense expense) {
        expense.setExpenseId(++idMaker);
        expenseTable.put(expense.getExpenseId(), expense);
        return expense;
    }

    @Override
    public Set<Expense> getAllExpenses() {
        Set<Expense> expenses = new HashSet<Expense>(expenseTable.values());
        return expenses;
    }

    @Override
    public Expense getExpenseById(int id) {
        return expenseTable.get(id);
    }

    @Override
    public Expense updateExpense(Expense expense) {
        return expenseTable.put(expense.getExpenseId(), expense);
    }

    @Override
    public boolean deleteExpenseById(int id) {
        Expense e = expenseTable.remove(id);
        return (e != null);
    }
}
