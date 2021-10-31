package jp.ne.sonet.statsuta.comments.common.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * システム例外を表現するクラスです。
 */
@Value
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
public class SystemException extends RuntimeException {

  /**
   * 指定されたパラメータを使用して SystemException のインスタンスを構築します。
   *
   * @param message 詳細メッセージ
   */
  public SystemException(String message) {
    super(message);
  }

  /**
   * 指定されたパラメータを使用して SystemException のインスタンスを構築します。
   *
   * @param message 詳細メッセージ
   * @param cause 原因
   */
  public SystemException(String message, Throwable cause) {
    super(message, cause);
  }

}
