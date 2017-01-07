/**
 * 
 */
package com.joker.common.service.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.joker.common.dto.SaleDto;
import com.joker.common.dto.SaleInfo;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.core.util.RandomCodeFactory;

/**
 * 折扣解析.折扣解析主要分为2类，整单折扣和商品折扣.
 * 
 * @author lvhaizhen
 *
 */
public class DiscPromotionParser implements PromotionParser {

	private List<PromotionOffer> promotionOffers;
	
	private SaleDto saleDto;
	
	private Promotion promotion;
	
	public DiscPromotionParser(SaleDto saleDto,Promotion promotion,List<PromotionOffer> promotionOffers){
		this.saleDto=saleDto;
		this.promotion=promotion;
		this.promotionOffers=promotionOffers;
	}
	
	@Override
	public SaleDto parsePromotion() {
		//对于相同类型的促销优惠，只参加一个优惠幅度最大的.
		Collections.sort(promotionOffers,new Comparator<PromotionOffer>(){
			@Override
			public int compare(PromotionOffer o1, PromotionOffer o2) {
				//假如A的值大于B，你返回1。这样调用Collections.sort()方法就是升序
				//假如A的值大于B，你返回-1。这样调用Collections.sort()方法就是降序
				return o1.getOfferContent().compareTo(o2.getOfferContent());
			}
		});
		PromotionOffer offer=promotionOffers.get(0);
		List<String> contents=new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(offer.getPromotionOfferMatchContents())){
			for(PromotionOfferMatchContent content : offer.getPromotionOfferMatchContents()){
				contents.add(content.getMatchContent());
			}
		}
		
		Map<String,Object> map=PromotionUtil.cacAllPrice(saleDto, saleDto.getSaleInfos(), contents, offer.getMatchType());
		BigDecimal amount=(BigDecimal)map.get("amount");
		List<SaleInfo> details=(List<SaleInfo>) map.get("details");
		BigDecimal disc=amount.multiply(offer.getOfferContent().subtract(new BigDecimal(100))).divide(new BigDecimal(100));
		SaleInfo saleInfo=PromotionUtil.createPromotionSaleInfo(promotion,saleDto.getSaleInfos().size());
		saleInfo.setTotalPrice(disc);
		saleInfo.setId(RandomCodeFactory.defaultGenerateMixed());
		//计算details中的促销折扣.
		for(SaleInfo detail : details){
			List<SaleInfo> saleInfos = saleDto.getSaleInfos();
			for(SaleInfo mat:saleInfos){
				if(mat.getSort().equals(detail.getSort())){
					BigDecimal price=mat.getSaleInfoTotalPrice();
					//计算当前促销下面的促销折扣信息.
					BigDecimal cac = disc.multiply((price.divide(amount,2,4)));
					mat.setPromotionDiscount(mat.getPromotionDiscount().add(cac));
					mat.addPromotionDiscount(cac,saleInfo.getId());
				}
			}
		}
		
		
		
		saleInfo.setPromotionDetails(details);
		
		
		
		//如果促销折扣是针对商品的信息,把数据插入到商品信息的后面.
		if(CollectionUtils.isNotEmpty(details)){
			boolean flag=false;
			for(int i=saleDto.getSaleInfos().size()-1;i>=0;i--){
				String id=saleDto.getSaleInfos().get(i).getId();
				if(StringUtils.isNotBlank(id) && id.equals(details.get(details.size()-1).getId()) && contents.contains(id)){
					saleDto.getSaleInfos().add(i+1, saleInfo);
					flag=true;
					break;
				}
			}
			if(!flag){
				saleDto.getSaleInfos().add(saleDto.getSaleInfos().size(), saleInfo);
			}
			
		}else{
			saleDto.getSaleInfos().add(saleInfo);
		}
		saleDto.addPromotionAmount(saleInfo.getTotalPrice());
		return saleDto;
	}

	public List<PromotionOffer> getPromotionOffers() {
		return promotionOffers;
	}

	public void setPromotionOffers(List<PromotionOffer> promotionOffers) {
		this.promotionOffers = promotionOffers;
	}

	public SaleDto getSaleDto() {
		return saleDto;
	}

	public void setSaleDto(SaleDto saleDto) {
		this.saleDto = saleDto;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

}
