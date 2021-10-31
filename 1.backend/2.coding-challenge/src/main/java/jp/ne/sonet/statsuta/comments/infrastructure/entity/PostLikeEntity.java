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
 * post_likes テーブルのエンティティクラス。
 */
@Entity
@Table(name = "post_likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "post_id")
  private Integer postId;

  @Column(name = "liked_by")
  private Integer likedBy;

  @Column(name = "created_at")
  private Timestamp createdAt;

  /**
   * 更新後の PostLikeEntity を作成します。
   *
   * @param to 既存の PostLikeEntity
   * @return 更新後の PostLikeEntity
   */
  public PostLikeEntity merge(PostLikeEntity to) {
    PostLikeEntity result = to;

    if (nonNull(postId)) {
      result.setPostId(postId);
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
