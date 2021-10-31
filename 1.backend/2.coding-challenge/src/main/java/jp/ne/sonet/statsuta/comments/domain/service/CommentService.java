package jp.ne.sonet.statsuta.comments.domain.service;

import jp.ne.sonet.statsuta.comments.domain.model.Comment;

public interface CommentService {

  /**
   * 指定されたポストにコメントを作成します。
   *
   * @param comment 作成するコメント
   * @return 作成されたコメントのモデル。
   */
  Comment createComment(Comment comment);

  /**
   * 指定されたコメントを更新します。
   *
   * @param userId コメントを更新するユーザーの ID
   * @param comment 更新するコメント
   * @return 更新されたコメントのモデル
   */
  Comment updateComment(Integer userId, Comment comment);

  /**
   * 指定された ID のコメントを削除します。
   *
   * @param commentId 削除するコメントの ID
   */
  void deleteComment(Integer commentId);

}
