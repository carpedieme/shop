package com.tt.controller;

import com.tt.service.ContentCategoryService;
import com.tt.util.EasyTreeNode;
import com.tt.util.EasyUIDateGrid;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {


    @Autowired
    private ContentCategoryService categoryService;

    /**
     * 接收页面传递过来的parentid，根据parentid查询节点列表。返回List<EUTreeNode>。需要响应json数据。
     *
     * @param parentId
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    private List<EasyTreeNode> getContengCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return categoryService.getCategory(parentId);
    }

    /**
     * 新增节点
     * 接收两个参数parentid、name。调用Service添加记录。返回TaotaoResult。应该返回json数据。
     *
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createCategory(Long parentId, String name) {
        return categoryService.insertContentCategory(parentId, name);
    }

    /**
     * 删除节点
     *接收parentid、id两个参数。删除id对应的记录。需要判断parentid对应的记录下是否有子节点。如果没有子节点。
     * 需要把parentid对应的记录的isparent改成false。注意：删除直接是物理删除。
     * @param parentId
     * @param id
     * @return
     */
    @RequestMapping("/delete/")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long parentId, Long id) {

        return categoryService.deleteContentCategory(parentId, id);

    }

    /**
     * 内容列表
     * 业务逻辑：根据内容分类id查询内容列表。需要实现分页。返回EUDataGridResult
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIDateGrid getContentList(Integer page, Integer rows, Long categoryId){
        return categoryService.getContentCatgroyList(page,rows,categoryId);
    }

    /**
     * 返回值：返回TaotaoResult。
     * Json格式业务逻辑：根据id更新记录的name列即可。
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id, String name) {
        return categoryService.updateContentCategory(id, name);
    }
}
