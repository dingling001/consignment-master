package com.bootdo.common.utils;

import com.bootdo.common.domain.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildDeptTree {

    public static <T> Tree<T> build(List<Tree<T>> nodes, String deptId, String deptName,String payment) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();
        for (Tree<T> children : nodes) {

            String pid = children.getParentId();

            if (pid == null || deptId.equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);
                    continue;
                }
            }

        }

        Tree<T> root = new Tree<T>();
//        if (topNodes.size() == 1) {
//            root = topNodes.get(0);
//        } else {
        if("1".equals(payment)){
            root.setId("-1");
        }else{
            root.setId(deptId);
        }
        root.setParentId("");
        root.setHasParent(false);
        root.setChildren(true);
        root.setChecked(true);
        root.setChildren(topNodes);
        root.setText(deptName);
        Map<String, Object> state = new HashMap<>(16);
        state.put("opened", true);
        root.setState(state);
//        }

        return root;
    }

    /**
     * 获取idParam下所有的直属子节点
     * @param nodes 所有节点
     * @param idParam 节点id
     * @param <T>
     * @return
     */
    public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();

        for (Tree<T> children : nodes) {

            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);

                    continue;
                }
            }

        }
        return topNodes;
    }

}