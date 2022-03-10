package sber.ru.dss.dsshomeworks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sber.ru.dss.dsshomeworks.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/transact/{fromId}/{toId}/{sum}", method = RequestMethod.GET)
    public ResponseEntity<String> transact(@PathVariable Long fromId,
                                           @PathVariable Long toId, @PathVariable Integer sum) {

        accountService.transactOptimistic(fromId, toId, sum);

        return ResponseEntity.ok("Sent sum from " + fromId + " to " + toId + " " + sum);
    }
}
