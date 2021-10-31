package jp.ne.sonet.statsuta.comments.domain.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * ライクのドメインのモデル。
 */
@Data
@Builder
public class Like {

  private User user;

  private LocalDateTime createdAt;

}
