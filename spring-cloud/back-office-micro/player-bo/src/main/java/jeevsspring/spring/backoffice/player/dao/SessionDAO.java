package jeevsspring.spring.backoffice.player.dao;

import jeevsspring.spring.backoffice.player.entity.SessionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Marco Romagnolo
 */
public interface SessionDAO extends CrudRepository<SessionEntity, Long> {

    SessionEntity findByIdAndToken(String id, String token);

    void insert(SessionEntity entity);

    void delete(String id);
}
