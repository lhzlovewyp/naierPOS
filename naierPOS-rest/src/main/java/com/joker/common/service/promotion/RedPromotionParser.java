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
 * 折让
 * 
 * @author lvhaizhen
 *
 */
public class RedPromotionParser implements PromotionParser {

private List<PromotionOffer> promotionOffers;
	
	private SaleDto saleDto;
	
	private Promotion promotion;
	
	public RedPromotionParser(SaleDto saleDto,Promotion promotion,List<PromotionOffer> promotionOffers){
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
		List<SaleInfo> details=new ArrayList<SaleInfo>();
		PromotionOffer offer=promotionOffers.get(promotionOffers.size()-1);
		List<String> contents=new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(offer.getPromotionOfferMatchContents())){
			for(PromotionOfferMatchContent content : offer.getPromotionOfferMatchContents()){
				contents.add(content.getMatchContent());
			}
		}
		
		Map<String,Object> map=PromotionUtil.cacAllPrice(saleDto, saleDto.getSaleInfos(), contents, offer.getMatchType());

		details=(List<SaleInfo>) map.get("details");
		
		SaleInfo saleInfo=PromotionUtil.createPromotionSaleInfo(promotion,saleDto.getSaleInfos().size());
		saleInfo.setTotalPrice(offer.getOfferContent().negate());
		saleInfo.setId(RandomCodeFactory.defaultGenerateMixed());
		
		if(CollectionUtils.isNotEmpty(details)){
			
//			for(int i=saleDto.getSaleInfos().size()-1;i>=0;i--){
//				String id=saleDto.getSaleInfos().get(i).getId();
//				if(StringUtils.isNotBlank(id) && contents.contains(id)){
//					details.add(saleDto.getSaleInfos().get(i));
//				}
//			}
			
			//给销售单中商品设置促销折扣金额.
			PromotionUtil.setSalesPromoPrice(details, saleDto.getSaleInfos(), saleInfo.getTotalPrice(),saleInfo);
			saleInfo.setPromotionDetails(details);
			//如果促销折扣是针对商品的信息,把数据插入到商品信息的后面.
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
			for(SaleInfo info:saleDto.getSaleInfos()){
				if(StringUtils.isEmpty(info.getDiscType())){//折扣类型为空，标记是商品.
					details.add(info);
				}
			}
			//给销售单中商品设置促销折扣金额.
			PromotionUtil.setSalesPromoPrice(details, saleDto.getSaleInfos(), saleInfo.getTotalPrice(),saleInfo);
			saleInfo.setPromotionDetails(details);
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
