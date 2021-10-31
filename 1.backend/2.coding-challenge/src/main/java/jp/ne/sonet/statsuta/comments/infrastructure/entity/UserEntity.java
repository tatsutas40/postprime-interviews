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
 * users テーブルのエンティティクラス。
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "first_name", length = 100)
  private String firstName;

  @Column(name = "last_name", length = 100)
  private String lastName;

  @Column(name = "created_at")
  private Timestamp createdAt;

  /**
   * 更新後の UserEntity を作成します。
   *
   * @param to 既存の UserEntity
   * @return 更新後の UserEntity
   */
  public UserEntity merge(UserEntity to) {
    UserEntity result = to;

    if (nonNull(firstName)) {
      result.setFirstName(firstName);
    }

    if (nonNull(lastName)) {
      result.setLastName(lastName);
    }

    if (nonNull(createdAt)) {
      result.setCreatedAt(createdAt);
    }

    return result;
  }

}
