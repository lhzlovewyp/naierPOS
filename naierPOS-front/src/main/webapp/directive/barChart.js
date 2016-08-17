/**
 * 
 */
app.directive('barChart',function(AmtFormat){
	return {
		restrict:'EAC',
		link:function($scope,ielm,attrs,controller){
			var types = $scope.info.types;
			var amts = $scope.info.amts;
            $scope.$watch('info',function(newVal,oldVal){
            	types = newVal.types;
    			amts = newVal.amts;
    			
            	var myChart = echarts.init(ielm[0]);
    			var option = {
    				    color: ['#3398DB'],
    				    tooltip : {
    				        trigger: 'axis',
    				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    				        }
    				    },
    				    grid: {
    				        left: '3%',
    				        right: '4%',
    				        bottom: '3%',
    				        containLabel: true
    				    },
    				    
    				    xAxis : [
    				        
    				        {
    				            type : 'value'
    				        }
    				    ],
    				    yAxis : [
    			            {
    			              type : 'category',
    			              data : types,
    			              axisTick: {
    			              alignWithLabel: true
    			              }
    			            }
    				    ],
    				    series : [
    				        {
    				            name:'收款汇总',
    				            type:'bar',
    				            barWidth: '60%',
    				            label: {
    				                normal: {
    				                    show: true,
    				                    position: 'right',
    				                    formatter:function(params){
    				                    	return '￥'+AmtFormat.amtFormat(params.data,2)
    				                    }
    				                }
    				            },
    				            data:amts
    				        }
    				    ]
    				};
                myChart.setOption(option);
			})
			
		}
	}
})