package jp.ne.sonet.statsuta.comments.infrastructure.repository;

import jp.ne.sonet.statsuta.comments.infrastructure.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

}
