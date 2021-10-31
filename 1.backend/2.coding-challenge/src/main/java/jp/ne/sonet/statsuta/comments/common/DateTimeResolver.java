package jp.ne.sonet.statsuta.comments.common;

import java.time.LocalDateTime;

/**
 * 現在日時を返すリゾルバ。
 */
public interface DateTimeResolver {

  /**
   * 現在日時を LocalDateTime のインスタンスとして返します。
   *
   * @return 現在日時
   */
  LocalDateTime getCurrentDateTime();

}
