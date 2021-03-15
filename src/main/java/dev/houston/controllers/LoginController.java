package dev.houston.controllers;

import com.google.gson.Gson;
import dev.houston.daos.ExpenseDaoHibernate;
import dev.houston.daos.MinerDaoHibernate;
import dev.houston.entities.Miner;
import dev.houston.services.ExpenseService;
import dev.houston.services.ExpenseServiceImpl;
import dev.houston.services.MinerService;
import dev.houston.services.MinerServiceImpl;
import dev.houston.utils.JwtUtil;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

public class LoginController {

    private ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDaoHibernate());
    private MinerService minerService = new MinerServiceImpl(new MinerDaoHibernate());
    private static Gson gson = new Gson();
    private static Logger logger = Logger.getLogger(LoginController.class.getName());

    //TODO fix env variables, or somehow get this ROLE and NAME from DB after AUTHENTICATION

    public Handler loginHandler = ctx -> {
        String body = ctx.body();
        // checking if user and pass input match to a miner in the DB
        Miner logAttempt = gson.fromJson(body,Miner.class);
        logger.info(logAttempt);
        Miner miner = minerService.getMinerByUserPass(logAttempt.getUsername(),logAttempt.getPass());

        if( miner == null ){
            logger.error("Invalid login attempt for user: " + logAttempt.getUsername());
            ctx.result("Invalid user/pass");
            ctx.status(400);
        }else{
            String jwt = JwtUtil.generate(miner.isForeman(), miner.getMinerId(), miner.getFname());
            ctx.result(jwt);
            logger.info("User " + logAttempt.getUsername() + " logged in");
            // ctx.cookie("jwt",jwt); // sends back a cookie jwt is the key
        }
    };


}
