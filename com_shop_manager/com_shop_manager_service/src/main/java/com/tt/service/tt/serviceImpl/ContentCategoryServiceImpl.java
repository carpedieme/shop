package com.tt.service.tt.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tt.mapper.TbContentCategoryMapper;
import com.tt.pojo.TbContent;
import com.tt.pojo.TbContentCategory;
import com.tt.pojo.TbContentCategoryExample;
import com.tt.pojo.TbContentExample;
import com.tt.service.ContentCategoryService;
import com.tt.util.EasyTreeNode;
import com.tt.util.EasyUIDateGrid;
import com.tt.util.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实现内容分类管理
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper categoryMapper;

    /**
     * 功能：接收parentid。根据parentid查询节点列表，
     * 返回一个EasyUI异步Tree要求的节点列表。每个节点包含三个属性id、text、state三个属性。可以使用EUTreeNode。
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyTreeNode> getCategory(long parentId) {
        //根据parentId查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria cr = example.createCriteria();
        cr.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = categoryMapper.selectByExample(example);
        List<EasyTreeNode> list1 = new ArrayList<>();
        for (TbContentCategory tb : list) {
            //创建一个节点
            EasyTreeNode node = new EasyTreeNode();
            node.setId(tb.getId());
            node.setText(tb.getName());
            node.setState(tb.getIsParent() ? "closed" : "open");
            list1.add(node);
        }
        return list1;
    }

    /**
     * 内容分类添加
     * 接收两个参数parentId父节点id、name：当前节点的名称。
     * 向tb_content_category表中添加一条记录。返回TaoTaoResult包含记录的pojo对象。
     *
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {
        //创建一个pojo
        TbContentCategory category = new TbContentCategory();
        //页面需要id,但是id时自动增长的，此时需要mysql中的主键返回
        category.setName(name);
        category.setIsParent(false);
        category.setSortOrder(1);
        //1正常  2删除
        category.setStatus(1);
        category.setParentId(parentId);
        category.setCreated(new Date());
        category.setCreated(new Date());
        //添加记录
        categoryMapper.insert(category);
        //查看父节点的isParent列是否为true，如果不是true改成true
        TbContentCategory parentCat = categoryMapper.selectByPrimaryKey(parentId);
        //判断是否为true
        if (!parentCat.getIsParent()) {
            parentCat.setIsParent(true);
            //更新父节点
            categoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回结果
        return TaotaoResult.ok(category);

    }

    /**
     * 内容分类删除
     *
     * @param parentId
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteContentCategory(long parentId, long id) {
        //删除节点
        TbContentCategory parentCat = categoryMapper.selectByPrimaryKey(parentId);
        if (!parentCat.getIsParent()) {
            parentCat.setIsParent(false);
        }
        categoryMapper.deleteByPrimaryKey(id);
        return TaotaoResult.ok();
    }

    /**
     * 更新节点
     *
     * @param id
     * @param name
     * @return
     */
    @Override
    public TaotaoResult updateContentCategory(long id, String name) {
        //先获取对象在更新
        TbContentCategory tb = categoryMapper.selectByPrimaryKey(id);
        tb.setName(name);
        categoryMapper.updateByPrimaryKey(tb);
        return TaotaoResult.ok();
    }

    /**
     * 根据内容分类id查询内容列表。需要实现分页。返回EUDataGridResult
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIDateGrid getContentCatgroyList(int page, int rows, long categoryId) {
        //根据categoryId查询拿到内容列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria cr = example.createCriteria();
        cr.andIdEqualTo(categoryId);
        //分页
        PageHelper.startPage(page, rows);
        List<TbContentCategory> list = categoryMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDateGrid eui=new EasyUIDateGrid();
        //将拿到的集合放到easyUIDateGrid中的list中
        eui.setRows(list);
        //取记录总条数
        PageInfo<TbContentCategory> pageInfo=new PageInfo<TbContentCategory>(list);
        eui.setTotal(pageInfo.getTotal());
        return eui;
    }
}
