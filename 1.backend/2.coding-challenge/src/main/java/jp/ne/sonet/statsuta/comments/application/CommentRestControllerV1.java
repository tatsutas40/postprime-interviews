package jp.ne.sonet.statsuta.comments.application;

import jp.ne.sonet.statsuta.comments.application.payload.CreateCommentRequest;
import jp.ne.sonet.statsuta.comments.application.payload.CreateCommentResponse;
import jp.ne.sonet.statsuta.comments.application.payload.UpdateCommentRequest;
import jp.ne.sonet.statsuta.comments.application.payload.UpdateCommentResponse;
import jp.ne.sonet.statsuta.comments.common.exception.BadRequestForCreateException;
import jp.ne.sonet.statsuta.comments.common.exception.BadRequestForUpdateException;
import jp.ne.sonet.statsuta.comments.common.exception.PathIdNumberFormatException;
import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import jp.ne.sonet.statsuta.comments.domain.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 特定のポストに対するコメントに関するコントローラクラスです。
 */
@RestController
@RequestMapping("/v1/comments")
public class CommentRestControllerV1 {

  @Autowired
  CommentService commentService;

  /**
   * ポストに対してコメントを作成するエンドポイント。
   *
   * @param request コメント作成のリクエスト
   * @param bindingResult リクエストパラメータのバインドの結果
   * @return コメント作成処理の結果のレスポンス。
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  CreateCommentResponse createComment(@RequestBody @Validated CreateCommentRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new BadRequestForCreateException();
    }

    Comment result = commentService.createComment(request.toModel());

    return CreateCommentResponse.of(result);
  }

  /**
   * コメントを更新するエンドポイント。
   *
   * @param id 更新するコメントを特定する ID
   * @param request コメント更新のリクエスト
   * @param bindingResult リクエストパラメータのバインドの結果
   * @return コメント更新処理の結果のレスポンス。
   */
  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  UpdateCommentResponse updateComment(@PathVariable("id") String id,
                                      @RequestBody @Validated UpdateCommentRequest request,
                                      BindingResult bindingResult) {
    if (bindingResult.hasErrors() || !request.validate()) {
      throw new BadRequestForUpdateException();
    }

    Comment result = commentService.updateComment(request.getUserId(), request.toModel(resolveId(id)));

    return UpdateCommentResponse.of(result);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteComment(@PathVariable("id") String id) {
    commentService.deleteComment(resolveId(id));
  }

  private Integer resolveId(String id) {
    Integer result = null;

    try {
      result = Integer.valueOf(id);

    } catch (NumberFormatException e) {
      throw new PathIdNumberFormatException();

    }

    return result;
  }

}
