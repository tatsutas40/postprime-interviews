package jp.ne.sonet.statsuta.comments.application.payload;

import jp.ne.sonet.statsuta.comments.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザーの JSON のメッセージを表すクラス。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserPayload {

  private Integer id;

  private String firstName;

  private String lastName;

  /**
   * ユーザのドメインモデルをユーザの JSON メッセージに変換します。
   *
   * @param user ユーザのドメインモデル
   * @return ユーザの JSON メッセージ
   */
  public static UserPayload of(User user) {
    return UserPayload.builder()
                      .id(user.getId())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .build();
  }

}
