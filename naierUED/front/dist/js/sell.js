jQuery.fn.extend({
	initTable : function(opt){
		var table = $(this);
		table.find("tr").each(function(i, tr){
			$(tr).find("td").eq(0).find('button').click(function(){
				$(tr).remove();
			});
			if(opt){
				var attribute_data = (i > 0) && ($(tr).find("td").eq(3).html().toString()).split('/');
				$(tr).find("td").eq(3).click(function(){
					var td = $(this);
					var options = $.extend(opt, {
						color:attribute_data[0],
						size:attribute_data[1]
					});
					td.setModal(options);
				});
				$(tr).find("td").eq(6).click(function(){
					var td = $(this);
					var options = $.extend(opt, {
						modalTitle:$(tr).find("td").eq(2).html()+' '+attribute_data[0]+' '+attribute_data[1],
						productName:$(tr).find("td").eq(2).html(),
						price:$(tr).find("td").eq(4).html(),
						total:td.html(),
						type:'discount',
						submitFuction:modalSubmit2
					});
					td.setModal(options);
				});
			}
		})
	},
})

jQuery.fn.extend({
	attributeUl : function(child){
		var ul = $(this);
		ul.find("li").each(function(i, li){
			if((!child && i > 0) || child){
				$(li).click(function(){
					$(li).parent().find(".fa-check").remove();
					if(child){
						$(li).find('a').eq(0).prepend('<i class="fa fa-check"></i>');
					}else{
						$(li).prepend('<i class="fa fa-check"></i>');
					}
				});
			}
		})
	},
})

jQuery.fn.extend({
	setModal : function(opt){
		var default_settings = {
				width:600,
				btnSubmitText:'提交',
				btnCancelText:'取消',
				isAll:''
			};
	    var options = $.extend(default_settings, opt);
		var obj = $(this);
		$(options.selector).find('.btn-default').text(options.btnCancelText).bind('click', function(){
			$(options.selector).modal('hide');
			$('.payment-detail').remove();
		})
		if(options.type == 'discount'){
			$(options.selector).find('.modal-body').html('<div class="row">'
			  +'  <div class="col-xs-10">'
			  +'      <ul class="discount-ul">'
			  +'		  <li>'
			  +'			<input type="radio" name="discount" value="1" checked="checked" />折扣%<br/>'
			  +'			<input type="radio" name="discount" value="2" />折让 </li>'
			  +'		  <li>'
			  +'			<input type="text" class="form-control"/>'
			  +'		  </li>'
			  +'      </ul>' 
			  +'  </div>'
			  +'</div>');
		}else if(options.type == 'coupon'){
			$(options.selector).find('.modal-body').html('<div class="row">'
			  +'  <div>'
			  +'      <ul class="coupon-ul">'
			  +'	  <li>'
			  +'		<input type="checkbox" name="coupon" value="1" />'
			  +'		<div>33121-1 全棉提花女船袜买一送一</div>'
			  +'	  </li>'
			  +'	  <li>'
			  +'		<input type="checkbox" name="coupon" value="2" />'
			  +'		<div>消费满100减10元</div>'
			  +'	  </li>'
			  +'      </ul>' 
			  +'  </div>'
			  +'</div>');
			$(options.selector).find('.btn-default').unbind('click');
			$(options.selector).find('.btn-default').bind('click', function(evt){
				options.submitFuction(obj, options);
			});
		}else if(options.type == 'payment'){
			$(options.selector).find('.modal-body').html('<div class="row">'
			  +'  <div>'
			  +'      <ul class="payment-ul">'
			  +'<li><a class="btn btn-default btn-flat" href="javascript:;" onClick="showPaymentDetail(1)">现金</a> <input type="text" class="form-control" value="¥7.00" /> </li>'
			  +'<li><a class="btn btn-default btn-flat" href="javascript:;" onClick="showPaymentDetail(2)">储值卡</a> <input type="text" class="form-control" value="¥7.00" /> </li>'
			  +'<li><a class="btn btn-default btn-flat" href="javascript:;" onClick="showPaymentDetail(3)">银联卡</a> <label class="form-control">¥7.00</label></li>'
			  +'<li><a class="btn btn-default btn-flat" href="javascript:;" onClick="showPaymentDetail(4)">电子券</a> <input type="text" class="form-control" value="¥7.00" /> </li>'
			  +'<li><a class="btn btn-default btn-flat" href="javascript:;" onClick="showPaymentDetail(5)">支付宝</a> <input type="text" class="form-control" value="¥7.00" /> </li>'
			  +'<li><a class="btn btn-default btn-flat" href="javascript:;" onClick="showPaymentDetail(6)">会员积分</a> <input type="text" class="form-control" value="¥7.00" /> </li>'
			  +'<li><a class="btn btn-default btn-flat" href="javascript:;" onClick="showPaymentDetail(7)">微信支付</a> <input type="text" class="form-control" value="¥7.00" /> </li>'
			  +'      </ul>' 
			  +'  </div>'
			  +'</div>');
		}else if(options.type == 'customer'){
			$(options.selector).find('.modal-body').html('<div class="box no-border no-shadow">'
			  +'<div class="box-header">'
			  +' 	<div style="width:250px;" class="input-group input-group-sm">'
			  +'	  <input type="text" placeholder="搜索" class="form-control pull-right" name="table_search">'
			  +'	  <div class="input-group-btn">'
			  +'		<button class="btn btn-primary" type="submit"><i class="fa fa-search"></i></button>'
			  +'	  </div>'
			  +'</div>'
			  +'</div>'
			  +'<div class="box-body">'
				+'<table id="table1" class="table table-bordered table-hover cursor">'
				  +'<tr>'
					+'<th>会员编号</th>'
					+'<th>姓名</th>'
					+'<th>性别</th>'
					+'<th>手机号</th>'
					+'<th>生日</th>'
					+'<th>等级</th>'
					+'<th>积分</th>'
				  +'</tr>'
				  +'<tr>'
					+'<td>30409583</td>'
					+'<td>懒得想</td>'
					+'<td>男</td>'
					+'<td>13918888888</td>'
					+'<td>1985-05-19</td>'
					+'<td>金牌会员</td>'
					+'<td>500</td>'
				  +'</tr>'
				+'</table>'
			  +'</div>'
			+'</div>');
		}else{
			$(options.selector).find('.modal-body').html('<p class="product-name">'+options.productName+'</p>'
			  +'<div class="row">'
			  +'  <div class="col-xs-5">'
			  +'      <ul class="attribute-ul">'
			  +'          <li class="header">颜色</li>'
			  +'          <li>'+((options.color == '宝蓝') ? '<i class="fa fa-check"></i>' : '')+'宝蓝</li>'
			  +'          <li>'+((options.color == '黑') ? '<i class="fa fa-check"></i>' : '')+'黑</li>'
			  +'          <li>'+((options.color == '咖啡') ? '<i class="fa fa-check"></i>' : '')+'咖啡</li>'
			  +'      </ul>' 
			  +'  </div>'
			  +'  <div class="col-xs-5">'
			  +'     <ul class="attribute-ul">'
			  +'          <li class="header">尺码</li>'
			  +'          <li>'+((options.size == '22-24') ? '<i class="fa fa-check"></i>' : '')+'22-24</li>'
			  +'          <li>'+((options.size == '35-37') ? '<i class="fa fa-check"></i>' : '')+'35-37</li>'
			  +'      </ul>'
			  +'  </div>'
			  +'</div>');
		}
		$(options.selector).find('.modal-dialog').width(options.width);
		$(options.selector).find('.modal-title').html(options.modalTitle);
		$(options.selector).find('.btn-primary').text(options.btnSubmitText).unbind('click').bind('click', function(evt){
			options.submitFuction(obj, options);
		});
		$(options.selector).modal('show');
		$('.attribute-ul').attributeUl();
		$('.payment-ul').attributeUl(true);
	},
})
