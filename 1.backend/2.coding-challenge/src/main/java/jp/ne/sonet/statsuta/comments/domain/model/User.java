package jp.ne.sonet.statsuta.comments.domain.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * ユーザーのドメインのモデル。
 */
@Data
@Builder
public class User {

  private Integer id;

  private String firstName;

  private String lastName;

  private LocalDateTime createdAt;

}
