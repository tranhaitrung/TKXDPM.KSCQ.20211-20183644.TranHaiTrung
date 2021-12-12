package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.ExemptionMechanism;
import javax.swing.plaf.multi.MultiLookAndFeel;

import org.junit.platform.commons.util.StringUtils;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

public class PlaceRushOrderController extends BaseController{
	/**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    }
    
    /**
     * Kiểm tra trong giỏ hàng có sản phẩm hỗ trợ giao hàng nhanh hay không
     * @param order : giỏ hàng
     * @return : true/false
     */
    public Boolean validateProductSupport(Order order) {
    	int count = 0;
    	List mediaList = order.getlstOrderMedia();
    	
    	for(Object object : mediaList) {
    		OrderMedia cartMedia = (OrderMedia) object;
    		if (checkProductPlaceRushOrder(cartMedia.getMedia())) {
    			count++;
			}
    	}
    	
    	return count == 0 ? false : true;
    }
    
    
    /**
     * Kiểm tra sản phẩm có hỗ trợ giao hành nhanh hay không
     * @param media: sản phẩm mua bao gồm Book, CD, DVD
     * @return true: hỗ trợ / false: không hỗ trợ
     */
   public boolean checkProductPlaceRushOrder(Media media) {
	   //Checking
	   //return media.getIsSupportRushOrderBoolean();
	   return true;
   }
   
   /**
    * Kiểm tra địa chỉ giao hàng có hỗ trợ giao hàng nhanh hay không
    * @param province: tỉnh/thành phố giao hàng
    * @return
    */
   public boolean checkProvinceRushShipping(String province) {
	   if ("Hà Nội".equals(province)) return true;
	   return false;
   }
    
   public boolean checkProduct() {
	   return false;
   }

    /**
     * This method calculates the shipping fees of order
     * @param order: đơn đặt hàng
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
