package jeevsspring.spring.backoffice.operator.dao;

import jeevsspring.spring.backoffice.operator.entity.OperatorSessionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Marco Romagnolo
 */
public interface OperatorSessionDAO extends CrudRepository<OperatorSessionEntity, Long> {

    OperatorSessionEntity findByIdAndToken(String id, String token);

    OperatorSessionEntity save(OperatorSessionEntity entity);

    void delete(String sessionId);
}
