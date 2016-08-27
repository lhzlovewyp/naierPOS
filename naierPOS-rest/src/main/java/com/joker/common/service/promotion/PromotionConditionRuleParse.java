/**
 * 
 */
package com.joker.common.service.promotion;

/**
 * 促销条件是否匹配.
 * 
 * @author lvhaizhen
 *
 */
public interface PromotionConditionRuleParse {

	//规则是否匹配.
	public boolean validate();
}
