package dev.houston.daos;

import dev.houston.entities.Miner;

import java.util.Set;

public interface MinerDAO {

    Miner getMinerById(int id);
    Set<Miner> getAllMiners();


}
