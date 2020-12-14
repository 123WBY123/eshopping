package com.jd.edu.controller.front;


import com.jd.edu.entity.Comment;
import com.jd.edu.service.CommentService;
import com.jd.edu.utils.R;
import com.jd.edu.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ysc666
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/edu/front/comment")
@CrossOrigin
public class CommentFrontController {

    @Autowired
    private CommentService commentService;


    //添加
    @PostMapping("addComment/{commodityId}")
    public R addComment(@PathVariable String commodityId, @RequestParam("content") String content, HttpServletRequest request){
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setCommodityId(commodityId);
        boolean save = commentService.save(comment);
        //boolean save = commentService.saveCom(commodityId,content,userId);
        return save ? R.ok() : R.error();
    }



    //根据商品ID查询
    @GetMapping("selectByCom/{commodityId}")
    public R selectByCom(@PathVariable String commodityId){
        List<Comment>comment = commentService.selectByCom(commodityId);

        return R.ok().data("comment",comment);
    }




}

