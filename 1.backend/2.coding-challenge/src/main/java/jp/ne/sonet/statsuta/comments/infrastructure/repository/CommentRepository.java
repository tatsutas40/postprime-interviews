package jp.ne.sonet.statsuta.comments.infrastructure.repository;

import jp.ne.sonet.statsuta.comments.infrastructure.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

}
