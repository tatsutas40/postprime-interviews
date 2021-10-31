package jp.ne.sonet.statsuta.comments.infrastructure.repository;

import jp.ne.sonet.statsuta.comments.infrastructure.entity.PostLikeEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostLikeRepository extends CrudRepository<PostLikeEntity, Integer> {

}
