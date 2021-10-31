package jp.ne.sonet.statsuta.comments.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import jp.ne.sonet.statsuta.comments.common.DateTimeResolver;
import jp.ne.sonet.statsuta.comments.common.exception.*;
import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import jp.ne.sonet.statsuta.comments.domain.model.User;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.CommentEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.PostEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.UserEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.CommentLikeRepository;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.CommentRepository;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.PostRepository;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommentServiceImplTest {

  @Autowired
  CommentService target;

  @MockBean
  UserRepository userRepository;

  @MockBean
  PostRepository postRepository;

  @MockBean
  CommentRepository commentRepository;

  @MockBean
  CommentLikeRepository commentLikeRepository;

  @MockBean
  DateTimeResolver dateTimeResolver;

  @Test
  void test_正常にコメントが作成できる場合() {
    when(postRepository.findById(1))
            .thenReturn(Optional.of(PostEntity.builder()
                                              .id(1)
                                              .text("これはポストです")
                                              .postedBy(2)
                                              .createdAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .updatedAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .build()));

    when(userRepository.findById(1))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(1)
                                              .lastName("Yamada")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(dateTimeResolver.getCurrentDateTime()).thenReturn(LocalDateTime.parse("2021-10-31T12:30:00"));

    when(commentRepository.save(CommentEntity.builder()
                                             .postId(1)
                                             .text("これはテストです。")
                                             .commentedBy(1)
                                             .isSpam(false)
                                             .createdAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                             .updatedAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                             .build()))
            .thenReturn(CommentEntity.builder()
                                     .id(1)
                                     .postId(1)
                                     .text("これはテストです。")
                                     .commentedBy(1)
                                     .isSpam(false)
                                     .createdAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                     .updatedAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                     .build());

    Comment comment = Comment.builder()
                             .postId(1)
                             .text("これはテストです。")
                             .commentedBy(User.builder()
                                              .id(1)
                                              .build())
                             .isSpam(false)
                             .build();
    Comment expected = Comment.builder()
                              .id(1)
                              .postId(1)
                              .text("これはテストです。")
                              .commentedBy(User.builder()
                                               .id(1)
                                               .lastName("Yamada")
                                               .firstName("Taro")
                                               .createdAt(LocalDateTime.parse("2021-10-30T00:00:00"))
                                               .build())
                              .isSpam(false)
                              .createdAt(LocalDateTime.parse("2021-10-31T12:30:00"))
                              .updatedAt(LocalDateTime.parse("2021-10-31T12:30:00"))
                              .build();
    Comment actual = target.createComment(comment);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void test_コメント作成時に指定されたIDのポストが存在しない場合() {
    when(postRepository.findById(1)).thenReturn(Optional.empty());

    Comment comment = Comment.builder()
                             .postId(1)
                             .text("これはテストです。")
                             .commentedBy(User.builder()
                                              .id(1)
                                              .build())
                             .isSpam(false)
                             .build();
    assertThrows(PostNotFoundException.class, () -> target.createComment(comment));
  }

  @Test
  void test_コメント作成時に指定されたIDのユーザが存在しない場合() {
    when(postRepository.findById(1))
            .thenReturn(Optional.of(PostEntity.builder()
                                              .id(1)
                                              .text("これはポストです")
                                              .postedBy(2)
                                              .createdAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .updatedAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .build()));

    when(userRepository.findById(1)).thenReturn(Optional.empty());

    Comment comment = Comment.builder()
                             .postId(1)
                             .text("これはテストです。")
                             .commentedBy(User.builder()
                                              .id(1)
                                              .build())
                             .isSpam(false)
                             .build();
    assertThrows(UserNotFoundException.class, () -> target.createComment(comment));
  }


  @Test
  void test_正常にコメントが更新できる場合() {
    when(commentRepository.findById(1))
            .thenReturn(Optional.of(CommentEntity.builder()
                                                 .id(1)
                                                 .postId(1)
                                                 .text("これはテストです。")
                                                 .commentedBy(1)
                                                 .isSpam(false)
                                                 .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .updatedAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .build()));

    when(userRepository.findById(1))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(1)
                                              .lastName("Yamada")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(postRepository.findById(1))
            .thenReturn(Optional.of(PostEntity.builder()
                                              .id(1)
                                              .text("これはポストです")
                                              .postedBy(2)
                                              .createdAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .updatedAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .build()));

    when(dateTimeResolver.getCurrentDateTime()).thenReturn(LocalDateTime.parse("2021-10-31T12:30:00"));

    when(commentRepository.save(CommentEntity.builder()
                                             .id(1)
                                             .postId(1)
                                             .text("これは更新テストです。")
                                             .commentedBy(1)
                                             .isSpam(false)
                                             .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                             .updatedAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                             .build()))
            .thenReturn(CommentEntity.builder()
                                     .id(1)
                                     .postId(1)
                                     .text("これは更新テストです。")
                                     .commentedBy(1)
                                     .isSpam(false)
                                     .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                     .updatedAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                     .build());

    Comment comment = Comment.builder()
                             .id(1)
                             .text("これは更新テストです。")
                             .build();
    Comment expected = Comment.builder()
                              .id(1)
                              .postId(1)
                              .text("これは更新テストです。")
                              .commentedBy(User.builder()
                                               .id(1)
                                               .lastName("Yamada")
                                               .firstName("Taro")
                                               .createdAt(LocalDateTime.parse("2021-10-30T00:00:00"))
                                               .build())
                              .isSpam(false)
                              .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                              .updatedAt(LocalDateTime.parse("2021-10-31T12:30:00"))
                              .build();
    Comment actual = target.updateComment(1, comment);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void test_正常にコメントをスパムとして報告できる場合() {
    when(commentRepository.findById(1))
            .thenReturn(Optional.of(CommentEntity.builder()
                                                 .id(1)
                                                 .postId(1)
                                                 .text("これはテストです。")
                                                 .commentedBy(1)
                                                 .isSpam(false)
                                                 .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .updatedAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .build()));

    when(userRepository.findById(1))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(1)
                                              .lastName("Yamada")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(userRepository.findById(2))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(2)
                                              .lastName("Momo")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(postRepository.findById(1))
            .thenReturn(Optional.of(PostEntity.builder()
                    .id(1)
                    .text("これはポストです")
                    .postedBy(2)
                    .createdAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                    .updatedAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                    .build()));

    when(dateTimeResolver.getCurrentDateTime()).thenReturn(LocalDateTime.parse("2021-10-31T12:30:00"));

    when(commentRepository.save(CommentEntity.builder()
                                             .id(1)
                                             .postId(1)
                                             .text("これはテストです。")
                                             .commentedBy(1)
                                             .isSpam(true)
                                             .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                             .updatedAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                             .build()))
            .thenReturn(CommentEntity.builder()
                                     .id(1)
                                     .postId(1)
                                     .text("これはテストです。")
                                     .commentedBy(1)
                                     .isSpam(true)
                                     .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                     .updatedAt(Timestamp.valueOf("2021-10-31 12:30:00"))
                                     .build());

    Comment comment = Comment.builder()
                             .id(1)
                             .isSpam(true)
                             .build();
    Comment expected = Comment.builder()
                              .id(1)
                              .postId(1)
                              .text("これはテストです。")
                              .commentedBy(User.builder()
                                               .id(1)
                                               .lastName("Yamada")
                                               .firstName("Taro")
                                               .createdAt(LocalDateTime.parse("2021-10-30T00:00:00"))
                                               .build())
                              .isSpam(true)
                              .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                              .updatedAt(LocalDateTime.parse("2021-10-31T12:30:00"))
                              .build();
    Comment actual = target.updateComment(2, comment);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void test_コメント更新時に指定されたIDのコメントが存在しない場合() {
    when(commentRepository.findById(1))
            .thenReturn(Optional.of(CommentEntity.builder()
                                                 .id(1)
                                                 .postId(1)
                                                 .text("これはテストです。")
                                                 .commentedBy(1)
                                                 .isSpam(false)
                                                 .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .updatedAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .build()));

    when(userRepository.findById(1)).thenReturn(Optional.empty());

    Comment comment = Comment.builder()
                             .id(1)
                             .text("これは更新テストです。")
                             .build();

    assertThrows(UserNotFoundException.class, () -> target.updateComment(1, comment));
  }

  @Test
  void test_コメント更新時に指定されたIDのユーザが存在しない場合() {
    when(commentRepository.findById(1)).thenReturn(Optional.empty());

    Comment comment = Comment.builder()
                             .id(1)
                             .text("これは更新テストです。")
                             .build();

    assertThrows(CommentNotFoundException.class, () -> target.updateComment(1, comment));
  }

  @Test
  void test_コメント更新時に指定された更新ユーザがコメントのオーナーでない場合() {
    when(commentRepository.findById(1))
            .thenReturn(Optional.of(CommentEntity.builder()
                                                 .id(1)
                                                 .postId(1)
                                                 .text("これはテストです。")
                                                 .commentedBy(1)
                                                 .isSpam(false)
                                                 .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .updatedAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .build()));

    when(userRepository.findById(1))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(1)
                                              .lastName("Yamada")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(userRepository.findById(2))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(2)
                                              .lastName("Momo")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(postRepository.findById(1))
            .thenReturn(Optional.of(PostEntity.builder()
                                              .id(1)
                                              .text("これはポストです")
                                              .postedBy(2)
                                              .createdAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .updatedAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .build()));

    Comment comment = Comment.builder()
                             .id(1)
                             .text("これは更新テストです。")
                             .build();

    assertThrows(CommentNotOwnerException.class, () -> target.updateComment(2, comment));
  }

  @Test
  void test_コメント更新時にスパムとして報告した際に指定された更新ユーザがポストのオーナーでない場合() {
    when(commentRepository.findById(1))
            .thenReturn(Optional.of(CommentEntity.builder()
                                                 .id(1)
                                                 .postId(1)
                                                 .text("これはテストです。")
                                                 .commentedBy(1)
                                                 .isSpam(false)
                                                 .createdAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .updatedAt(Timestamp.valueOf("2021-10-31 01:31:50"))
                                                 .build()));

    when(userRepository.findById(1))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(1)
                                              .lastName("Yamada")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(userRepository.findById(2))
            .thenReturn(Optional.of(UserEntity.builder()
                                              .id(2)
                                              .lastName("Momo")
                                              .firstName("Taro")
                                              .createdAt(Timestamp.valueOf("2021-10-30 00:00:00"))
                                              .build()));

    when(postRepository.findById(1))
            .thenReturn(Optional.of(PostEntity.builder()
                                              .id(1)
                                              .text("これはポストです")
                                              .postedBy(2)
                                              .createdAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .updatedAt(Timestamp.valueOf("2021-10-30 09:30:00"))
                                              .build()));

    Comment comment = Comment.builder()
                             .id(1)
                             .isSpam(true)
                             .build();

    assertThrows(PostNotOwnerException.class, () -> target.updateComment(1, comment));
  }

  @Test
  void test_正常にコメントが削除できる場合() {
    when(commentRepository.findById(1))
            .thenReturn(Optional.of(CommentEntity.builder()
                                                 .id(1)
                                                 .postId(1)
                                                 .text("これはテストです")
                                                 .commentedBy(1)
                                                 .isSpam(false)
                                                 .createdAt(Timestamp.valueOf("2021-10-31 01:30:00"))
                                                 .updatedAt(Timestamp.valueOf("2021-10-31 01:30:00"))
                                                 .build()));

    target.deleteComment(1);

    verify(commentLikeRepository).deleteByCommentId(1);
    verify(commentRepository).deleteById(1);
  }

  @Test
  void test_削除対象のコメントが存在しない場合() {
    when(commentRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(CommentNotFoundException.class, () -> target.deleteComment(1));

    verify(commentLikeRepository, never()).deleteByCommentId(1);
    verify(commentRepository, never()).deleteById(1);
  }

}