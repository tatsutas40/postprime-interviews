package jp.ne.sonet.statsuta.comments.domain.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ポストのドメインのモデル。
 */
@Data
@Builder
public class Post {

  private Integer id;

  private String text;

  private User postedBy;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<Like> likes;

  private List<Comment> comments;

}
