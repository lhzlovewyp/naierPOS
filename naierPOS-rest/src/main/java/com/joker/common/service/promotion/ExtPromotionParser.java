/**
 * 
 */
package com.joker.common.service.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
 * 加价购买促销解析.
 * 
 * @author lvhaizhen
 * 
 */
public class ExtPromotionParser implements PromotionParser {

	private SaleDto saleDto;

	private Promotion promotion;

	private List<PromotionOffer> promotionOffers;
	
	public ExtPromotionParser(SaleDto saleDto,Promotion promotion,List<PromotionOffer> promotionOffers){
		this.saleDto=saleDto;
		this.promotion=promotion;
		this.promotionOffers=promotionOffers;
	}
	
	@Override
	public SaleDto parsePromotion() {

		MaterialService materialService=(MaterialService)SpringBeanFactory.getBean("materialService");
		
		// 对于相同类型的促销优惠，只参加一个优惠幅度最大的.
		Collections.sort(promotionOffers, new Comparator<PromotionOffer>() {
			@Override
			public int compare(PromotionOffer o1, PromotionOffer o2) {
				// 假如A的值大于B，你返回1。这样调用Collections.sort()方法就是升序
				// 假如A的值大于B，你返回-1。这样调用Collections.sort()方法就是降序
				return o1.getOfferContent().compareTo(o2.getOfferContent());
			}
		});
		
		PromotionOffer offer=promotionOffers.get(0);
		//加价金额.
		BigDecimal extAmount=offer.getOfferContent();
		
		BigDecimal amount=new BigDecimal(0);
		//获取加价购的商品信息.
		List<PromotionOfferMatchContent> contents=offer.getPromotionOfferMatchContents();
		List<SaleInfo> list=new ArrayList<SaleInfo>();
		for(PromotionOfferMatchContent content:contents){
			Material material=materialService.getMaterialById(content.getMatchContent());
			SaleInfo saleInfo=PromotionUtil.transToSaleInfo(material,saleDto.getSaleInfos().size());
			saleDto.getSaleInfos().add(saleInfo);
			amount=amount.add(saleInfo.getTotalPrice());
			list.add(saleInfo);
		}
		//计算促销优惠价格.
		SaleInfo saleInfo=PromotionUtil.createPromotionSaleInfo(promotion,saleDto.getSaleInfos().size());
		saleInfo.setTotalPrice(extAmount.subtract(amount));
		saleInfo.setId(RandomCodeFactory.defaultGenerateMixed());
		//给销售单中商品设置促销折扣金额.
		PromotionUtil.setSalesPromoPrice(list, saleDto.getSaleInfos(), saleInfo.getTotalPrice(),saleInfo);
		
		saleInfo.setPromotionDetails(list);
		saleDto.getSaleInfos().add(saleInfo);
		return saleDto;
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
