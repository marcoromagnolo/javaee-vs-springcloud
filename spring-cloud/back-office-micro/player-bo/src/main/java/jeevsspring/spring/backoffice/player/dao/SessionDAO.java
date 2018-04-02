package jeevsspring.spring.backoffice.player.dao;

import jeevsspring.spring.backoffice.player.entity.SessionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Marco Romagnolo
 */
public interface SessionDAO extends CrudRepository<SessionEntity, String> {

    SessionEntity findByIdAndToken(String id, String token);

    SessionEntity save(SessionEntity enstity);

    void delete(String id);
}
