package jeevsspring.spring.console.controller;

import jeevsspring.spring.console.bean.TableGameBean;
import jeevsspring.spring.console.bo.BOException;
import jeevsspring.spring.console.bo.json.OperatorLoginIn;
import jeevsspring.spring.console.bo.json.OperatorLoginOut;
import jeevsspring.spring.console.bo.json.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Scope("request")
@Controller("addTableGameCtrl")
public class AddTableGameCtrl implements Serializable {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private TableGameBean tableGame;

    @Autowired
    private RestTemplate restTemplate;

    private String target = "http://poker-lobby-manager/poker-manager/api";

    public void create() {
        logger.log(Level.FINE, "create() tableGame: " + tableGame);

        TableGameBean tableSettings = new TableGameBean();
        tableSettings.setActionTimeout(tableGame.getActionTimeout());
        tableSettings.setGameType(tableGame.getGameType());
        tableSettings.setName(tableGame.getName());
        tableSettings.setNumberOfSeats(tableGame.getNumberOfSeats());
        tableSettings.setStartTimeout(tableGame.getStartTimeout());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TableGameBean> entity = new HttpEntity<>(tableSettings, headers);
        try {
            ResponseEntity rs = restTemplate.postForEntity(target + "/lobby/table", entity, Status.class);
            if (rs.getStatusCode() != HttpStatus.OK) {
                logger.log(Level.SEVERE, "Error " + rs);
            }
            logger.log(Level.FINE, "create(" + tableGame + ") return " + rs.getBody());
        } catch (RestClientException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
