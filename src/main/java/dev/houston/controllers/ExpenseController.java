package dev.houston.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import dev.houston.daos.ExpenseDaoHibernate;
import dev.houston.entities.Expense;
import dev.houston.services.ExpenseService;
import dev.houston.services.ExpenseServiceImpl;
import dev.houston.utils.JwtUtil;
import io.javalin.http.Handler;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Set;

public class ExpenseController {

    private ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDaoHibernate());
    private static Gson gson = new Gson();
    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());

    // GET /expenses (If foreman == true, return ALL.  if false, return only corresponding ID)
    public Handler getAllExpensesHandler = ctx ->{
        logger.setLevel(Level.INFO);
        try {
            String jwt = ctx.header("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJwt(jwt);
            if(decodedJWT.getClaim("foreman").asBoolean()){
                Set<Expense> expenses = this.expenseService.getAllExpenses();
                String expensesJSON = gson.toJson(expenses);
                ctx.result(expensesJSON);
                logger.info("Got all expenses!");
            }else{
                Set<Expense> expenses = this.expenseService.getExpensesByMinerId(decodedJWT.getClaim("minerId").asInt());
                String expensesJSON = gson.toJson(expenses);
                ctx.result(expensesJSON);
                logger.info("Got all miner " + decodedJWT.getClaim("minerId").asInt() + "'s expenses!");
            }
        }catch(Exception e){
            e.printStackTrace();
            ctx.status(403);
            ctx.result("Missing authorization or improper token");
            logger.error("Illegal attempt to get expenses made");
        }
    };
    // currently unused
    // GET /expenses/miner/:mid (Only expenses belonging to mid)
    public Handler getExpensesByMinerIdHandler = ctx -> {
        try {
            String jwt = ctx.header("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJwt(jwt); // throws error if invalid
            int minerId = Integer.parseInt(ctx.pathParam("mid"));
            Set<Expense> expenses = this.expenseService.getExpensesByMinerId(minerId);
            if (expenses.size() == 0) {
                ctx.result("No expenses found for this miner ID");
                ctx.status(404);
                logger.error("No expenses found for this miner ID");
            } else {
                String expensesJSON = gson.toJson(expenses);
                ctx.result(expensesJSON);
                logger.info("Got some expenses for miner " + minerId);
            }
        }catch (Exception e){
            e.printStackTrace();
            ctx.status(403);
            ctx.result("Unauthorized to do that");
            logger.error("Illegal attempt to get miner expenses made");
        }
    };
    // POST /expenses (Create expense)
    public Handler createExpenseHandler = ctx -> {
        try{
            String jwt = ctx.header("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJwt(jwt);

            String body = ctx.body();
            Expense expense = gson.fromJson(body, Expense.class);
            this.expenseService.writeExpense(expense);

            String json = gson.toJson(expense);
            ctx.result(json);
            ctx.status(201);
            logger.info("Expense created for miner " + expense.getMinerId());
        }catch(Exception e){
            ctx.status(403);
            ctx.result("Not authorized to do that");
            logger.error("An illegal attempt to create was made");
        }
    };
    // PUT /expenses/:eid (update expense eid)
    public Handler updateExpenseHandler = ctx -> {
        // Only a foreman can update
        try{
            String jwt = ctx.header("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJwt(jwt);
            if(decodedJWT.getClaim("foreman").asBoolean()){
                int expenseId = Integer.parseInt(ctx.pathParam("eid"));
                Expense oldExpense = this.expenseService.getExpenseById(expenseId);
                // A foreman cannot update his own expense
                if(oldExpense.getMinerId() != decodedJWT.getClaim("minerId").asInt()){
                    String body = ctx.body();
                    Expense updatedExpense = gson.fromJson(body, Expense.class);
                    updatedExpense.setExpenseId(expenseId); // path ID takes precedent
                    // These should not have changed in update
                    updatedExpense.setMinerId(oldExpense.getMinerId());
                    updatedExpense.setDateSubmitted(oldExpense.getDateSubmitted());
                    updatedExpense.setDescription(oldExpense.getDescription());
                    this.expenseService.updateExpense(updatedExpense);
                    ctx.result(gson.toJson(updatedExpense));
                    logger.info("Expense updated");
                }else{
                    ctx.status(403);
                    ctx.result("Cannot Approve/Deny your own expense");
                }
            }else{
                ctx.status(403);
                ctx.result("Not authorized to update");
            }
        }catch (Exception e){
            e.printStackTrace();
            ctx.status(403);
            ctx.result("Missing authorization or improper token");
            logger.error("Illegal attempt to update expense made");
        }
    };

    // DELETE /expenses/:eid (delete expense eid)
    public Handler deleteExpenseHandler = ctx -> {
        try {
            int expenseId = Integer.parseInt(ctx.pathParam("eid"));
            String jwt = ctx.header("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJwt(jwt);
            //ENSURES this is the miner's own expense they are deleting
            if (decodedJWT.getClaim("minerId").asInt() == this.expenseService.getExpenseById(expenseId).getMinerId()) {
                boolean deleted = this.expenseService.deleteExpenseById(expenseId);
                if (deleted) {
                    ctx.result("Expense deleted");
                } else {
                    ctx.result("Could not delete expense");
                    ctx.status(400);
                }
            }else{
                ctx.status(403);
                ctx.result("Unauthorized to delete that expense");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Tried to delete an expense with an invalid JWT!!!");
            ctx.result("Unauthorized");
            ctx.status(403);
        }
    };

}
