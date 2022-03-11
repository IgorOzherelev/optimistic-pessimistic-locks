package sber.ru.dss.dsshomeworks.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sber.ru.dss.dsshomeworks.entities.Account;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

@Component
public class AccountDao {
    private final EntityManager em;

    @Autowired
    public AccountDao(EntityManager em) {
        this.em = em;
    }

    public Account getAccountByIdPessimistic(Long id) {
        return em.find(Account.class, id, LockModeType.PESSIMISTIC_READ);
    }

    public Account getAccountByIdOptimistic(Long id) {
        return em.find(Account.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    public void updateAccountSumByIdOptimistic(Account account, Integer sum) {
        Query query = em.createQuery("update Account acc set acc.sum = :sum" +
                " where acc.id = :id and acc.versionNum = :versionNum");

        query.setParameter("id", account.getId());
        query.setParameter("sum", account.getSum() - sum);
        query.setParameter("versionNum", account.getVersionNum());

        query.executeUpdate();
    }

    public void updateAccountSumByIdPessimistic(Account account, Integer sum) {
        em.lock(account, LockModeType.PESSIMISTIC_WRITE);

        Query query = em.createQuery("update Account acc set acc.sum = :sum" +
                " where acc.id = :id");

        query.setParameter("id", account.getId());
        query.setParameter("sum", account.getSum() - sum);

        query.executeUpdate();
    }
}
