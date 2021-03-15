package dev.houston.daotests;

import dev.houston.daos.ExpenseDAO;
import dev.houston.daos.ExpenseDaoHibernate;
import dev.houston.daos.ExpenseDaoLocal;
import dev.houston.entities.Expense;
import dev.houston.enums.Status;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDaoTests {

    private static Logger  logger = Logger.getLogger(ExpenseDaoTests.class.getName());
    private static ExpenseDAO edao = new ExpenseDaoHibernate();
    private static Expense testExpense = null;

    @Test
    @Order(1)
    void create_expense(){
        Expense e = new Expense(0, 1, 100,"I had to buy a new pickaxe", Status.pending, 0, 0);
        edao.createExpense(e); // save

        testExpense = e;
        System.out.println(e);
        Assertions.assertNotEquals(0,e.getExpenseId());
        logger.info("Create Expense Test Passed");
    }

    @Test
    @Order(2)
    void get_expense_by_id(){
        int id = testExpense.getExpenseId();
        Expense e = edao.getExpenseById(id);

        Assertions.assertEquals(testExpense.getExpenseId(),e.getExpenseId());
        System.out.println("The expense retrieved was eId:" + e.getExpenseId());
    }

    @Test
    @Order(3)
    void get_all_expenses(){
        Expense e1 = new Expense(0, 1, 100,"I had to buy a new pickaxe", Status.pending, 0, 0);
        Expense e2 = new Expense(0, 1, 11,"New gloves", Status.pending, 0, 0);
        Expense e3 = new Expense(0, 1, 25,"Lunch", Status.pending, 0, 0);

        edao.createExpense(e1);
        edao.createExpense(e2);
        edao.createExpense(e3);

        Set<Expense> allExpenses = edao.getAllExpenses();

        System.out.println(e2.getExpenseId());

        Assertions.assertTrue(allExpenses.size()>2);
    }

    @Test
    @Order(4)
    void update_expense(){
        Expense e = edao.getExpenseById(testExpense.getExpenseId());
        e.setStatus(Status.approved);
        edao.updateExpense(e);

        Expense updatedExpense = edao.getExpenseById(testExpense.getExpenseId());
        System.out.println(updatedExpense);
        Assertions.assertEquals(Status.approved, updatedExpense.getStatus());
    }

    @Test
    @Order(5)
    void delete_expense_by_id(){
        int id = testExpense.getExpenseId();
        boolean deleted = edao.deleteExpenseById(id);
        Assertions.assertTrue(deleted);
        logger.info("Delete expense test passed for eId:" + id);
    }
}
