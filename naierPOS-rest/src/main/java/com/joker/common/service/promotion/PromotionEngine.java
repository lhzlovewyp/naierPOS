package com.joker.common.service.promotion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.joker.common.Constant.Constants;
import com.joker.common.dto.SaleDto;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.service.PromotionService;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.SpringBeanFactory;

/**
 * 促销规则引擎,获取当前销售单可参与的促销活动.
 * 
 * @author lvhaizhen
 *
 */
public class PromotionEngine {

	//门店可用促销活动.
	private List<Promotion> promotions; 
	//销售单.
	private SaleDto saleDto;
	//当前销售单可用促销.
	private List<Promotion> salePromotions; 
	//当前系统支持的促销规则.
	private List<PromotionRule> promotionRule;
	
	private String clientId;
	
	private String storeId;
	
	private PromotionService promotionService;
	
	public PromotionEngine(String clientId,String storeId,SaleDto saleDto){
		this.saleDto=saleDto;
		this.clientId=clientId;
		this.storeId=storeId;
		
		promotionService=(PromotionService)SpringBeanFactory.getBean("promotionService");
	}
	
	/**
	 * 获取当前可用的促销活动.
	 * @return
	 */
	public List<Promotion> getAvailablePromotion(){
		
		//得到当前门店可用促销.
		promotions = promotionService.getPromotionsByStore(clientId, storeId, saleDto.getSaleDate());
		
		if(CollectionUtils.isEmpty(promotions)){
			return null;
		}
		
		List<Promotion> result= new ArrayList<Promotion>();
		
		//对促销活动进行排序,根据促销条件来进行排序
		List<Promotion> matQtyPromotions = new ArrayList<Promotion>();
		List<Promotion> matAmtPromotions = new ArrayList<Promotion>();
		List<Promotion> ttlAmtPromotions = new ArrayList<Promotion>();
		
		for(Promotion promotion:promotions){
			//如果时间上不匹配,直接过滤.
			if(!timeAvailable(promotion)){
				continue;
			}
			//会员限制.
			if(!memberAvailable(promotion)){
				continue;
			}
			//促销条件限制.
			if(!conditionAvailable(promotion)){
				continue;
			}
			
			List<PromotionOffer> offers=promotion.getPromotionOffers();
			for(PromotionOffer offer:offers){
				List<PromotionCondition> conditions = offer.getPromotionCondition();
				PromotionCondition condition = conditions.get(0);
				if (condition.getConditionType().equals(Constants.PROMOTION_CONDITION_MATAMT)){//商品金额
					matAmtPromotions.add(promotion);
					break;
				}else if (condition.getConditionType().equals(Constants.PROMOTION_CONDITION_MATQTY)){//商品数量
					matQtyPromotions.add(promotion);
					break;
					
				}else if (condition.getConditionType().equals(Constants.PROMOTION_CONDITION_TTLAMT)){//整单金额
					ttlAmtPromotions.add(promotion);
					break;
				}
			}
		}
		result.addAll(matQtyPromotions);
		result.addAll(matAmtPromotions);
		result.addAll(ttlAmtPromotions);
		if(CollectionUtils.isEmpty(result)){
			return null;
		}
		return result;
	}
	
	public boolean  isAvailablePromotion(Promotion promotion){
		boolean result = true;
		//如果时间上不匹配,直接过滤.
		if(!timeAvailable(promotion)){
			result=false;
		}
		//会员限制.
		if(!memberAvailable(promotion)){
			result=false;
		}
		//促销条件限制.
		if(!conditionAvailable(promotion)){
			result=false;
		}
		return result;
	}
	
	/**
	 * 参与促销活动，并返回当前saleDto对象.
	 * 
	 * @return
	 */
	public SaleDto calPromotions(){
		
		List<Promotion> cacPromotions=saleDto.getPromotions();
		if(CollectionUtils.isEmpty(cacPromotions)){
			return saleDto;
		}
		
		for(Promotion cacPromotion:cacPromotions){
			if(isAvailablePromotion(cacPromotion)){
				saleDto = PromotionParserFactory.parsePromotion(saleDto, cacPromotion);
			}
		}
		return saleDto;
	}
	
	
	private boolean memberAvailable(Promotion promotion){
		boolean result =true;
		
		if(Constants.PROMOTION_MEMBER_ONLY.equals(promotion.getMemberRestrict()) && saleDto.getMember()==null){
			result = false;
		}
		
		if(Constants.PROMOTION_MEMBER_NON.equals(promotion.getMemberRestrict()) && saleDto.getMember()!=null){
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * 从促销时间上面判断当前促销是否有效.
	 * 
	 * @param promotion
	 * @return
	 */
	private boolean timeAvailable(Promotion promotion){
		boolean result =true;
		int week=DatetimeUtil.getWeek(saleDto.getSaleDate());
		int day=DatetimeUtil.getDay(saleDto.getSaleDate());
		
		//判断星期是否匹配
		if(promotion.getWeek().charAt(week-1)!='1'){
			result = false;
		}
		//判断日期是否匹配.
		if(promotion.getDays().charAt(day-1)!='1'){
			result = false;
		}
		//判断开始时间是否有效
		if(StringUtils.isNotBlank(promotion.getEffTime())){
			//有效时间小于当前时间.
			if(DatetimeUtil.compareTime(promotion.getEffTime(), DatetimeUtil.nowToString("hh:MM"))==-1){
				result = false;
			}
		}
		
		//判断结束时间是否有效
		if(StringUtils.isNotBlank(promotion.getExpTime())){
			if(DatetimeUtil.compareTime(DatetimeUtil.nowToString("hh:MM"),promotion.getExpTime())==-1){
				result = false;
			}
		}
		
		return result;
	}
	
	private boolean conditionAvailable(Promotion promotion){
		boolean result = true;
		
		if(promotion!=null && CollectionUtils.isNotEmpty(promotion.getPromotionOffers())){
			for(Iterator<PromotionOffer> it=promotion.getPromotionOffers().iterator();it.hasNext();){
				PromotionOffer offer = it.next();
				List<PromotionCondition> conditions = offer.getPromotionCondition();
				//促销条件判断，全部满足才生效.
				boolean flag=true;
				if(CollectionUtils.isNotEmpty(conditions)){
					for(PromotionCondition condition:conditions){
						PromotionConditionRuleParse parse = new PromotionConditionRuleParseImpl(condition, saleDto);
						boolean validate = parse.validate();
						if(!validate){
							flag=false;
							break;
						}
					}
				}
				//假设一个促销活动可以由多个促销优惠，如果满足条件，则同时参加,对于不满足条件的促销优惠进行删除.
				if(!flag){
					it.remove();
				}
			}
			//如果促销活动下面没有促销优惠，说明不满足此促销优惠活动.
			if(CollectionUtils.isEmpty(promotion.getPromotionOffers())){
				result = false;
			}
		}
		return result;
	}
	public List<Promotion> getPromotions() {
		return promotions;
	}
	public SaleDto getSaleDto() {
		return saleDto;
	}
	public List<Promotion> getSalePromotions() {
		return salePromotions;
	}
	public List<PromotionRule> getPromotionRule() {
		return promotionRule;
	}

	public String getClientId() {
		return clientId;
	}
	public String getStoreId() {
		return storeId;
	}

	public PromotionService getPromotionService() {
		return promotionService;
	}

}
