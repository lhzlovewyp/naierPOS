/**
 * 
 */
package com.joker.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.joker.common.Constant.Constants;
import com.joker.common.dto.SaleDto;
import com.joker.common.dto.SaleInfo;
import com.joker.common.mapper.SalesOrderMapper;
import com.joker.common.model.Account;
import com.joker.common.model.ClientPayment;
import com.joker.common.model.ItemClass;
import com.joker.common.model.Material;
import com.joker.common.model.SalesOrder;
import com.joker.common.model.SalesOrderDetails;
import com.joker.common.model.SalesOrderDiscount;
import com.joker.common.model.SalesOrderPay;
import com.joker.common.service.MaterialService;
import com.joker.common.service.SalesConfigService;
import com.joker.common.service.SalesOrderService;
import com.joker.core.util.NumberUtil;
import com.joker.core.util.RandomCodeFactory;

/**
 * @author lvhaizhen
 *
 */
@Service
public class SalesOrderServiceImpl implements SalesOrderService{

	@Autowired
    SalesOrderMapper mapper;
	@Autowired
	SalesConfigService salesConfigService;
	@Autowired
	MaterialService materialService;
	

	@Override
	public SalesOrder getSalesInfo(String clientId, String storeId, Date salesDate) {
		return mapper.getSalesInfo(clientId, storeId, salesDate);
	}


	@Override
	public boolean addSaleInfo(SaleDto saleDto,Account account) {
		//创建销售单
		SalesOrder salesOrder=createSaleOrder(saleDto, account);
		Map<String,List> map=createSalesOrderDetails(saleDto,account,salesOrder);
		//创建销售明细单
		List<SalesOrderDetails> details=map.get("salesOrderDetails");
		//创建折扣明细.
		List<SalesOrderDiscount> discounts=map.get("discounts");
		//创建支付单
		List<SalesOrderPay> pays =  createSalesOrderPay(saleDto,account,salesOrder);
		
		//保存销售单数据.
		mapper.saveSalesOrder(salesOrder);
		mapper.saveSalesOrderDetail(details);
		if(CollectionUtils.isNotEmpty(discounts)){
			mapper.saveSalesOrderDiscount(discounts);
		}
		mapper.saveSalesOrderPay(pays);
		
		return true;
	}
	
	//创建销售单.
	private SalesOrder createSaleOrder(SaleDto saleDto,Account account){
		SalesOrder salesOrder=new SalesOrder();
		salesOrder.setId(RandomCodeFactory.defaultGenerateMixed());
		salesOrder.setClient(account.getClient());
		salesOrder.setTransClass(Constants.ORDER_TYPE_SO);
		salesOrder.setStore(account.getStore());
		salesOrder.setSalesDate(saleDto.getSaleDate());
		//获取交易序号.
		salesOrder.setCode(salesConfigService.getSaleMaxCode(account));
		salesOrder.setMember(saleDto.getMember());
		salesOrder.setShoppingGuide(saleDto.getShoppingGuide());
		//销售总数
		salesOrder.setQuantity(new BigDecimal(saleDto.getTotalNum()));
		//销售金额
		salesOrder.setAmount(saleDto.getTotalPrice());
		//TODO:抹零金额不设置.
		//销售折扣.
		salesOrder.setDiscount(saleDto.getTotalDiscPrice());
		//应付金额
		salesOrder.setPayable(saleDto.getPay());
		//实付金额
		salesOrder.setPaid(saleDto.getPayed());
		//溢收金额
		salesOrder.setExcess(saleDto.getPayed().subtract(saleDto.getPay()));
		//勾选取消促销
		salesOrder.setCancelPromotion(saleDto.getCancelPromotion());
		//创建者
		salesOrder.setCreator(account.getId());
		//TODO:待修改,创建时间.
		salesOrder.setCreated(new Date());
		//订单完成时间.
		salesOrder.setFinished(new Date());
		return salesOrder;
	}
	
	private Map<String,List> createSalesOrderDetails(SaleDto saleDto,Account account,SalesOrder salesOrder){
		List<SalesOrderDetails> list=new ArrayList<SalesOrderDetails>();
		
		List<SalesOrderDiscount> discounts = new ArrayList<SalesOrderDiscount>();
		
		List<SaleInfo> saleInfos = saleDto.getSaleInfos();
		
		//整单折扣
		List<SaleInfo> allDiscs=new ArrayList<SaleInfo>();
		//促销折扣
		List<SaleInfo> promotionDiscs=new ArrayList<SaleInfo>();
		//单项折扣.
		Map<String,List<SaleInfo>> itemDiscMap=new HashMap();
		//提取出单项折扣、整单折扣和促销折扣
		for(SaleInfo saleInfo : saleInfos){
			if("1".equals(saleInfo.getDiscType())){//单项打折.
				 String relatedId=saleInfo.getDict().getSaleInfo().getSort();
				 saleInfo.setId(RandomCodeFactory.defaultGenerateMixed());
				 if(itemDiscMap.containsKey(relatedId)){
					 itemDiscMap.get(relatedId).add(saleInfo);
				 }else{
					 List<SaleInfo> temp=new ArrayList<SaleInfo>();
					 temp.add(saleInfo);
					 itemDiscMap.put(relatedId, temp);
				 }
			}else if("2".equals(saleInfo.getDiscType())){//整单打折.
				saleInfo.setId(RandomCodeFactory.defaultGenerateMixed());
				allDiscs.add(saleInfo);
			}else if("3".equals(saleInfo.getDiscType())){//促销折扣.
				saleInfo.setId(RandomCodeFactory.defaultGenerateMixed());
				promotionDiscs.add(saleInfo);
			}
		}
		
		
		int i=1;
		for(SaleInfo saleInfo : saleInfos){
			SalesOrderDetails detail = new SalesOrderDetails();
			//id
			detail.setId(RandomCodeFactory.defaultGenerateMixed());
			//商户
			detail.setClient(account.getClient());
			//销售单
			detail.setSalesOrder(salesOrder);
			//明细类型.
			detail.setTransClass(salesOrder.getTransClass());
			//门店
			detail.setStore(account.getStore());
			//营业日期
			detail.setSalesDate(salesOrder.getSalesDate());
			//交易序号
			detail.setCode(salesOrder.getCode());
			//序号
			detail.setSerialNo(i);
			ItemClass itemClass=new ItemClass();
			if(StringUtils.isEmpty(saleInfo.getDiscType()) || saleInfo.getDiscType().equals("4")){//商品或者赠品.
				itemClass.setCode(Constants.SALE_TYPE_MAT);
				detail.setItemClass(itemClass);
				
				Material material=materialService.getMaterialByCode(saleInfo.getClient().getId(),saleInfo.getCode());
				//商品
				detail.setMaterial(material);
				//销售单位数量.
				detail.setQuantity(new BigDecimal(saleInfo.getCount()));
				//销售单位.
				detail.setSalesUnit(material.getSalesUnit());
				//基本单位
				detail.setBasicUnit(material.getBasicUnit());
				//销售单位对基本单位的换算率.
				detail.setConversion(material.getSalesConversion());
				//基本单位数量.
				detail.setBasicQuantity(material.getSalesConversion().multiply(new BigDecimal(saleInfo.getCount())));
				//销售价格
				detail.setPrice(saleInfo.getRetailPrice());
				detail.setAmount(saleInfo.getSaleInfoTotalPrice());
				//销售折扣.
				//计算销售折扣,并生成折扣明细,主要是单项折扣和整单折扣.
				//单项折扣计算.
				BigDecimal discount=new BigDecimal(0);
				List<SaleInfo> itemDiscs=itemDiscMap.get(saleInfo.getSort());
				if(CollectionUtils.isNotEmpty(itemDiscs)){
					for(SaleInfo itemDisc:itemDiscs){
						discount=discount.add(itemDisc.getTotalPrice());
						SalesOrderDiscount discountVo=new SalesOrderDiscount();
						discountVo.setId(RandomCodeFactory.defaultGenerateMixed());
						discountVo.setSalesOrder(salesOrder);
						discountVo.setClient(salesOrder.getClient());
						discountVo.setSalesDate(salesOrder.getSalesDate());
						discountVo.setTransClass(salesOrder.getTransClass());
						discountVo.setStore(salesOrder.getStore());
						discountVo.setCode(salesOrder.getCode());
						discountVo.setDisc(itemDisc.getId());
						discountVo.setMat(detail.getId());
						discountVo.setAmount(itemDisc.getTotalPrice());
						discounts.add(discountVo);
					}
				}
				//整单折扣计算.
				if(CollectionUtils.isNotEmpty(allDiscs)){
					for(SaleInfo allDisc:allDiscs ){
						BigDecimal tempDiscount=saleInfo.getDiscount() == null ?new BigDecimal(0) : saleInfo.getDiscount();
						BigDecimal price = allDisc.getTotalPrice().multiply((saleInfo.getSaleInfoTotalPrice().subtract(tempDiscount).divide(saleDto.getTotalPrice(),2)));
						price=NumberUtil.round(price,2,2);
						discount=discount.add(price);
						SalesOrderDiscount discountVo=new SalesOrderDiscount();
						discountVo.setId(RandomCodeFactory.defaultGenerateMixed());
						discountVo.setClient(salesOrder.getClient());
						discountVo.setSalesDate(salesOrder.getSalesDate());
						discountVo.setSalesOrder(salesOrder);
						discountVo.setTransClass(salesOrder.getTransClass());
						discountVo.setStore(salesOrder.getStore());
						discountVo.setCode(salesOrder.getCode());
						discountVo.setDisc(allDisc.getId());
						discountVo.setMat(detail.getId());
						discountVo.setAmount(allDisc.getTotalPrice());
						discounts.add(discountVo);
					}
				}
				
				if(CollectionUtils.isNotEmpty(promotionDiscs)){
					for(SaleInfo disc:promotionDiscs){
						List<SaleInfo> details=disc.getPromotionDetails();
						if(CollectionUtils.isNotEmpty(details)){
							BigDecimal pdPrice=new BigDecimal(0);
							BigDecimal allPrice=new BigDecimal(0);
							for(SaleInfo pd : details){
								allPrice=allPrice.add(pd.getSaleInfoTotalPrice());
								if(pd.getSort().equals(saleInfo.getSort())){
									pdPrice=pd.getSaleInfoTotalPrice();
								}
							}
							if(pdPrice.doubleValue() == 0){
								continue;
							}
							BigDecimal proDisc=disc.getTotalPrice().multiply(pdPrice.divide(allPrice,2,4));
							SalesOrderDiscount discountVo=new SalesOrderDiscount();
							discountVo.setId(RandomCodeFactory.defaultGenerateMixed());
							discountVo.setClient(salesOrder.getClient());
							discountVo.setSalesDate(salesOrder.getSalesDate());
							discountVo.setSalesOrder(salesOrder);
							discountVo.setTransClass(salesOrder.getTransClass());
							discountVo.setStore(salesOrder.getStore());
							discountVo.setCode(salesOrder.getCode());
							discountVo.setDisc(disc.getId());
							discountVo.setMat(detail.getId());
							discountVo.setAmount(proDisc);
							discounts.add(discountVo);
						}
					}
				}
				
				detail.setDiscount(discount);
				//抹零金额.
				//销售净额.
				detail.setPayable(detail.getAmount().subtract(detail.getDiscount()));
				detail.setColor(saleInfo.getColor());
				detail.setSize(saleInfo.getSize());
			}else if("1".equals(saleInfo.getDiscType())){//单项打折.
				itemClass.setCode(Constants.SALE_TYPE_ITEMDISC);
				detail.setItemClass(itemClass);
				detail.setAmount(saleInfo.getTotalPrice());
			}else if("2".equals(saleInfo.getDiscType())){//整单打折.
				itemClass.setCode(Constants.SALE_TYPE_TRANSDISC);
				detail.setItemClass(itemClass);
				detail.setAmount(saleInfo.getTotalPrice());
			}else if("3".equals(saleInfo.getDiscType())){//促销折扣.
				itemClass.setCode(Constants.SALE_TYPE_PROMDISC);
				detail.setItemClass(itemClass);
				detail.setAmount(saleInfo.getTotalPrice());
				detail.setPromotion(saleInfo.getPromotion());
			}
			i++;
			list.add(detail);
		}
		
		Map<String,List> map=new HashMap<String,List>();
		map.put("salesOrderDetails", list);
		map.put("discounts",discounts);
		return map;
	}
	

	private List<SalesOrderPay> createSalesOrderPay(SaleDto saleDto,Account account,SalesOrder salesOrder){
		List<SalesOrderPay> list=new ArrayList<SalesOrderPay>();
		List<ClientPayment> payments=saleDto.getPayments();
		int i = 1;
		for(ClientPayment payment:payments){
			if(!StringUtils.isEmpty(payment.getSelected()) && payment.getSelected().equals("1")){
				SalesOrderPay pay=new SalesOrderPay();
				pay.setId(RandomCodeFactory.defaultGenerateMixed());
				pay.setClient(account.getClient());
				pay.setSalesOrder(salesOrder);
				pay.setTransClass(salesOrder.getTransClass());
				pay.setStore(salesOrder.getStore());
				pay.setSalesDate(salesOrder.getSalesDate());
				pay.setCode(salesOrder.getCode());
				pay.setSerialNo(i);
				pay.setPayTime(new Date());
				pay.setPayment(payment.getPayment());
				pay.setAmount(payment.getAmount());
				//找零
				//溢收
				pay.setTransNo(payment.getTransNo());
				list.add(pay);
				i++;
			}
		}
		return list;
	}
	

}
