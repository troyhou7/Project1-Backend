package dev.houston.servicetests;

import dev.houston.daos.ExpenseDAO;
import dev.houston.entities.Expense;
import dev.houston.enums.Status;
import dev.houston.services.ExpenseService;
import dev.houston.services.ExpenseServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class GetExpensesByMinerIdTest {

    //Mock, write, update, getbyminerid
    @Mock
    ExpenseDAO expenseDAO = null;

    private ExpenseService expenseService = null;
    @BeforeEach
    void setUp(){
        Expense e1 = new Expense(1, 1, 100, "Needed a new pickaxe", Status.pending, 100000, 0);
        Expense e2 = new Expense(2, 1, 12, "Needed gloves", Status.pending, 100050, 0);
        Expense e3 = new Expense(3, 2, 5, "Bought water", Status.pending, 121201, 0);
        Expense e4 = new Expense(4, 3, 20, "Brews for the boys", Status.pending, 120000, 0);
        Set<Expense> expenses = new HashSet<>();

        expenses.add(e1);
        expenses.add(e2);
        expenses.add(e3);
        expenses.add(e4);

        Mockito.when(expenseDAO.getAllExpenses()).thenReturn(expenses);

        this.expenseService = new ExpenseServiceImpl(this.expenseDAO);
    }

    @Test
    void get_expense_by_miner_id(){
        Set<Expense> expenses = this.expenseService.getExpensesByMinerId(1);
        System.out.println(expenses);
        Assertions.assertEquals(2,expenses.size());
    }
}
