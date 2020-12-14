package com.jd.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jd.edu.entity.Comment;
import com.jd.edu.entity.vo.CommentQuery;
import com.jd.edu.service.CommentService;
import com.jd.edu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/edu/back/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    //删除
    @DeleteMapping("{id}")
    public R removeComment(@PathVariable String id) {
        boolean flag = commentService.removeById(id);
        return flag ? R.ok() : R.error();
    }
    //添加
    @PostMapping("addComment")
    public R addComment(@RequestBody Comment comment){
        boolean save = commentService.save(comment);
        return save ? R.ok() : R.error();
    }
    //点赞
    @GetMapping("countAdd/{content}")
    public R countAdd(@PathVariable String content){
        int result = commentService.countAdd(content);
        return result == 1 ? R.ok() : R.error();
    }
    //查询单个数据
    @GetMapping("selectOne/{commentId}")
    public R selectOne(@PathVariable String commentId){
        Comment comment = commentService.selectOne(commentId);
        System.out.println(comment);
        return R.ok().data("comment",comment);
    }


    //根据商品ID查询
    @GetMapping("selectByCom/{commodityId}")
    public R selectByCom(@PathVariable String commodityId){
        List<Comment>comment = commentService.selectByCom(commodityId);
        System.out.println(comment);
        return R.ok().data("comment",comment);
    }



    //更新数据
    @PostMapping("updateOne")
    public R updateOne(@RequestBody Comment comment){
        boolean result = commentService.updateById(comment);
        return result ? R.ok() : R.error();
    }


    //分页查询
    @PostMapping("pageComment/{current}/{limit}")
    public R pageListComment(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) CommentQuery commentQuery){
        System.out.println(commentQuery);
        //创建page对象
        Page<Comment> pageComment = new Page<>(current,limit);
        List<Comment> records = commentService.selectAllComment(pageComment,commentQuery);//数据list集合
        long total = pageComment.getTotal();//总记录数
        return R.ok().data("total",total).data("rows",records);
    }
}

