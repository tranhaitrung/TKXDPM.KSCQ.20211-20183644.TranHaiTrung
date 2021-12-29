package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.calculate.NormalShippingFee;
import controller.calculate.ShippingFeeCalculator;
import controller.validate.ValidateInterface;
import controller.validate.validateIpm.ValidateAddress;
import controller.validate.validateIpm.ValidateName;
import controller.validate.validateIpm.ValidatePhone;
import org.junit.platform.commons.util.StringUtils;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

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
        return new Order().createdOrder();
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
    public boolean validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	boolean validatePhone = validatePhoneNumber(info.get("phone").toString());
    	boolean validateAddress = validateAddress(info.get("address").toString());
    	boolean validateName = validateName(info.get("name").toString());
    	String errorString = "";
    	if (!validatePhone) {
			errorString += "Your phone is required! \n";
		}
    	if (!validateName) {
			errorString += "Your name is required! \n";
		}
    	if (!validateAddress) {
			errorString += "Your address is required! \n";
		}
    	
    	if (!StringUtils.isBlank(errorString)) {
    		PopupScreen.error(errorString);
			return false;
		}
    	return true;
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
        ValidateInterface validatePhone = new ValidatePhone();
        return validatePhone.validateString(phoneNumber);
    }
    
    public boolean validateName(String name) {
    	// TODO: your work
        ValidateInterface validateName = new ValidateName();
        return validateName.validateString(name);
    }
    
    public boolean validateAddress(String address) {
        ValidateInterface validateAddress = new ValidateAddress();
        return validateAddress.validateString(address);
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param amount
     * @return shippingFee
     */
    public int calculateShippingFee(int amount){
        ShippingFeeCalculator shippingFeeCalculator = new NormalShippingFee();
        return shippingFeeCalculator.calculateShippingFee(amount);
    }
}
