package sber.ru.dss.dsshomeworks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class AccountService {
    private final EntityManager em;

    @Autowired
    public AccountService(EntityManager em) {
        this.em = em;
    }

    public void transact(Long fromId, Long toId, Integer sum) {

    }
}
