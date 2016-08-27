package com.joker.common.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TreeNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7854396822935202407L;

	/** 节点ID **/
	private String id;
	/** 树节点名称 **/
	private String text;
	/** 父节点ID **/
	private String parentId;
	/** 子节点 **/
	private List<TreeNode> nodes;
	
	private Map<String,Object> data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}