package dev.houston.app;

import dev.houston.controllers.ExpenseController;
import dev.houston.controllers.LoginController;
import dev.houston.controllers.MinerController;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        // TODO Controllers

        Javalin app = Javalin.create(
                config -> {
                    config.enableCorsForAllOrigins();
                }
        );

        ExpenseController expenseController = new ExpenseController();
        LoginController loginController = new LoginController();
        MinerController minerController = new MinerController();

        // GET /expenses (If foreman == true, return ALL.  if false, return only corresponding ID)
        app.get("/expenses",expenseController.getAllExpensesHandler);
        // GET /expenses/miner/:mid (Only expenses belonging to mid)
        app.get("/expenses/miner/:mid",expenseController.getExpensesByMinerIdHandler);

        // GET /miner/:mid gets miner name
        app.get("/miner/:mid",minerController.getMinerNameByIdHandler);

        // POST /expenses (Create expense)
        app.post("/expenses", expenseController.createExpenseHandler);

        // PUT /expenses/:eid (update expense eid)
        app.put("/expenses/:eid", expenseController.updateExpenseHandler);

        // DELETE /expenses/:eid (delete expense eid)
        app.delete("/expenses/:eid", expenseController.deleteExpenseHandler);

        // POST /login
        app.post("/login", loginController.loginHandler);

        app.start();
    }

}
