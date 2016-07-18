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
				if(StringUtils.isEmpty(info.getDiscType()) && contents.contains(info.getMaterial().getCategory().getId())){//折扣类型为空，标记是商品.
					amount=amount.add(info.getSaleInfoTotalPrice());
					details.add(info);
				}
			}
		}else if(type.equals(Constants.PROMOTION_TYPE_BRAND)){//品牌
			for(SaleInfo info:saleInfos){
				if(StringUtils.isEmpty(info.getDiscType()) && contents.contains(info.getMaterial().getBrand().getId())){//折扣类型为空，标记是商品.
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
	
	
	public static SaleInfo createPromotionSaleInfo(int size){
		SaleInfo saleInfo=new SaleInfo();
		saleInfo.setName("促销折扣");
		saleInfo.setCode("促销折扣");
		saleInfo.setDiscType("3");
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
	
}
