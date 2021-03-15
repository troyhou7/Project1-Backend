package dev.houston.daotests;

import dev.houston.daos.MinerDAO;
import dev.houston.daos.MinerDaoHibernate;
import dev.houston.entities.Miner;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class MinerDaoTests {

    private static Logger logger = Logger.getLogger(MinerDaoTests.class.getName());
    private static MinerDAO mdao = new MinerDaoHibernate();

    @Test
    void get_miner_by_id(){
        int id = 1;
        Miner m = this.mdao.getMinerById(1);

        Assertions.assertNotNull(m);
        logger.info("Miner "+m.getMinerId()+" retrieved");
    }
    @Test
    void get_all_miners(){
        Set<Miner> miners = new HashSet<>();
        miners = this.mdao.getAllMiners();

        // we have no create so we have to know how many are in the DB
        Assertions.assertTrue(miners.size() > 2);
        logger.info("Miners retrieved");
    }
}
