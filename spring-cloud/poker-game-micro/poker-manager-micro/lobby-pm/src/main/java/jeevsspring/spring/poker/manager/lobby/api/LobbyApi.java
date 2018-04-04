package jeevsspring.spring.poker.manager.lobby.api;

import jeevsspring.spring.poker.common.GameType;
import jeevsspring.spring.poker.common.TableSettings;
import jeevsspring.spring.poker.manager.lobby.api.json.*;
import jeevsspring.spring.poker.manager.lobby.manager.Game;
import jeevsspring.spring.poker.manager.lobby.manager.GameException;
import jeevsspring.spring.poker.manager.lobby.manager.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController()
@RequestMapping("/lobby")
public class LobbyApi {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private Games games;

    @GetMapping("/test")
    public ResponseEntity test() {
        logger.log(Level.FINEST, "test()");
        return ResponseEntity.ok("Test completed");
    }

    @PostMapping("/table")
    public @ResponseBody ResponseEntity table(@RequestBody SettingsIn in) {
        TableSettings settings = new TableSettings();
        settings.setActionTimeout(in.getActionTimeout());
        settings.setGameType(GameType.TEXAS_HOLDEM);
        settings.setName(in.getName());
        settings.setNumberOfSeats(in.getNumberOfSeats());
        settings.setStartTimeout(in.getStartTimeout());
        Game game = new Game(settings);
        games.add(game);
        return ResponseEntity.ok(new Status());
    }

    @PutMapping("/table/{id}")
    public @ResponseBody ResponseEntity tables(@RequestBody SettingsIn in, @PathVariable("id") String id) {
        Status out = new Status();
        ResponseEntity response;
        try {
            Game game = games.get(id);
            TableSettings settings = new TableSettings();
            settings.setActionTimeout(in.getActionTimeout());
            settings.setGameType(GameType.TEXAS_HOLDEM);
            settings.setName(in.getName());
            settings.setNumberOfSeats(in.getNumberOfSeats());
            settings.setStartTimeout(in.getStartTimeout());
            game.setSettings(settings);
            response = ResponseEntity.ok(new Status());
        } catch (GameException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            out.setError(e.getError());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(out);
        }

        return response;
    }

    @DeleteMapping("/table/{id}")
    public @ResponseBody ResponseEntity tables(@PathVariable("id") String id) {
        Status out = new Status();
        ResponseEntity response;
        try {
            games.remove(id);
            response = ResponseEntity.ok(out);
        } catch (GameException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            out.setError(e.getError());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(out);
        }
        return response;
    }

    @PostMapping("/tables")
    public @ResponseBody ResponseEntity tables(@RequestBody TablesIn in) {
        logger.log(Level.FINEST,"tables(" + in + ")");

        ResponseEntity response;
        TablesOut out = new TablesOut();
        out.setTables(new ArrayList<>());
        List<? extends Game> tables = games.getAll();
        for (Game table : tables) {
            Table lobbyTable = new Table();
            lobbyTable.setId(table.getTableId());
            lobbyTable.setName(table.getSettings().getName());
            out.getTables().add(lobbyTable);
        }
        out.setSessionId(in.getSessionId());
        out.setSessionToken(in.getSessionToken());
        response = ResponseEntity.ok(out);

        logger.log(Level.FINE,"tables(" + in + ") return " + out);
        return response;
    }

    @PostMapping("/table-settings")
    public @ResponseBody ResponseEntity tableSettings(@RequestBody TableSettingsIn in) {
        logger.log(Level.FINEST,"tableSettings(" + in + ")");

        ResponseEntity response;
        TableSettingsOut out = new TableSettingsOut();
        try {
            TableSettings settings = games.get(in.getTableId()).getSettings();
            out.setId(in.getTableId());
            out.setName(settings.getName());
            out.setGameType(settings.getGameType().name());
            out.setActionTimeout(settings.getActionTimeout());
            out.setNumberOfSeats(settings.getNumberOfSeats());
            out.setStartTimeout(settings.getStartTimeout());
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = ResponseEntity.ok(out);
        } catch (GameException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            out.setError(e.getError());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(out);
        }

        logger.log(Level.FINE,"tableSettings(" + in + ") return " + out);
        return response;
    }

}
