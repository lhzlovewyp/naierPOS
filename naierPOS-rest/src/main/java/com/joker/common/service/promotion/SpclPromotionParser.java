/**
 * 
 */
package com.joker.common.service.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.joker.common.dto.SaleDto;
import com.joker.common.dto.SaleInfo;
import com.joker.common.model.Material;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.common.service.MaterialService;
import com.joker.core.util.RandomCodeFactory;
import com.joker.core.util.SpringBeanFactory;

/**
 * 赠送商品解析.
 * 
 * @author lvhaizhen
 *
 */
public class SpclPromotionParser implements PromotionParser{

	private SaleDto saleDto;

	private Promotion promotion;

	private List<PromotionOffer> promotionOffers;
	
	public SpclPromotionParser(SaleDto saleDto,Promotion promotion,List<PromotionOffer> promotionOffers){
		this.saleDto=saleDto;
		this.promotion=promotion;
		this.promotionOffers=promotionOffers;
	}
	
	@Override
	public SaleDto parsePromotion() {
		
		MaterialService materialService=(MaterialService)SpringBeanFactory.getBean("materialService");
		BigDecimal amount=new BigDecimal(0);
		
		List<SaleInfo> list=new ArrayList<SaleInfo>();
		
		//赠送商品优惠活动.
		for(PromotionOffer offer:promotionOffers){
			//特价.
			BigDecimal spclPrice=offer.getOfferContent();
			List<PromotionOfferMatchContent> promotionOfferMatchContents =offer.getPromotionOfferMatchContents();
			if(CollectionUtils.isNotEmpty(promotionOfferMatchContents)){
				for(PromotionOfferMatchContent content :promotionOfferMatchContents ){
					Material m = materialService.getMaterialById(content.getId());
					if(m!=null){
						SaleInfo saleInfo=PromotionUtil.transToSaleInfo(m,saleDto.getSaleInfos().size());
						saleDto.getSaleInfos().add(saleInfo);
						amount=amount.add(saleInfo.getRetailPrice());
						list.add(saleInfo);
					}
				}
			}
			//计算促销优惠价格.
			if(amount.intValue()>0){
				SaleInfo saleInfo=PromotionUtil.createPromotionSaleInfo(promotion,saleDto.getSaleInfos().size());
				saleInfo.setTotalPrice(spclPrice.subtract(amount));
				
				saleInfo.setId(RandomCodeFactory.defaultGenerateMixed());
				//给销售单中商品设置促销折扣金额.
				PromotionUtil.setSalesPromoPrice(list, saleDto.getSaleInfos(), saleInfo.getTotalPrice(),saleInfo);
				
				saleDto.getSaleInfos().add(saleInfo);
			}
		}
		
		
		return saleDto;
	}

}
