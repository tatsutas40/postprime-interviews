package jp.ne.sonet.statsuta.comments.application;

import static jp.ne.sonet.statsuta.comments.common.Constants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import jp.ne.sonet.statsuta.comments.application.payload.*;
import jp.ne.sonet.statsuta.comments.common.exception.*;
import jp.ne.sonet.statsuta.comments.domain.model.Comment;
import jp.ne.sonet.statsuta.comments.domain.model.User;
import jp.ne.sonet.statsuta.comments.domain.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerV1Test {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  CommentService commentService;

  ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
  }

  @Test
  void test_正常にコメントが作成できる場合() throws Exception {
    when(commentService.createComment(Comment.builder()
                                             .postId(1)
                                             .text("これはテストです")
                                             .commentedBy(User.builder().id(1).build())
                                             .isSpam(false)
                                             .build()))
            .thenReturn(Comment.builder()
                               .id(1)
                               .postId(1)
                               .text("これはテストです")
                               .commentedBy(User.builder()
                                                .id(1)
                                                .lastName("Yamada")
                                                .firstName("Taro")
                                                .build())
                               .isSpam(false)
                               .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                               .updatedAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                               .build());

    CreateCommentRequest request = CreateCommentRequest.builder()
                                                       .postId(1)
                                                       .text("これはテストです")
                                                       .commentedBy(1)
                                                       .build();
    CreateCommentResponse expected = CreateCommentResponse.builder()
                                                          .id(1)
                                                          .postId(1)
                                                          .text("これはテストです")
                                                          .commentedBy(UserPayload.builder()
                                                                                  .id(1)
                                                                                  .lastName("Yamada")
                                                                                  .firstName("Taro")
                                                                                  .build())
                                                          .isSpam(false)
                                                          .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                                                          .updatedAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                                                          .build();

    mockMvc.perform(post("/v1/comments")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント作成時に必須パラメータが指定されていない場合() throws Exception {
    CreateCommentRequest request = CreateCommentRequest.builder().build();
    ErrorResponse expected = new ErrorResponse(CREATE_FAILED_REQUIRED_PARAMETER);

    mockMvc.perform(post("/v1/comments")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント作成時にユーザが存在しない場合() throws Exception {
    when(commentService.createComment(Comment.builder()
                                             .postId(1)
                                             .text("これはテストです")
                                             .commentedBy(User.builder().id(1).build())
                                             .isSpam(false)
                                             .build()))
                                             .thenThrow(new UserNotFoundException());

    CreateCommentRequest request = CreateCommentRequest.builder()
                                                       .postId(1)
                                                       .text("これはテストです")
                                                       .commentedBy(1)
                                                       .build();
    ErrorResponse expected = new ErrorResponse(SPECIFIED_USER_NOT_FOUND);

    mockMvc.perform(post("/v1/comments")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント作成時にポストが存在しない場合() throws Exception {
    when(commentService.createComment(Comment.builder()
                                             .postId(1)
                                             .text("これはテストです")
                                             .commentedBy(User.builder().id(1).build())
                                             .isSpam(false)
                                             .build()))
                                             .thenThrow(new PostNotFoundException());

    CreateCommentRequest request = CreateCommentRequest.builder()
                                                       .postId(1)
                                                       .text("これはテストです")
                                                       .commentedBy(1)
                                                       .build();
    ErrorResponse expected = new ErrorResponse(SPECIFIED_POST_NOT_FOUND);

    mockMvc.perform(post("/v1/comments")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_正常にコメントが更新できる場合() throws Exception {
    when(commentService.updateComment(1, Comment.builder()
                                                .id(1)
                                                .text("これは更新テストです")
                                                .build()))
            .thenReturn(Comment.builder()
                               .id(1)
                               .postId(1)
                               .text("これは更新テストです")
                               .commentedBy(User.builder()
                                                .id(1)
                                                .lastName("Yamada")
                                                .firstName("Taro")
                                                .build())
                               .isSpam(false)
                               .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                               .updatedAt(LocalDateTime.parse("2021-10-31T03:31:50"))
                               .build());

    UpdateCommentRequest request = UpdateCommentRequest.builder().userId(1).text("これは更新テストです").build();
    UpdateCommentResponse expected = UpdateCommentResponse.builder()
                                                          .id(1)
                                                          .postId(1)
                                                          .text("これは更新テストです")
                                                          .commentedBy(UserPayload.builder()
                                                                                  .id(1)
                                                                                  .lastName("Yamada")
                                                                                  .firstName("Taro")
                                                                                  .build())
                                                          .isSpam(false)
                                                          .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                                                          .updatedAt(LocalDateTime.parse("2021-10-31T03:31:50"))
                                                          .build();

    mockMvc.perform(patch("/v1/comments/1")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_正常にコメントがスパムとして報告できる場合() throws Exception {
    when(commentService.updateComment(1, Comment.builder()
                                                .id(1)
                                                .isSpam(true)
                                                .build()))
            .thenReturn(Comment.builder()
                               .id(1)
                               .postId(1)
                               .text("これはテストです")
                               .commentedBy(User.builder()
                                                .id(1)
                                                .lastName("Yamada")
                                                .firstName("Taro")
                                                .build())
                               .isSpam(true)
                               .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                               .updatedAt(LocalDateTime.parse("2021-10-31T03:31:50"))
                               .build());

    UpdateCommentRequest request = UpdateCommentRequest.builder().userId(1).isSpam(true).build();
    UpdateCommentResponse expected = UpdateCommentResponse.builder()
                                                          .id(1)
                                                          .postId(1)
                                                          .text("これはテストです")
                                                          .commentedBy(UserPayload.builder()
                                                                                  .id(1)
                                                                                  .lastName("Yamada")
                                                                                  .firstName("Taro")
                                                                                  .build())
                                                          .isSpam(true)
                                                          .createdAt(LocalDateTime.parse("2021-10-31T01:31:50"))
                                                          .updatedAt(LocalDateTime.parse("2021-10-31T03:31:50"))
                                                          .build();

    mockMvc.perform(patch("/v1/comments/1")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント更新時に必須パラメータが指定されていない場合() throws Exception {
    UpdateCommentRequest request = UpdateCommentRequest.builder().build();
    ErrorResponse expected = new ErrorResponse(UPDATE_FAILED_REQUIRED_PARAMETER);

    mockMvc.perform(patch("/v1/comments/1")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント更新時にパスで指定されたIDが不正な場合() throws Exception {
    UpdateCommentRequest request = UpdateCommentRequest.builder().userId(1).isSpam(true).build();
    ErrorResponse expected = new ErrorResponse(PATH_ID_NUMBER_FORMAT_ERROR);

    mockMvc.perform(patch("/v1/comments/aaa")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント更新時に指定されたコメントが存在しない場合() throws Exception {
    when(commentService.updateComment(1, Comment.builder()
                                                .id(1)
                                                .isSpam(true)
                                                .build()))
                                                .thenThrow(new CommentNotFoundException());

    UpdateCommentRequest request = UpdateCommentRequest.builder().userId(1).isSpam(true).build();
    ErrorResponse expected = new ErrorResponse(SPECIFIED_COMMENT_NOT_FOUND);

    mockMvc.perform(patch("/v1/comments/1")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント更新時に指定された更新ユーザが存在しない場合() throws Exception {
    when(commentService.updateComment(1, Comment.builder()
                                                .id(1)
                                                .isSpam(true)
                                                .build()))
                                                .thenThrow(new UserNotFoundException());

    UpdateCommentRequest request = UpdateCommentRequest.builder().userId(1).isSpam(true).build();
    ErrorResponse expected = new ErrorResponse(SPECIFIED_USER_NOT_FOUND);

    mockMvc.perform(patch("/v1/comments/1")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント更新時に指定された更新ユーザがコメントのオーナーでない場合() throws Exception {
    when(commentService.updateComment(1, Comment.builder()
                                                .id(1)
                                                .text("これは更新テストです")
                                                .build()))
                                                .thenThrow(new CommentNotOwnerException());

    UpdateCommentRequest request = UpdateCommentRequest.builder().userId(1).text("これは更新テストです").build();
    ErrorResponse expected = new ErrorResponse(SPECIFIED_USER_NOT_COMMENT_OWNER);

    mockMvc.perform(patch("/v1/comments/1")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント更新時にスパムとして報告した際に指定された更新ユーザがポストのオーナーでない場合() throws Exception {
    when(commentService.updateComment(1, Comment.builder()
                                                .id(1)
                                                .isSpam(true)
                                                .build()))
                                                .thenThrow(new PostNotOwnerException());

    UpdateCommentRequest request = UpdateCommentRequest.builder().userId(1).isSpam(true).build();
    ErrorResponse expected = new ErrorResponse(SPECIFIED_USER_NOT_POST_OWNER);

    mockMvc.perform(patch("/v1/comments/1")
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_正常にコメントが削除できる場合() throws Exception {
    mockMvc.perform(delete("/v1/comments/1"))
            .andExpect(status().isNoContent());
  }

  @Test
  void test_コメント削除時にパスに指定されたIDが不正な場合() throws Exception {
    ErrorResponse expected = new ErrorResponse(PATH_ID_NUMBER_FORMAT_ERROR);

    mockMvc.perform(delete("/v1/comments/aaa"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

  @Test
  void test_コメント削除時に指定されたコメントが存在しない場合() throws Exception {
    doThrow(new CommentNotFoundException()).when(commentService).deleteComment(1);

    ErrorResponse expected = new ErrorResponse(SPECIFIED_COMMENT_NOT_FOUND);

    mockMvc.perform(delete("/v1/comments/1"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().json(mapper.writeValueAsString(expected)));
  }

}