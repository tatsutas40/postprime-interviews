package jp.ne.sonet.statsuta.comments.domain.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * コメントのドメインのモデル。
 */
@Data
@Builder
public class Comment {

  private Integer id;

  private Integer postId;

  private String text;

  private User commentedBy;

  private Boolean isSpam;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
