package jp.ne.sonet.statsuta.comments.common.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * コメント作成時のリクエストが不正な場合の例外。
 */
@Value
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
public class BadRequestForCreateException extends RuntimeException {

}
