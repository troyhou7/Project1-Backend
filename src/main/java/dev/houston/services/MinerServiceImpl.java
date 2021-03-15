package dev.houston.services;

import dev.houston.daos.MinerDAO;

import dev.houston.entities.Miner;
import org.apache.log4j.Logger;

import java.util.Set;

public class MinerServiceImpl implements MinerService{
    private static Logger logger = Logger.getLogger(MinerServiceImpl.class.getName());
    private MinerDAO mdao;

    public MinerServiceImpl(MinerDAO mdao){ this.mdao = mdao; }

    @Override
    public Miner getMinerByUserPass(String user, String pass) {
        Set<Miner> miners = mdao.getAllMiners();
        logger.info(user);
        for(Miner m : miners){
            if( m.getUsername().equals(user) && m.getPass().equals(pass) ){
                return m;
            }else if( m.getUsername().equals(user) && !m.getPass().equals(pass)){
                logger.error("Invalid password for user: " + m.getUsername());
                return null;
            }
        }
        logger.info("No miner found with that username");
        return null;
    }

    @Override
    public String getMinerNameById(int id){
        Miner m = this.mdao.getMinerById(id);
        return m.getFname() + " " + m.getLname();
    }
}
