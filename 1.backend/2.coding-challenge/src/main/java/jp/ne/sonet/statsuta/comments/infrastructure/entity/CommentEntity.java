package jp.ne.sonet.statsuta.comments.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

import static java.util.Objects.nonNull;

/**
 * comments テーブルのエンティティクラス。
 */
@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "post_id")
  private Integer postId;

  @Column(name = "text", length = 4096)
  private String text;

  @Column(name = "commented_by")
  private Integer commentedBy;

  @Column(name = "is_spam")
  private Boolean isSpam;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  /**
   * 更新後の CommentEntity を作成します。
   *
   * @param to 既存の CommentEntity
   * @return 更新後の CommentEntity
   */
  public CommentEntity merge(CommentEntity to) {
    CommentEntity result = to;

    if (nonNull(postId)) {
      result.setPostId(postId);
    }

    if (nonNull(text)) {
      result.setText(text);
    }

    if (nonNull(commentedBy)) {
      result.setCommentedBy(commentedBy);
    }

    if (nonNull(isSpam)) {
      result.setIsSpam(isSpam);
    }

    if (nonNull(createdAt)) {
      result.setCreatedAt(createdAt);
    }

    if (nonNull(updatedAt)) {
      result.setUpdatedAt(updatedAt);
    }

    return result;
  }

}
