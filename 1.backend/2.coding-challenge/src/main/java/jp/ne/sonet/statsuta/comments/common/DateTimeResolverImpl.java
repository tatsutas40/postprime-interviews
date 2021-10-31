package jp.ne.sonet.statsuta.comments.common;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 * {@link DateTimeResolver} の実装クラスです。
 */
@Component
public class DateTimeResolverImpl implements DateTimeResolver {

  /**
   * {@inheritDoc}
   */
  @Override
  public LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now();
  }

}
