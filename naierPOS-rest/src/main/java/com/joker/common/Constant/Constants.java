package com.joker.common.Constant;

public class Constants {

	// 订单类型，销售订单
	public final static String ORDER_TYPE_SO = "SO";
	// 订单类型，售退订单
	public final static String ORDER_TYPE_RTN = "RTN";

	// 状态，成功标记
	public final static String STATUS_SUCCESS = "1";
	// 状态，失败标记
	public final static String STATUS_FAIL = "0";

	// 是否存在属性-yes
	public final static String PROPERTY_YES = "1";
	//是否存在属性-No
	public final static String PROPERTY_NO = "0";
	
	
	//促销类型
	/**折扣*/
	public final static String PROMOTION_DISC="DISC";
	/**折让*/
	public final static String PROMOTION_RED="RED";
	/**特价*/
	public final static String PROMOTION_SPCL="SPCL";
	/**赠送商品.*/
	public final static String PROMOTION_FREE="FREE";
	/**加价购买.*/
	public final static String PROMOTION_EXT="EXT";
	
	//促销类型
	//品类
	public final static String PROMOTION_TYPE_MATCAT="MATCAT";
	//品牌
	public final static String PROMOTION_TYPE_BRAND="BRAND";
	//物料
	public final static String PROMOTION_TYPE_MAT="MAT";
	//促销条件
	/**商品数量*/
	public final static String PROMOTION_CONDITION_MATQTY="MATQTY";
	/**商品金额*/
	public final static String PROMOTION_CONDITION_MATAMT="MATAMT";
	/**整单金额*/
	public final static String PROMOTION_CONDITION_TTLAMT="TTLAMT";
	//促销匹配条件-任意
	public final static String PROMOTION_CONDITION_MATCH_ANY="ANY";
	//促销匹配条件-相同
	public final static String PROMOTION_CONDITION_MATCH_SAME="SAME";
	//促销匹配条件-不同.
	public final static String PROMOTION_CONDITION_MATCH_DIFF="DIFF";
	
	//促销会员设置
	//所有都可以参加
	public final static String PROMOTION_MEMBER_ALL="ALL";
	//只有会员可以参加
	public final static String PROMOTION_MEMBER_ONLY="ONLY";
	public final static String PROMOTION_MEMBER_NON="NON";
	
	//支付方式
	//现金
	public final static String PAYMENT_CASH="CASH";
	//银联卡(脱机)
	public final static String PAYMENT_UNIONPAY_OFF="UNIONPAY_OFF";
	//支付宝
	public final static String PAYMENT_ALIPAY="ALIPAY";
	//微信支付
	public final static String PAYMENT_WXPAY="WXPAY";
	//百胜储值卡
	public final static String PAYMENT_BS_PREPAID="BS_PREPAID";
	//百胜电子券
	public final static String PAYMENT_BS_COUPON="BS_COUPON";
	//百胜会员积分
	public final static String PAYMENT_BS_POINT="BS_POINT";
	
	//销售单明细类型.
	//商品
	public final static String SALE_TYPE_MAT="MAT";
	//商品
	public final static String SALE_TYPE_ITEMDISC="ITEMDISC";
	//商品
	public final static String SALE_TYPE_TRANSDISC="TRANSDISC";
	//商品
	public final static String SALE_TYPE_PROMDISC="PROMDISC";
	public final static String COMMA = ",";
}
