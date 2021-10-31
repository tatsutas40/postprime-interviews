package jp.ne.sonet.statsuta.comments.application.payload;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

/**
 * コメントを更新する際の JSON のリクエストを表すクラス。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateCommentRequest {

  @NotNull
  private Integer userId;

  private String text;

  private Boolean isSpam;

  public boolean validate() {
    return isNotEmpty(text) || nonNull(isSpam);
  }

  /**
   * コメント更新の JSON リクエストからドメインモデルに変換します。
   *
   * @param commentId 更新するコメントの ID
   * @return コメントのドメインモデル
   */
  public Comment toModel(Integer commentId) {
    return Comment.builder()
                  .id(commentId)
                  .text(text)
                  .isSpam(isSpam)
                  .build();
  }

}
