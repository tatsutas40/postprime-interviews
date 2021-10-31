package jp.ne.sonet.statsuta.comments.domain.service;


import java.time.LocalDateTime;
import java.util.Optional;

import jp.ne.sonet.statsuta.comments.common.DateTimeResolver;
import jp.ne.sonet.statsuta.comments.common.exception.*;
import jp.ne.sonet.statsuta.comments.domain.mapper.CommentEntityMapper;
import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.CommentEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.PostEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.entity.UserEntity;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.CommentLikeRepository;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.CommentRepository;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.PostRepository;
import jp.ne.sonet.statsuta.comments.infrastructure.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

/**
 * {@link CommentService} の実装クラスです。
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  CommentLikeRepository commentLikeRepository;

  @Autowired
  DateTimeResolver dateTimeResolver;

  /**
   * {@inheritDoc}
   */
  @Override
  public Comment createComment(Comment comment) {
    Optional<PostEntity> postEntityOptional = postRepository.findById(comment.getPostId());
    PostEntity postEntity = postEntityOptional.orElseThrow(PostNotFoundException::new);

    Optional<UserEntity> userEntityOptional = userRepository.findById(comment.getCommentedBy().getId());
    UserEntity userEntity = userEntityOptional.orElseThrow(UserNotFoundException::new);

    LocalDateTime current = dateTimeResolver.getCurrentDateTime();
    CommentEntity commentEntity = CommentEntityMapper.toEntityForCreation(comment, current);

    CommentEntity createdCommentEntity = commentRepository.save(commentEntity);

    return CommentEntityMapper.fromEntity(createdCommentEntity, postEntity, userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Comment updateComment(Integer userId, Comment comment) {
    Optional<CommentEntity> commentEntityOptional = commentRepository.findById(comment.getId());
    CommentEntity commentEntity = commentEntityOptional.orElseThrow(CommentNotFoundException::new);

    Optional<UserEntity> updateUserEntityOptional = userRepository.findById(userId);
    updateUserEntityOptional.orElseThrow(UserNotFoundException::new);

    Optional<PostEntity> postEntityOptional = postRepository.findById(commentEntity.getPostId());
    PostEntity postEntity = postEntityOptional.orElseThrow(PostNotFoundException::new);

    if (nonNull(comment.getIsSpam()) && !userId.equals(postEntity.getPostedBy())) {
      throw new PostNotOwnerException();
    }

    if (nonNull(comment.getText()) && !userId.equals(commentEntity.getCommentedBy())) {
      throw new CommentNotOwnerException();
    }

    LocalDateTime current = dateTimeResolver.getCurrentDateTime();
    CommentEntity updateEntity = CommentEntityMapper.toEntityForUpdation(comment, current);
    CommentEntity toBeUpdateEntity = updateEntity.merge(commentEntity);

    CommentEntity updatedCommentEntity = commentRepository.save(toBeUpdateEntity);
    Optional<UserEntity> userEntityOptional = userRepository.findById(updatedCommentEntity.getCommentedBy());
    UserEntity userEntity = userEntityOptional.orElseThrow(UserNotFoundException::new);

    return CommentEntityMapper.fromEntity(updatedCommentEntity, postEntity, userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Transactional
  @Override
  public void deleteComment(Integer commentId) {
    Optional<CommentEntity> optional = commentRepository.findById(commentId);

    CommentEntity commentEntity = optional.orElseThrow(CommentNotFoundException::new);

    commentLikeRepository.deleteByCommentId(commentEntity.getId());
    commentRepository.deleteById(commentEntity.getId());
  }

}
