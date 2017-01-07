/**
 * 
 */
package com.joker.common.service.promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.joker.common.Constant.Constants;
import com.joker.common.dto.SaleDto;
import com.joker.common.dto.SaleInfo;
import com.joker.common.model.Material;
import com.joker.common.model.promotion.Promotion;

/**
 * @author lvhaizhen
 *
 */
public class PromotionUtil {

	public static Map<String,Object> cacAllPrice(SaleDto saleDto,List<SaleInfo> saleInfos,List<String> contents,String type){
		BigDecimal amount=new BigDecimal(0);
		List<SaleInfo> details=new ArrayList<SaleInfo>();
		if(StringUtils.isEmpty(type)){
			for(SaleInfo info:saleInfos){
				if(StringUtils.isEmpty(info.getDiscType())){//折扣类型为空，标记是商品.
					details.add(info);
				}
			}
			amount = saleDto.getTotalPrice();
		}else if(type.equals(Constants.PROMOTION_TYPE_MATCAT)){//类目;
			for(SaleInfo info:saleInfos){
				if(StringUtils.isEmpty(info.getDiscType()) && contents.contains(info.getCategory().getId())){//折扣类型为空，标记是商品.
					amount=amount.add(info.getSaleInfoTotalPrice());
					details.add(info);
				}
			}
		}else if(type.equals(Constants.PROMOTION_TYPE_BRAND)){//品牌
			for(SaleInfo info:saleInfos){
				if(StringUtils.isEmpty(info.getDiscType()) && contents.contains(info.getBrand().getId())){//折扣类型为空，标记是商品.
					amount=amount.add(info.getSaleInfoTotalPrice());
					details.add(info);
				}
			}
		}else if(type.equals(Constants.PROMOTION_TYPE_MAT)){//物料
			for(SaleInfo info:saleInfos){
				if(StringUtils.isEmpty(info.getDiscType()) && contents.contains(info.getId())){//折扣类型为空，标记是商品.
					amount=amount.add(info.getSaleInfoTotalPrice());
					details.add(info);
				}
			}
		}
		Map<String,Object> map=new HashMap();
		map.put("amount", amount);
		map.put("details", details);
		return map;
	}
	
	
	public static SaleInfo createPromotionSaleInfo(Promotion promotion,int size){
		SaleInfo saleInfo=new SaleInfo();
		saleInfo.setName("促销折扣");
		saleInfo.setCode("促销折扣");
		saleInfo.setDiscType("3");
		saleInfo.setAbbr(promotion.getName());
		saleInfo.setSort((1000+size)+"");
		saleInfo.addItemClassCode(Constants.SALE_TYPE_PROMDISC);
		return saleInfo;
	}
	
	public static SaleInfo transToSaleInfo(Material m,int size){
		SaleInfo saleInfo = new SaleInfo();
		saleInfo.setClient(m.getClient());
		saleInfo.setId(m.getId());
		saleInfo.setCode(m.getCode());
		saleInfo.setName(m.getName());
		saleInfo.setAbbr(m.getAbbr());
		saleInfo.setCategory(m.getCategory());
		saleInfo.setBrand(m.getBrand());
		saleInfo.setBasicUnit(m.getBasicUnit());
		saleInfo.setSalesUnit(m.getSalesUnit());
		saleInfo.setProperty(m.getProperty());
		saleInfo.setRetailPrice(m.getRetailPrice());
		saleInfo.setCount(1);
		saleInfo.setTotalPrice(m.getRetailPrice());
		saleInfo.setDiscType("4");
		saleInfo.addItemClassCode(Constants.SALE_TYPE_MAT);
		saleInfo.setSort((1000+size)+"");
		return saleInfo;
	}
	
	
	
	/**
	 * 根据促销匹配上的商品信息,获取销售单中对应的商品总价.
	 * 
	 * @param promDetails
	 * @param saleInfos
	 */
	public static List<SaleInfo>  setSalesPromoPrice(List<SaleInfo> promDetails,List<SaleInfo> saleInfos,BigDecimal amount,SaleInfo promoSaleInfo){
		BigDecimal result = new BigDecimal(0);
		//设置商品的总价.
		for(SaleInfo detail : promDetails){
			for(SaleInfo saleInfo : saleInfos){
				if(saleInfo.getSort().equals(detail.getSort())){
					result=result.add(saleInfo.getSaleInfoTotalPrice());
				}
			}
		}
		//计算商品的折扣.
		for(SaleInfo detail : promDetails){
			for(SaleInfo saleInfo : saleInfos){
				if(saleInfo.getSort().equals(detail.getSort())){
					BigDecimal price=saleInfo.getSaleInfoTotalPrice();
					//计算当前促销下面的促销折扣信息.
					BigDecimal cac = amount.multiply(price).divide(result,2,4);
					saleInfo.setPromotionDiscount(saleInfo.getPromotionDiscount().add(cac));
					saleInfo.addPromotionDiscount(cac,promoSaleInfo.getId());
				}
			}
		}
		return saleInfos;
	}
	
	
}
