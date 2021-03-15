package dev.houston.servicetests;

import dev.houston.daos.MinerDAO;
import dev.houston.entities.Miner;
import dev.houston.services.MinerService;
import dev.houston.services.MinerServiceImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class MinerServiceTestsMockito {

    @Mock
    MinerDAO mdao = null;

    private MinerService minerService = null;

    Logger logger = Logger.getLogger(MinerServiceTestsMockito.class.getName());

    @BeforeEach
    void setUp(){
        Miner m1 = new Miner(1,"Grimly","Berrgson","gber","1234",false);
        Miner m2 = new Miner(2,"Mardrin","Caskfield","mcask","oooo",true);
        Miner m3 = new Miner(3,"Mostred","Bitterhood","mbitter","0000",false);
        Miner m4 = new Miner(4,"Nasuc","Hammercoat","hammer12","0000",false);

        Set<Miner> miners = new HashSet<>();
        miners.add(m1);
        miners.add(m2);
        miners.add(m3);
        miners.add(m4);

        lenient().when(mdao.getAllMiners()).thenReturn(miners);
        lenient().when(mdao.getMinerById(1)).thenReturn(m1);
        this.minerService = new MinerServiceImpl(mdao);
    }

    @Test
    void get_miner_by_user_pass_valid(){
        String user = "mbitter";
        String pass = "0000";

        Miner m = this.minerService.getMinerByUserPass(user,pass);

        Assertions.assertEquals(3, m.getMinerId());
        logger.info("Miner " + m.getMinerId() + " logged in");
    }

    @Test
    void get_miner_by_user_pass_invalid(){
        String user = "mbitter";
        String pass = "0020";

        Miner m = this.minerService.getMinerByUserPass(user,pass);

        Assertions.assertNull(m);
        logger.info("Invalid password");
    }

    @Test
    void get_miner_by_user_pass_not_found(){
        String user = "test";
        String pass = "0000";

        Miner m = this.minerService.getMinerByUserPass(user,pass);

        Assertions.assertNull(m);
        logger.info("User not found");
    }

    @Test
    void get_miner_name_by_id(){
        int id = 1;
        String name = this.minerService.getMinerNameById(id);
        Assertions.assertEquals("Grimly Berrgson", name);
        logger.info(name);
    }
}
