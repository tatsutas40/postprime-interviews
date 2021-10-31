package jp.ne.sonet.statsuta.comments.common;

import lombok.Builder;
import lombok.Value;

/**
 * エラーメッセージを表現するクラス。
 */
@Value
@Builder
public class ErrorCode {

  String code;

  String message;

}
