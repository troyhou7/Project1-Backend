package dev.houston.services;

import dev.houston.entities.Miner;

public interface MinerService {

    Miner getMinerByUserPass(String user, String pass);
    String getMinerNameById(int id);


}
