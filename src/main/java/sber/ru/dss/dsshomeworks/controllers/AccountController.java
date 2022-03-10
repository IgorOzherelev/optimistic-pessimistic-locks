package sber.ru.dss.dsshomeworks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sber.ru.dss.dsshomeworks.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("transact/{fromId}/{toId}/{sum}")
    public ResponseEntity<String> transact(@PathVariable Long fromId,
                                           @PathVariable Long toId, @PathVariable Integer sum) {

        accountService.transact(fromId, toId, sum);

        return ResponseEntity.ok("Sent sum from " + fromId + " to " + toId + " " + sum);
    }
}
