package jp.ne.sonet.statsuta.comments.common;

/**
 * 共有の定数クラス。
 */
public class Constants {


  public static final ErrorCode UNEXPECTED_ERROR
          = ErrorCode.builder()
                     .code("0000")
                     .message("unexpected error is occurred.")
                     .build();

  public static final ErrorCode CREATE_FAILED_REQUIRED_PARAMETER
          = ErrorCode.builder()
                     .code("0001")
                     .message("Comment creation failed. Required parameters are [postId, text, commentedBy].")
                     .build();

  public static final ErrorCode UPDATE_FAILED_REQUIRED_PARAMETER
          = ErrorCode.builder()
                     .code("0002")
                     .message("Comment updation failed. Required parameters are [userId, text, isSpam].")
                     .build();

  public static final ErrorCode SPECIFIED_USER_NOT_FOUND
          = ErrorCode.builder()
                     .code("0003")
                     .message("Specified user is not found.")
                     .build();

  public static final ErrorCode SPECIFIED_POST_NOT_FOUND
          = ErrorCode.builder()
                     .code("0004")
                     .message("Specified post is not found.")
                     .build();

  public static final ErrorCode SPECIFIED_COMMENT_NOT_FOUND
          = ErrorCode.builder()
                     .code("0005")
                     .message("Specified comment is not found.")
                     .build();

  public static final ErrorCode PATH_ID_NUMBER_FORMAT_ERROR
          = ErrorCode.builder()
                     .code("0006")
                     .message("Specified id is not number format.")
                     .build();

  public static final ErrorCode SPECIFIED_USER_NOT_POST_OWNER
          = ErrorCode.builder()
                     .code("0007")
                     .message("Specified user is not post owner.")
                     .build();

  public static final ErrorCode SPECIFIED_USER_NOT_COMMENT_OWNER
          = ErrorCode.builder()
                     .code("0008")
                     .message("Specified user is not comment owner.")
                     .build();

}
