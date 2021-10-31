package jp.ne.sonet.statsuta.comments.application.payload;

import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import jp.ne.sonet.statsuta.comments.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

/**
 * コメントを作成する際の JSON のリクエストを表すクラス。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateCommentRequest {

  @NotNull
  private Integer postId;

  @NotNull
  private String text;

  @NotNull
  private Integer commentedBy;

  /**
   * コメント作成の JSON リクエストからドメインモデルに変換します。
   *
   * @return コメントのドメインモデル
   */
  public Comment toModel() {
    return Comment.builder()
                  .postId(postId)
                  .text(text)
                  .commentedBy(User.builder().id(commentedBy).build())
                  .isSpam(false)
                  .build();
  }

}
