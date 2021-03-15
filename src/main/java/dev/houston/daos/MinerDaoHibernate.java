package dev.houston.daos;

import dev.houston.entities.Miner;
import dev.houston.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinerDaoHibernate implements MinerDAO{

    private SessionFactory sf = HibernateUtil.getSessionFactory();
    @Override
    public Miner getMinerById(int id) {
        Session sess = sf.openSession();
        Miner miner = sess.get(Miner.class,id);
        sess.close();
        return miner;
    }

    @Override
    public Set<Miner> getAllMiners() {
        Session sess = sf.openSession();
        CriteriaBuilder builder = sess.getCriteriaBuilder();
        CriteriaQuery<Miner> criteria = builder.createQuery(Miner.class);
        criteria.from(Miner.class);
        // Get list of miners back
        List<Miner> miners = sess.createQuery(criteria).getResultList();
        // Convert list to set
        Set<Miner> mSet = new HashSet<>();
        for(Miner m : miners){
            mSet.add(m);
        }
        sess.close();
        return mSet;
    }
}
