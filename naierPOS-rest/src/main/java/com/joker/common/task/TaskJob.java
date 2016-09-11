package com.joker.common.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.joker.common.Constant.Constants;
import com.joker.common.model.SalesOrder;
import com.joker.common.model.SalesOrderDetails;
import com.joker.common.service.SalesOrderService;
import com.joker.common.third.dto.ThirdSalesOrderDetail;
import com.joker.common.third.dto.ThirdSalesOrderDto;
import com.joker.common.third.member.DataBackPassService;
import com.joker.core.util.DatetimeUtil;

@Component("taskjob")
public class TaskJob {
	
	@Autowired
	SalesOrderService salesOrderService;

	@Scheduled(cron = "0 0/5 * * * ?")  
    public void job1() {  
        System.out.println("任务进行中。。。");  
        List<SalesOrder>  list = salesOrderService.getLimitSalesOrderByFinished(null, null);
        if(CollectionUtils.isEmpty(list)){
        	return;
        }
        for(SalesOrder salesOrder : list){
        	SalesOrder detailSalesOrder=salesOrderService.getSalesOrderById(salesOrder.getClient().getId(), salesOrder.getStore().getId(), salesOrder.getId());
        	
        	ThirdSalesOrderDto orderDto  = new ThirdSalesOrderDto();
        	orderDto.setCustomer_code(salesOrder.getMember().getMemberCode());
        	orderDto.setMoney(salesOrder.getPaid().toString());
        	orderDto.setNum(salesOrder.getQuantity().intValue()+"");
        	orderDto.setRecord_code(salesOrder.getId());
        	orderDto.setVip_code(salesOrder.getMember().getMemberCode());
        	orderDto.setIs_add_time(DatetimeUtil.formatDateToString(salesOrder.getFinished(), DatetimeUtil.DATEANDTIME));
    		List<ThirdSalesOrderDetail> details = new ArrayList<ThirdSalesOrderDetail>();

        	List<SalesOrderDetails> sodetails=detailSalesOrder.getSalesOrderDetails();
        	if(CollectionUtils.isNotEmpty(sodetails)){
        		for(SalesOrderDetails sod:sodetails){
        			if(sod.getItemClass().getCode().equals(Constants.SALE_TYPE_ITEMDISC)){//只回传商品信息.
        				ThirdSalesOrderDetail detail = new ThirdSalesOrderDetail();
            			detail.setMoney(sod.getPayable().toString());
            			detail.setNum(sod.getQuantity().intValue()+"");
            			detail.setRecord_code(salesOrder.getId());
            			detail.setDetail_record_code(sod.getId());
            			detail.setGoods_code(sod.getMaterial().getCode());
            			details.add(detail);
        			}
        		}
        	}
        	boolean result=DataBackPassService.process(orderDto, details);
        	if(result){
        		salesOrder.setToBaison(1);
        		salesOrderService.updateSalesOrder(salesOrder);
        	}
        }
    }  
}
