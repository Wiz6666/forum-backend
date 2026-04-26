package com.weisizhang.forumbackend.controller;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import com.weisizhang.forumbackend.config.AppConfig;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "文章接口")
@RestController
@RequestMapping("/board")
public class ArticleController {

    @Resource
    IArticleService articleService;

    @PostMapping("/create")
    @Operation(summary = "创建新贴子")
    public AppResult create(HttpServletRequest request,
                            @Parameter(description="板块id") @RequestParam("boardId") @NonNull Long boardId,
                            @Parameter(description="文章标题") @RequestParam("title") @NonNull String title,
                            @Parameter(description="文章内容") @RequestParam("content") @NonNull String content
                            ) {
        //获取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        //判断是否被禁言
        if (user.getState()==1){
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
}
