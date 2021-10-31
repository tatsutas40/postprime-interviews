package jp.ne.sonet.statsuta.comments.common.handler;

import static jp.ne.sonet.statsuta.comments.common.Constants.*;

import jp.ne.sonet.statsuta.comments.application.payload.ErrorResponse;
import jp.ne.sonet.statsuta.comments.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * レシピ REST API で、例外をハンドリングして、エラーメッセージを送出するクラスです。
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   *  コメント作成時の必須パラメータが指定されていない場合の例外をハンドルします。
   *
   * @param e コメント作成時の必須パラメータが指定されていない場合の例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(BadRequestForCreateException.class)
  public static ResponseEntity<ErrorResponse> handleBadRequestForCreateException(BadRequestForCreateException e) {
    return new ResponseEntity<>(new ErrorResponse(CREATE_FAILED_REQUIRED_PARAMETER), HttpStatus.BAD_REQUEST);
  }

  /**
   *  コメント更新時の必須パラメータが指定されていない場合の例外をハンドルします。
   *
   * @param e コメント更新時の必須パラメータが指定されていない場合の例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(BadRequestForUpdateException.class)
  public static ResponseEntity<ErrorResponse> handleBadRequestForUpdatexception(BadRequestForUpdateException e) {
    return new ResponseEntity<>(new ErrorResponse(UPDATE_FAILED_REQUIRED_PARAMETER), HttpStatus.BAD_REQUEST);
  }

  /**
   *  指定されたユーザが存在しない場合の例外をハンドルします。
   *
   * @param e 指定されたユーザが存在しない場合の例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(UserNotFoundException.class)
  public static ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    return new ResponseEntity<>(new ErrorResponse(SPECIFIED_USER_NOT_FOUND), HttpStatus.BAD_REQUEST);
  }

  /**
   *  指定されたポストが存在しない場合の例外をハンドルします。
   *
   * @param e 指定されたポストが存在しない場合の例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(PostNotFoundException.class)
  public static ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
    return new ResponseEntity<>(new ErrorResponse(SPECIFIED_POST_NOT_FOUND), HttpStatus.BAD_REQUEST);
  }

  /**
   *  指定されたコメントが存在しない場合の例外をハンドルします。
   *
   * @param e 指定されたコメントが存在しない場合の例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(CommentNotFoundException.class)
  public static ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
    return new ResponseEntity<>(new ErrorResponse(SPECIFIED_COMMENT_NOT_FOUND), HttpStatus.BAD_REQUEST);
  }

  /**
   *  パスに指定された ID のフォーマット例外をハンドルします。
   *
   * @param e パスに指定された ID のフォーマット例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(PathIdNumberFormatException.class)
  public static ResponseEntity<ErrorResponse> handlePathIdNumberFormatException(PathIdNumberFormatException e) {
    return new ResponseEntity<>(new ErrorResponse(PATH_ID_NUMBER_FORMAT_ERROR), HttpStatus.BAD_REQUEST);
  }

  /**
   *  指定されたユーザがポストの作成者でない場合の例外をハンドルします。
   *
   * @param e 指定されたユーザがポストの作成者でない場合の例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(PostNotOwnerException.class)
  public static ResponseEntity<ErrorResponse> handlePostNotOwnerException(PostNotOwnerException e) {
    return new ResponseEntity<>(new ErrorResponse(SPECIFIED_USER_NOT_POST_OWNER), HttpStatus.BAD_REQUEST);
  }

  /**
   *  指定されたユーザがコメントの作成者でない場合の例外をハンドルします。
   *
   * @param e 指定されたユーザがコメントの作成者でない場合の例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(CommentNotOwnerException.class)
  public static ResponseEntity<ErrorResponse> handleCommentNotOwnerException(CommentNotOwnerException e) {
    return new ResponseEntity<>(new ErrorResponse(SPECIFIED_USER_NOT_COMMENT_OWNER), HttpStatus.BAD_REQUEST);
  }

  /**
   * 予期しない例外をハンドルします。
   *
   * @param e 予期しない例外
   * @return エラーレスポンスの ResponseEntity
   */
  @ExceptionHandler(Exception.class)
  public static ResponseEntity<ErrorResponse> handleAllException(Exception e) {
    log.error(UNEXPECTED_ERROR.getMessage(), e);
    return new ResponseEntity<>(new ErrorResponse(UNEXPECTED_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
