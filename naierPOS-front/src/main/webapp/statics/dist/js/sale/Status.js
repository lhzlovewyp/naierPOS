/**
 * Created by crell on 2016/1/17.
 */
var Status = (function() {
    var constant = {
        SUCCESS : 1001,
        ERROR : 1002,
        FAILED : 1003,
        INVALID_TOKEN : 1004,
        INVALID_USER : 1005,
        REDIRECT : 1006,
        
        
        CASH : "CASH",
        UNIONPAY_OFF : "UNIONPAY_OFF",
        ALIPAY : "ALIPAY",
        WXPAY : "WXPAY",
        BS_PREPAID : "BS_PREPAID",
        BS_COUPON : "BS_COUPON",
        BS_POINT : "BS_POINT"
    };
    return constant;
})();