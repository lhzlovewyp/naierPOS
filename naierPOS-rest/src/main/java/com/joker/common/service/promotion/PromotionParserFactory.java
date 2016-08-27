/**
 * 
 */
package com.joker.common.service.promotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.joker.common.Constant.Constants;
import com.joker.common.dto.SaleDto;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionOffer;

/**
 * @author lvhaizhen
 *
 */
public class PromotionParserFactory {

	public static SaleDto parsePromotion(SaleDto saleDto,Promotion promotion){
		
		//获取促销关键字段信息.
		//当前促销活动是否可以同时享受 0-不行 1-可以.
		String offerRelation=promotion.getOfferRelation();
		//促销优惠是否可以叠加.0-否 1-是.
		int repeatEffect=promotion.getRepeatEffect();
		//获取所有的促销活动.
		List<PromotionOffer> offers=promotion.getPromotionOffers();
		
		//首先对促销优惠按照类别进行分组.
		Map<String,List<PromotionOffer>> map=new HashMap<String,List<PromotionOffer>>();
		for(PromotionOffer offer:offers){
			String offerType=offer.getOfferType();
			if(map.containsKey(offerType)){
				map.get(offerType).add(offer);
			}else{
				List<PromotionOffer> temps=new ArrayList<PromotionOffer>();
				temps.add(offer);
				map.put(offerType, temps);
			}
		}
		
		//对map进行遍历;
		Set<Entry<String,List<PromotionOffer>>> set=map.entrySet();
		for(Iterator<Entry<String,List<PromotionOffer>>> it=set.iterator();it.hasNext();){
			Entry<String,List<PromotionOffer>> entry=it.next();
			String key=entry.getKey();
			List<PromotionOffer> values=entry.getValue();
			PromotionParser parser=null;
			if(Constants.PROMOTION_DISC.equals(key)){//折扣
				parser = new DiscPromotionParser(saleDto,promotion,values);
			}else if(Constants.PROMOTION_EXT.equals(key)){//加价购
				parser=new ExtPromotionParser(saleDto,promotion,values);
			}else if(Constants.PROMOTION_FREE.equals(key)){//赠送商品.
				parser=new FreePromotionParser(saleDto,promotion,values);
			}else if(Constants.PROMOTION_SPCL.equals(key)){//特卖价.
				parser=new SpclPromotionParser(saleDto,promotion,values);
			}else if(Constants.PROMOTION_RED.equals(key)){//折让.
				parser=new RedPromotionParser(saleDto,promotion,values);
			}
			if(parser!=null){
				saleDto=parser.parsePromotion();
			}
			
		}
		return saleDto;
	}
}
