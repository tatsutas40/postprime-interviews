package jp.ne.sonet.statsuta.comments.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.ne.sonet.statsuta.comments.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * エラーが発生したときの JSON メッセージを表すクラス。
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  public ErrorResponse(ErrorCode errorCode) {
    code = errorCode.getCode();
    message = errorCode.getMessage();
  }

}