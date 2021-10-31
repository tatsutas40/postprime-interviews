package jp.ne.sonet.statsuta.comments.domain.mapper;

import static java.util.Objects.nonNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.CommentEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.PostEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.UserEntity;

/**
 * コメントのドメインモデルとエンティティを相互に変換を行うマッパー。
 */
public final class CommentEntityMapper {

  private CommentEntityMapper() {
    // Do Nothing.
  }

  public static CommentEntity toEntityForCreation(Comment comment, LocalDateTime current) {
    return CommentEntity.builder()
                        .postId(comment.getPostId())
                        .text(comment.getText())
                        .commentedBy(nonNull(comment.getCommentedBy()) ? comment.getCommentedBy().getId() : null)
                        .isSpam(comment.getIsSpam())
                        .createdAt(Timestamp.valueOf(current))
                        .updatedAt(Timestamp.valueOf(current))
                        .build();
  }

  public static CommentEntity toEntityForUpdation(Comment comment, LocalDateTime current) {
    return CommentEntity.builder()
                        .text(comment.getText())
                        .isSpam(comment.getIsSpam())
                        .updatedAt(Timestamp.valueOf(current))
                        .build();
  }

  public static Comment fromEntity(CommentEntity commentEntity, PostEntity postEntity, UserEntity userEntity) {
    return Comment.builder()
                  .id(commentEntity.getId())
                  .postId(postEntity.getId())
                  .text(commentEntity.getText())
                  .commentedBy(UserEntityMapper.fromEntity(userEntity))
                  .isSpam(commentEntity.getIsSpam())
                  .createdAt(commentEntity.getCreatedAt().toLocalDateTime())
                  .updatedAt(commentEntity.getUpdatedAt().toLocalDateTime())
                  .build();
  }

}
