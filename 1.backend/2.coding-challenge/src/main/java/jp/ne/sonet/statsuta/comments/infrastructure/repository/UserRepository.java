package jp.ne.sonet.statsuta.comments.infrastructure.repository;

import jp.ne.sonet.statsuta.comments.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer>  {

}
