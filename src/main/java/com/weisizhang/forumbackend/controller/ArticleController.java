package com.weisizhang.forumbackend.controller;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.config.AppConfig;
import com.weisizhang.forumbackend.exception.ApplicationException;
import com.weisizhang.forumbackend.model.Article;
import com.weisizhang.forumbackend.model.User;
import com.weisizhang.forumbackend.services.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Tag(name = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    IArticleService articleService;

    @PostMapping("/create")
    @Operation(summary = "创建新贴子")
    public AppResult create(HttpServletRequest request,
                            @Parameter(description = "板块id") @RequestParam("boardId") @NonNull Long boardId,
                            @Parameter(description = "文章标题") @RequestParam("title") @NonNull String title,
                            @Parameter(description = "文章内容") @RequestParam("content") @NonNull String content
    ) {
        //获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //判断是否被禁言
        if (user.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        //构造贴子对象
        Article article = new Article();
        article.setBoardId(boardId);
        article.setUserid(user.getId());
        article.setTitle(title);
        article.setContent(content);
        //调用Service
        articleService.create(article);
        return AppResult.success();
    }

    @GetMapping("/getAll")
    @Operation(summary = "根据板块ID查询帖子列表，不传ID则查询全部")
    public AppResult<List<Article>> getAllByBoardId(
            @Parameter(description = "板块ID（可选，不传查询全部）")
            @RequestParam(value = "boardId", required = false)
            Long boardId
    ) {
        List<Article> result;

        if (boardId == null) {
            result = articleService.selectAll();
            log.info("查询全部板块帖子");
        } else {
            result = articleService.selectAllByBoardId(boardId);
            log.info("查询板块帖子, boardId = {}", boardId);
        }

        // 永远不返回 null，避免前端报错
        result = Objects.requireNonNullElse(result, new ArrayList<>());

        return AppResult.success(result);
    }


    @GetMapping("/details")
    @Operation(summary = "根据贴子ID查询帖子详情")
    public AppResult<Article> getDetails(
            HttpServletRequest request,
            @Parameter(description = "贴子ID")
            @RequestParam(value = "id")
            @NonNull
            Long id
    ) {
        //获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);

        //Service
        Article article = articleService.selectDetailById(id);

        if (user.getId().equals(article.getUserid())) {
            article.setOwn(true);
        }
        return  AppResult.success(article);
    }

    @PostMapping("/modify")
    @Operation(summary = "更新帖子内容")
    public AppResult modify(HttpServletRequest request,
                            @Parameter(description = "贴子ID")
                            @RequestParam(value = "id")
                            Long id,
                            @Parameter(description = "贴子标题")
                                @RequestParam(value = "title")
                                String title,
                            @Parameter(description = "贴子内容")
                                @RequestParam(value = "content")
                                String content
                            ){
        //获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //判断是否禁言
        if (user.getState() == 1) {
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        //获取帖子数据
        Article article = articleService.selectDetailById(id);

        //校验用户是否是发帖人
        if (article == null){
            log.warn(MessageFormat.format("发帖人不是当前用户. article id = {0},article user id = {1}, " + "current user id = {2}", article.getId(), article.getUserid(), user.getId()));
            return AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE);
        }
        //校验帖子状态
        if (article.getState() != 0 || article.getDeleteState() != 0){
            log.warn("帖子状态异常. articleId = " + id + ", state = " +
                    article.getState() + ", delete state = " + article.getDeleteState());
            return AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE);
        }

        //DAO, 更新
        articleService.modify(id, title, content);
        log.info(MessageFormat.format("帖子修改成功. id = {0}, user id = {1}, 原标题 = {2}, 新标题 = {3}",
                id, user.getId(), article.getTitle(), title));
        //返回结果
        return AppResult.success();
    }

    @PostMapping("/thumbsUp")
    @Operation(summary = "点赞")
    public AppResult thumbsUp(HttpServletRequest request,
                              @Parameter(description = "帖子Id")
                              @RequestParam(value="id")
                                      @NonNull
                              Long id
                              ){
        //获取用户信息判断是否禁言
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if (user.getState() == 1) {
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        articleService.thumbsUpById(id);

        return AppResult.success();

    }
}