package jeevsspring.spring.backoffice.operator.dao;

import jeevsspring.spring.backoffice.operator.entity.OperatorEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Marco Romagnolo
 */

public interface OperatorDAO extends CrudRepository<OperatorEntity, Long> {

    OperatorEntity findByUsernameAndPassword(String username, String password);
}
