package jp.ne.sonet.statsuta.comments.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static java.util.Objects.nonNull;

/**
 * comment_likes テーブルのエンティティクラス。
 */
@Entity
@Table(name = "comment_likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentLikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "comment_id")
  private Integer commentId;

  @Column(name = "liked_by")
  private Integer likedBy;

  @Column(name = "created_at")
  private Timestamp createdAt;

  /**
   * 更新後の CommentLikeEntity を作成します。
   *
   * @param to 既存の CommentLikeEntity
   * @return 更新後の CommentLikeEntity
   */
  public CommentLikeEntity merge(CommentLikeEntity to) {
    CommentLikeEntity result = to;

    if (nonNull(commentId)) {
      result.setCommentId(commentId);
    }

    if (nonNull(likedBy)) {
      result.setLikedBy(likedBy);
    }

    if (nonNull(createdAt)) {
      result.setCreatedAt(createdAt);
    }

    return result;
  }

}
