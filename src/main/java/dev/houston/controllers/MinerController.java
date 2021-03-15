package dev.houston.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

import dev.houston.daos.MinerDaoHibernate;
import dev.houston.services.MinerService;
import dev.houston.services.MinerServiceImpl;
import dev.houston.utils.JwtUtil;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

public class MinerController {
    private MinerService minerService = new MinerServiceImpl(new MinerDaoHibernate());
    private static Gson gson = new Gson();
    private static Logger logger = Logger.getLogger(MinerController.class.getName());

    public Handler getMinerNameByIdHandler = ctx -> {
        try {
            String jwt = ctx.header("Authorization");
            DecodedJWT decodedJWT = JwtUtil.isValidJwt(jwt); // throws error if invalid
            int minerId = Integer.parseInt(ctx.pathParam("mid"));
            String name = this.minerService.getMinerNameById(minerId);
            ctx.result(name);
        }catch(Exception e){
            e.printStackTrace();
            ctx.result("Unauthorized to access endpoint");
            logger.error("Illegal attempt to get miner name by id");
            ctx.status(403);
        }
    };
}
