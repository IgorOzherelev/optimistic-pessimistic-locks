package sber.ru.dss.dsshomeworks.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import sber.ru.dss.dsshomeworks.daos.AccountDao;
import sber.ru.dss.dsshomeworks.entities.Account;

import javax.transaction.Transactional;

@Service
@Slf4j
public class AccountService {
    private final AccountDao accountDao;

    private int attemptCounter = 0;

    @Autowired
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    // optimistic lock usage with retryable
    @Retryable(StaleStateException.class)
    @Transactional
    public void transactOptimistic(Long fromId, Long toId, Integer sum) {
        Account accountFrom = accountDao.getAccountByIdOptimistic(fromId);
        Account accountTo = accountDao.getAccountByIdOptimistic(toId);

        log.info("attemptCounter: " + attemptCounter++); // dummy retryable checking

        checkAccountSum(sum, accountFrom);

        accountDao.updateAccountSumById(accountFrom, sum);
        accountDao.updateAccountSumById(accountTo, -sum);
    }

    @Transactional
    public void transactPessimistic(Long fromId, Long toId, Integer sum) {
        Account accountFrom = accountDao.getAccountByIdPessimistic(fromId);
        Account accountTo = accountDao.getAccountByIdPessimistic(toId);

        checkAccountSum(sum, accountFrom);

        accountDao.updateAccountSumByIdPessimistic(accountFrom, sum);
        accountDao.updateAccountSumByIdPessimistic(accountTo, -sum);
    }

    private void checkAccountSum(Integer sum, Account accountFrom) {
        if (accountFrom.getSum() < sum) {
            throw new IllegalStateException("Account from doesn't have enough money");
        }
    }
}
