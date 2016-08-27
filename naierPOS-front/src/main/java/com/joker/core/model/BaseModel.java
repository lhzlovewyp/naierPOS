package com.joker.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 定义基本的对象属性
 * 
 * @author lvhaizhen
 * 
 */
public class BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2439300154282480134L;
	// id 自增id
	private String id;
	// 创建者
	private String creator;
	// 创建时间
	private Date created;
	// 编辑者
	private String editor;
	// 编辑时间
	private String modified;
	
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
