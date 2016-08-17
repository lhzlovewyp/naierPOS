/**
 * 
 */
package com.joker.common.model;

/**
 * @author lvhaizhen
 *
 */
public class MaterialProperty extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6452672286589370250L;

	private Client client;
	
	private Material material;
	
	private Color color;
	
	private Size size;
	
	private String barCode;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	
}
