package com.joker.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.joker.common.model.Color;
import com.joker.common.model.Size;

/**
 * 
 * 把颜色和尺寸转换为在前台比较容易显示的结构.
 * @author lvhaizhen
 *
 */
public class AttrDto {
	
	private Color color;
	
	private List<Size> sizes;
	
	public void addSize(Size e){
		if(sizes==null){
			sizes=new ArrayList<Size>();
			
		}
		sizes.add(e);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public List<Size> getSizes() {
		return sizes;
	}

	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}
	
	
}
