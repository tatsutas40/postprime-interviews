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
 * posts テーブルのエンティティクラス。
 */
@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "text", length = 4096)
  private String text;

  @Column(name = "posted_by")
  private Integer postedBy;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  /**
   * 更新後の PostEntity を作成します。
   *
   * @param to 既存の PostEntity
   * @return 更新後の PostEntity
   */
  public PostEntity merge(PostEntity to) {
    PostEntity result = to;

    if (nonNull(text)) {
      result.setText(text);
    }

    if (nonNull(postedBy)) {
      result.setPostedBy(postedBy);
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
