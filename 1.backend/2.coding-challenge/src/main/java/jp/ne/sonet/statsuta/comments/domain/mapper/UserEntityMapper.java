package jp.ne.sonet.statsuta.comments.domain.mapper;

import jp.ne.sonet.statsuta.comments.domain.model.User;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.UserEntity;

/**
 * ユーザのドメインモデルとエンティティを相互に変換を行うマッパー。
 */
public final class UserEntityMapper {

  private UserEntityMapper() {
    // Do Nothing.
  }

  public static User fromEntity(UserEntity userEntity) {
    return User.builder()
               .id(userEntity.getId())
               .firstName(userEntity.getFirstName())
               .lastName(userEntity.getLastName())
               .createdAt(userEntity.getCreatedAt().toLocalDateTime())
               .build();
  }

}
