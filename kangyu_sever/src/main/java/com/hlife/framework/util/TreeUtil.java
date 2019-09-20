package com.hlife.framework.util;

import com.hlife.framework.base.BaseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树操作
 */
public class TreeUtil {
    private TreeUtil() {
    }

    /**
     * 创建树结构数据
     *
     * @param listData 原list 数据
     * @param <T> 泛型
     * @return 树结构数据
     */
    public static <T extends BaseTree> List<T> createTree(List<T> listData) {
        List<T> treeData = new ArrayList<>();
        for (T baseTree : listData) {
            if (StringUtil.stringIsNull(baseTree.getParentId())) {
                @SuppressWarnings("unchecked")
                List<BaseTree> children = createTree((List<BaseTree>) listData, baseTree.getNodeId());
                baseTree.setChildren(children).setIsLeaf(children.isEmpty());
                treeData.add(baseTree);
            }
        }
        return treeData;
    }

    /**
     * 创建树结构数据
     *
     * @param listData 原list 数据
     * @param parentId 当前结点
     * @param <T> 泛型
     * @return 树结构数据
     */
    public static <T extends BaseTree> List<T> createTree(List<T> listData, String parentId) {
        List<T> treeData = new ArrayList<>();
        for (T baseTree : listData) {
            if (StringUtil.stringIsNotNull(baseTree.getParentId())
                    && baseTree.getParentId().equals(parentId)) {
                @SuppressWarnings("unchecked")
                List<BaseTree> children = createTree((List<BaseTree>) listData, baseTree.getNodeId());
                baseTree.setChildren(children).setIsLeaf(children.isEmpty());
                treeData.add(baseTree);
            }
        }
        return treeData;
    }

    public static void main(String[] args) {
        List<BaseTree> treeData = new ArrayList<BaseTree>() {
            {
                this.add(new BaseTree() {
                    {
                        this.setParentId("123").setNodeId("a");
                    }
                });
                this.add(new BaseTree() {
                    {
                        this.setParentId("123").setNodeId("b");
                    }
                });
                this.add(new BaseTree() {
                    {
                        this.setNodeId("123");
                    }
                });

                this.add(new BaseTree() {
                    {
                        this.setNodeId("456").setParentId("a");
                    }
                });

                this.add(new BaseTree() {
                    {
                        this.setNodeId("789").setParentId("b");
                    }
                });
            }
        };

        System.out.println(createTree(treeData));
    }
}
