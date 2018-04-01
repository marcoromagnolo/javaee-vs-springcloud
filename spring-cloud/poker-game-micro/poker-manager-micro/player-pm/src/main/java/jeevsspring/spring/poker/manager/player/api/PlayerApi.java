package jeevsspring.spring.poker.manager.player.api;


import jeevsspring.spring.poker.manager.player.api.json.*;
import jeevsspring.spring.poker.manager.player.bo.BOException;
import jeevsspring.spring.poker.manager.player.bo.json.BOAccountOut;
import jeevsspring.spring.poker.manager.player.bo.json.BOLoginOut;
import jeevsspring.spring.poker.manager.player.bo.json.BOLogoutOut;
import jeevsspring.spring.poker.manager.player.bo.json.BOWalletOut;
import jeevsspring.spring.poker.manager.player.manager.PlayerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController()
@RequestMapping("/player")
public class PlayerApi {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private PlayerManager playerManager;

    @GetMapping("/test")
    public ResponseEntity test() {
        logger.log(Level.FINEST, "test()");
        return ResponseEntity.ok("Test completed");
    }

    @PostMapping("/wallet")
    public ResponseEntity wallet(WalletIn in) {
        logger.log(Level.FINEST,"wallet(" + in + ")");

        ResponseEntity response;
        WalletOut out = new WalletOut();
        try {
            BOWalletOut bo = playerManager.getWallet(in.getSessionId(), in.getSessionToken());
            out.setBalance(bo.getBalance());
            response = ResponseEntity.ok(out);
        } catch (BOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            out.setError(e.getError());
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(out);
        }
        logger.log(Level.FINE,"wallet(" + in + ") return " + out);
        return response;
    }

    @PostMapping("/account")
    public ResponseEntity account(AccountIn in) {
        logger.log(Level.FINEST,"wallet(" + in + ")");

        ResponseEntity response;
        AccountOut out = new AccountOut();
        try {
            BOAccountOut bo = playerManager.getAccount(in.getSessionId(), in.getSessionToken());
            out.setFirstName(bo.getFirstName());
            out.setLastName(bo.getLastName());
            response = ResponseEntity.ok(out);
        } catch (BOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            out.setError(e.getError());
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(out);
        }
        logger.log(Level.FINE,"login(" + in + ") return " + out);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity login(LoginIn in) {
        logger.log(Level.FINEST,"login(" + in + ")");

        ResponseEntity response;
        LoginOut out = new LoginOut();
        try {
            BOLoginOut bo = playerManager.login(in.getUsername(), in.getPassword());
            out.setSessionId(bo.getSessionId());
            out.setSessionToken(bo.getSessionToken());
            out.setNickname(bo.getNickname());
            out.setSessionCreateTime(bo.getSessionCreateTime());
            out.setSessionExpireTime(bo.getSessionCreateTime());

            // Set Account
            AccountOut account = new AccountOut();
            account.setFirstName(bo.getFirstName());
            account.setLastName(bo.getLastName());
            out.setAccount(account);

            // Set Wallet
            WalletOut wallet = new WalletOut();
            wallet.setBalance(bo.getBalance());
            out.setWallet(wallet);

            response = ResponseEntity.ok(out);
        } catch (BOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            out.setError(e.getError());
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(out);
        }
        logger.log(Level.FINE,"login(" + in + ") return " + out);
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity logout(LogoutIn in) {
        logger.log(Level.FINEST,"logout(" + in + ")");

        ResponseEntity response;
        LogoutOut out = new LogoutOut();
        try {
            BOLogoutOut bo = playerManager.logout(in.getSessionId(), in.getSessionToken());
            out.setMessage(bo.getMessage());
            response = ResponseEntity.ok(out);
        } catch (BOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            out.setError(e.getError());
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(out);
        }
        logger.log(Level.FINE,"logout(" + in + ") return " + out);
        return response;
    }
}
