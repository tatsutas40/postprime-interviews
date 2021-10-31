package jp.ne.sonet.statsuta.comments.application.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * コメントを作成する際の JSON のレスポンスを表すクラス。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateCommentResponse {

  private Integer id;

  private Integer postId;

  private String text;

  private UserPayload commentedBy;

  private Boolean isSpam;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime updatedAt;

  /**
   * コメントのドメインモデルをコメント更新の JSON レスポンスに変換します。
   *
   * @param comment コメントのドメインモデル
   * @return コメント更新の JSON レスポンス
   */
  public static UpdateCommentResponse of(Comment comment) {
    return UpdateCommentResponse.builder()
                                .id(comment.getId())
                                .postId(comment.getPostId())
                                .text(comment.getText())
                                .commentedBy(UserPayload.of(comment.getCommentedBy()))
                                .isSpam(comment.getIsSpam())
                                .createdAt(comment.getCreatedAt())
                                .updatedAt(comment.getUpdatedAt())
                                .build();
  }

}
