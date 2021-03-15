package dev.houston.daos;

import dev.houston.entities.Expense;
import dev.houston.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenseDaoHibernate implements ExpenseDAO{

    private SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public Expense createExpense(Expense expense) {
        Session sess = sf.openSession();
        sess.getTransaction().begin();
        sess.save(expense);
        sess.getTransaction().commit();
        sess.close();
        return expense;
    }

    @Override
    public Set<Expense> getAllExpenses() {
        Session sess = sf.openSession();
        CriteriaBuilder builder = sess.getCriteriaBuilder();
        CriteriaQuery<Expense> criteria = builder.createQuery(Expense.class);
        criteria.from(Expense.class);
        // Get list of expenses back
        List<Expense> expenses = sess.createQuery(criteria).getResultList();
        // Convert list to set
        Set<Expense> eSet = new HashSet<>();
        for(Expense e : expenses){
            eSet.add(e);
        }
        sess.close();
        return eSet;
    }

    @Override
    public Expense getExpenseById(int id) {
        Session sess = sf.openSession();
        Expense expense = sess.get(Expense.class,id);
        sess.close();
        return expense;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Session sess = sf.openSession();
        sess.getTransaction().begin();
        sess.update(expense);
        sess.getTransaction().commit();
        sess.close();
        return expense;
    }

    @Override
    public boolean deleteExpenseById(int id) {
        try{
            Session sess = sf.openSession();
            sess.getTransaction().begin();
            sess.delete(this.getExpenseById(id));
            sess.getTransaction().commit();
            sess.close();
            return true;
        }catch(HibernateException he){ // in hibernate all exceptions are a runtime HibernateException
            he.printStackTrace();
            return false;
        }
    }
}
