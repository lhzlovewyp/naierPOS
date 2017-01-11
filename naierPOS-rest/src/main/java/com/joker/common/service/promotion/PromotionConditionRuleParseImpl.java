package com.joker.common.service.promotion;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.joker.common.Constant.Constants;
import com.joker.common.dto.SaleDto;
import com.joker.common.dto.SaleInfo;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionConditionMatchContent;

/**
 * 
 * 当前商品是否符合当前促销活动.
 * 
 * @author lvhaizhen
 *
 */
public class PromotionConditionRuleParseImpl implements PromotionConditionRuleParse{

	//促销条件.
	private PromotionCondition promotionCondition;
	
	private SaleDto saleDto;
	
	private List<SaleInfo> saleInfos;
	
	public PromotionConditionRuleParseImpl(PromotionCondition promotionCondition,SaleDto saleDto){
		this.promotionCondition=promotionCondition;
		this.saleDto=saleDto;
		this.saleInfos=saleDto.getSaleInfos();
	}
	
	
	@Override
	public boolean validate() {
		
		if(promotionCondition.getConditionType().equals(Constants.PROMOTION_CONDITION_MATQTY)){//商品数量
			return matQtyValidate();
		}else if(promotionCondition.getConditionType().equals(Constants.PROMOTION_CONDITION_MATAMT)){//商品价格
			return matAmtValidate();
		}else if(promotionCondition.getConditionType().equals(Constants.PROMOTION_CONDITION_TTLAMT)){//整单金额
			return ttlAmtValidate();
		}
		
		return false;
	}
	
	//商品数量校验.
	private boolean matQtyValidate(){
		int num=0;
		if(promotionCondition.getMatch().equals(Constants.PROMOTION_CONDITION_MATCH_ANY)){
			//any的情况下，只需要商品数量达标即可.
			num=saleDto.getTotalNum();
		}else if(promotionCondition.getMatch().equals(Constants.PROMOTION_CONDITION_MATCH_SAME)){
			if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MATCAT)){//类目
					if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
						for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
							for(SaleInfo info:saleInfos){
								if(StringUtils.isEmpty(info.getDiscType()) && info.getCategory().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
									num+=info.getCount();
								}
							}
						}
					}
			}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_BRAND)){//品牌
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
						for(SaleInfo info:saleInfos){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getBrand().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								num+=info.getCount();
							}
						}
					}
				}
			}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MAT)){//物料
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
						for(SaleInfo info:saleInfos){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								num+=info.getCount();
							}
						}
					}
				}
				
			}
		}else if(promotionCondition.getMatch().equals(Constants.PROMOTION_CONDITION_MATCH_DIFF)){
			if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MATCAT)){//类目
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(SaleInfo info:saleInfos){
						for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getCategory().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								continue;
							}
						}
						num+=info.getCount();
					}
				}
			
		}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_BRAND)){//品牌
			if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
				for(SaleInfo info:saleInfos){
					for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
						if(StringUtils.isEmpty(info.getDiscType()) && info.getBrand().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
							continue;
						}
					}
					num+=info.getCount();
				}
			}
			
		}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MAT)){//物料
			if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
				for(SaleInfo info:saleInfos){
					for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
						if(StringUtils.isEmpty(info.getDiscType()) && info.getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
							continue;
						}
					}
					num+=info.getCount();
				}
			}
			
			
		}
		}
		return num>=promotionCondition.getCondition().doubleValue();
	}
	
	//商品金额校验
	private boolean matAmtValidate(){
		BigDecimal amount=new BigDecimal(0);
		if(promotionCondition.getMatch().equals(Constants.PROMOTION_CONDITION_MATCH_ANY)){
			
			amount=saleDto.getTotalPrice().add(saleDto.getPromotionAmount());
		}else if(promotionCondition.getMatch().equals(Constants.PROMOTION_CONDITION_MATCH_SAME)){
			if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MATCAT)){//类目
					if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
						for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
							for(SaleInfo info:saleInfos){
								if(StringUtils.isEmpty(info.getDiscType()) && info.getCategory().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
									amount=amount.add(info.getSaleInfoTotalPrice());
								}
							}
						}
					}
			}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_BRAND)){//品牌
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
						for(SaleInfo info:saleInfos){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getBrand().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								amount=amount.add(info.getSaleInfoTotalPrice());
							}
						}
					}
				}
			}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MAT)){//物料
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
						for(SaleInfo info:saleInfos){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								amount=amount.add(info.getSaleInfoTotalPrice());
							}
						}
					}
				}
				
			}
		}else if(promotionCondition.getMatch().equals(Constants.PROMOTION_CONDITION_MATCH_DIFF)){
			if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MATCAT)){//类目
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(SaleInfo info:saleInfos){
						for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getCategory().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								continue;
							}
						}
						amount=amount.add(info.getSaleInfoTotalPrice());
					}
				}
			
			}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_BRAND)){//品牌
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(SaleInfo info:saleInfos){
						for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getBrand().getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								continue;
							}
						}
						amount=amount.add(info.getSaleInfoTotalPrice());
					}
			}
			
			}else if(promotionCondition.getMatchType().equals(Constants.PROMOTION_TYPE_MAT)){//物料
				if(CollectionUtils.isNotEmpty(promotionCondition.getPromotionConditionMatchContents())){
					for(SaleInfo info:saleInfos){
						for(PromotionConditionMatchContent content:promotionCondition.getPromotionConditionMatchContents()){
							if(StringUtils.isEmpty(info.getDiscType()) && info.getId().equals(content.getMatchContent())){//折扣类型为空，标记是商品.
								continue;
							}
						}
						amount=amount.add(info.getSaleInfoTotalPrice());
					}
				}
			}
		}
		return amount.doubleValue()>=promotionCondition.getCondition().doubleValue();
	}
	
	//整单金额
	private boolean ttlAmtValidate(){
		if(promotionCondition.getCondition().compareTo(saleDto.getNeedPay())<0){
			return true;
		}
		return false;
	}
	
	

}
