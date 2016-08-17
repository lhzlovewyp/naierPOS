package com.joker.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.joker.common.model.TreeNode;

/**
 * Created by zhangfei on 2016/7/24.
 */
public class TreeUtil {

	public static TreeNode makeTree(List<TreeNode> treeNodes,
			String defaultRootId) {
		if (StringUtils.isBlank(defaultRootId)) {
			defaultRootId = "0";
		}
		TreeNode rootNode = new TreeNode();
		rootNode.setId(defaultRootId);
		rootNode.setText("all");

		// 存储所有节点，以节点ID为key
		Map<String, TreeNode> allNodeMap = new HashMap<String, TreeNode>();
		// 存储未找到父节点的节点，以父节点ID为key
		Map<String, List<TreeNode>> parentNodeMap = new HashMap<String, List<TreeNode>>();
		// 遍历权限树，生成菜单树
		for (TreeNode treeNode : treeNodes) {
			if (treeNode != null && StringUtils.isNotBlank(treeNode.getId())
					&& StringUtils.isNotBlank(treeNode.getText())) {
				String id = treeNode.getId();
				String parentId = treeNode.getParentId();
				String text = treeNode.getText();
				TreeNode newTreeNode = new TreeNode();
				newTreeNode.setId(id);
				newTreeNode.setText(text);
				newTreeNode.setData(treeNode.getData());

				// 标记节点
				allNodeMap.put(id, newTreeNode);

				if (StringUtils.isBlank(parentId)
						|| defaultRootId.equals(parentId)) {
					// 根节点
					List<TreeNode> childNodeList = parentNodeMap
							.get(defaultRootId);
					if (childNodeList == null) {
						childNodeList = new ArrayList<TreeNode>();
						parentNodeMap.put(defaultRootId, childNodeList);
					}
					childNodeList.add(newTreeNode);

					// 查找parentNodeMap中是否有本节点的系列孩子节点
					if (parentNodeMap.containsKey(id)) {
						newTreeNode.setNodes(parentNodeMap.get(id));
					}
				} else if (allNodeMap.containsKey(parentId)) {
					// 如果能找到父母节点，则将此节点挂进去
					TreeNode parentNode = allNodeMap.get(parentId);
					List<TreeNode> childNodeList = parentNode.getNodes();
					if (childNodeList == null) {
						childNodeList = new ArrayList<TreeNode>();
						parentNode.setNodes(childNodeList);
					}
					childNodeList.add(newTreeNode);
				} else {
					List<TreeNode> childNodeList = null;
					if (parentNodeMap.containsKey(parentId)) {
						childNodeList = parentNodeMap.get(parentId);
					} else {
						childNodeList = new ArrayList<TreeNode>();
					}
					childNodeList.add(newTreeNode);

					// 查找是否有自己的孩子节点
					if (parentNodeMap.containsKey(id)) {
						newTreeNode.setNodes(parentNodeMap.get(id));
					}
					parentNodeMap.put(parentId, childNodeList);
				}
			}
		}
		rootNode.setNodes(parentNodeMap.get(defaultRootId));
		return rootNode;
	}
}