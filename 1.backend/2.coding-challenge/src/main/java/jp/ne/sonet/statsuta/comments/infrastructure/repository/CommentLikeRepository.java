package jp.ne.sonet.statsuta.comments.infrastructure.repository;

import jp.ne.sonet.statsuta.comments.infrastructure.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {

  @Transactional
  @Modifying
  @Query("delete from CommentLikeEntity c where c.commentId = :commentId")
  void deleteByCommentId(@Param("commentId") Integer commentId);

}
