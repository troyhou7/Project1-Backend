package dev.houston.servicetests;

import dev.houston.daos.ExpenseDaoHibernate;

import dev.houston.entities.Expense;
import dev.houston.enums.Status;
import dev.houston.services.ExpenseService;
import dev.houston.services.ExpenseServiceImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseServiceTests {

    private static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDaoHibernate());
    private static Expense testExpense = null;
    private static Logger logger = Logger.getLogger(ExpenseServiceTests.class.getName());

    @Test
    @Order(1)
    void write_expense(){
        Expense e = new Expense(0, 2, 100, "Needed a new pickaxe", Status.pending, 0, 0);
        expenseService.writeExpense(e);
        testExpense = e;

        Assertions.assertNotEquals(0,e.getExpenseId());
        Assertions.assertNotEquals(0,e.getDateSubmitted());
    }

    @Test
    @Order(2)
    void update_expense(){
        Expense e = new Expense(testExpense.getExpenseId(),1, 100, "Needed a new pickaxe", Status.pending, testExpense.getDateSubmitted(), 0);

        e.setStatus(Status.approved);
        e.setResolvedReason("This checks out");

        this.expenseService.updateExpense(e);

        System.out.println(e);

        Assertions.assertNotEquals(0,e.getDateResolved());
        Assertions.assertNotEquals("",e.getResolvedReason());

        logger.info("Expense " + e.getExpenseId() + " updated successfully");
    }
}
