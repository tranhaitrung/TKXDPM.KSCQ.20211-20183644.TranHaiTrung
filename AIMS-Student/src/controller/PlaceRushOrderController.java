package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import controller.calculate.RushShippingFee;
import controller.calculate.ShippingFeeCalculator;
import controller.validate.ValidateRushOrder;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.shipping.Location;

public class PlaceRushOrderController extends PlaceOrderController{
	/**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

    /**
     * Kiểm tra trong giỏ hàng có sản phẩm hỗ trợ giao hàng nhanh hay không
     * @param order : giỏ hàng
     * @return : true/false
     */
    public Boolean validateProductSupport(Order order) {
    	return new ValidateRushOrder().validateProductSupport(order.getlstOrderMedia());
    }


    /**
     * Kiểm tra sản phẩm có hỗ trợ giao hành nhanh hay không
     * @param media: sản phẩm mua bao gồm Book, CD, DVD
     * @return true: hỗ trợ / false: không hỗ trợ
     */
   public boolean checkProductPlaceRushOrder(Media media) {
	   //Checking
	   //return media.getIsSupportRushOrderBoolean();
	   return new ValidateRushOrder().checkProductPlaceRushOrder(media);
   }

   /**
    * Kiểm tra địa chỉ giao hàng có hỗ trợ giao hàng nhanh hay không
    * @param province: tỉnh/thành phố giao hàng
    * @return
    */
   public boolean checkProvinceRushShipping(String province) {
	   return new ValidateRushOrder().checkProvinceRushShipping(province);
   }
    
   public boolean checkProduct() {
	   return new ValidateRushOrder().checkProduct();
   }

    /**
     * This method calculates the shipping fees of order
     * @param amount: đơn đặt hàng
     * @return shippingFee
     */
    public int calculateShippingFee(int amount){
        ShippingFeeCalculator shippingFeeCalculator = new RushShippingFee();
        return shippingFeeCalculator.calculateShippingFee(amount);
    }
}
