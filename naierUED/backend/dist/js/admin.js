var i = 2;
function addMaterialProperty(){
	$('#MaterialProperty table').append('<tr>'
	+'<td width="60"><a class="btn btn-danger btn-flat btn-xs" href="#" onClick="$(this).parent().parent().remove();"> 删除</a></td>'
	+'<td width="50">颜色</td>'
	+'<td><select id="Color2" name="Color[]" class="form-control">'
		  +'<option value="">颜色1</option>'
		  +'<option value="">颜色2</option>'
	  +'</select></td>'
	+'<td width="50">尺码</td>'
	+'<td><select id="Size1" name="Size[]" class="form-control">'
		  +'<option value="">尺码1</option>'
		  +'<option value="">尺码2</option>'
	  +'</select></td>'
	 +'<td width="50">条码</td>'
	 +'<td><input type="text" class="form-control" id="Barcode1" name="Barcode[]" placeholder="条码"></td>'
	 +'<td width="50">状态</td>'
	 +'<td><select id="Status1" name="Status[]" class="form-control">'
	  +'<option value="">有效</option>'
	  +'<option value="">无效</option>'
  +'</select></td>'
	+'</tr>');
}


$(function(){
	$('input[type="checkbox"]').iCheck({
	  checkboxClass: 'icheckbox_minimal-blue',
	  radioClass: 'iradio_minimal-blue'
	});
	
	//Enable check and uncheck all functionality
	$(".checkbox-toggle").click(function () {
	  var clicks = $(this).data('clicks');
	  if (clicks) {
		//Uncheck all checkboxes
		$(".mailbox-messages input[type='checkbox']").iCheck("uncheck");
		$(".fa", this).removeClass("fa-check-square-o").addClass('fa-square-o');
	  } else {
		//Check all checkboxes
		$(".mailbox-messages input[type='checkbox']").iCheck("check");
		$(".fa", this).removeClass("fa-square-o").addClass('fa-check-square-o');
	  }
	  $(this).data("clicks", !clicks);
	});
	
	$('.select2').select2();
	$('#myTab a').click(function (e) {
      e.preventDefault();
      $(this).tab('show');
    })
})