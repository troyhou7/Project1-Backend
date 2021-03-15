package dev.houston.services;

import dev.houston.daos.ExpenseDAO;
import dev.houston.entities.Expense;
import dev.houston.enums.Status;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class ExpenseServiceImpl implements ExpenseService{

    private static Logger logger = Logger.getLogger(ExpenseServiceImpl.class.getName());
    private ExpenseDAO edao;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO){ this.edao = expenseDAO; }

    // Test
    @Override
    public Expense writeExpense(Expense expense) {
        // Set date created
        expense.setStatus(Status.pending);
        expense.setDateSubmitted(System.currentTimeMillis()/1000);

        this.edao.createExpense(expense);

        return expense;
    }

    @Override
    public Set<Expense> getAllExpenses() {
        return this.edao.getAllExpenses();
    }

    //Test
    @Override
    public Set<Expense> getExpensesByMinerId(int id) {
        Set<Expense> expenses = this.edao.getAllExpenses();
        Set<Expense> minerExpenses = new HashSet<>();

        for(Expense e : expenses){
            if(e.getMinerId() == id){
                minerExpenses.add(e);
                logger.info("Expense found for miner "+ id);
            }
        }
        return minerExpenses;
    }

    @Override
    public Expense getExpenseById(int id) {
        return this.edao.getExpenseById(id);
    }

    @Override
    public Expense updateExpense(Expense expense) {

        Expense oldExpense = this.edao.getExpenseById(expense.getExpenseId());
        // If the expense was resolved:
        if( oldExpense.getStatus() == Status.pending
            && expense.getStatus() != Status.pending ){
            expense.setDateResolved(System.currentTimeMillis()/1000);
        }
        this.edao.updateExpense(expense);

        return expense;
    }

    @Override
    public boolean deleteExpenseById(int id) {
        return this.edao.deleteExpenseById(id);
    }
}
