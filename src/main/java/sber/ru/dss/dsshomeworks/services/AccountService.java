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

        log.info(String.valueOf(attemptCounter++)); // dummy retryable checking

        if (accountFrom.getSum() < sum) {
            throw new IllegalStateException("Account from doesn't have enough money");
        }

        accountDao.updateAccountSumByIdOptimistic(accountFrom, sum);
        accountDao.updateAccountSumByIdOptimistic(accountTo, -sum);
    }

    @Transactional
    public void transactPessimistic(Long fromId, Long toId, Integer sum) {

    }
}
