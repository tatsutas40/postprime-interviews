package jp.ne.sonet.statsuta.comments.common.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * ユーザが存在しない場合の例外。
 */
@Value
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
public class UserNotFoundException extends RuntimeException {

}
