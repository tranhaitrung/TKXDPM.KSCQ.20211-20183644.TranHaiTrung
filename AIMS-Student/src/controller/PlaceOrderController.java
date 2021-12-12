package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    	if (phoneNumber.length() != 10) return false;
    	
    	if(!phoneNumber.startsWith("0")) return false;
    	
    	Pattern letter = Pattern.compile("[a-zA-z]");
    	Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    	Matcher hasLetter = letter.matcher(phoneNumber);
    	Matcher hasSpecial = special.matcher(phoneNumber);
    	
    	if (hasLetter.find() || hasSpecial.find()) return false;
    	
    	try {
			Integer.parseInt(phoneNumber);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    	return true;
    }
    
    public boolean validateName(String name) {
    	// TODO: your work
    	if (StringUtils.isBlank(name)) return false;
    	
    	//Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        //Pattern eight = Pattern.compile (".{8}");


        //Matcher hasLetter = letter.matcher(name);
        Matcher hasDigit = digit.matcher(name);
        Matcher hasSpecial = special.matcher(name);

        if (hasDigit.find() || hasSpecial.find()) {
        	return false;
        }
    	
    	return true;
    }
    
    public boolean validateAddress(String address) {
    	// TODO: your work
    	if (StringUtils.isBlank(address)) return false;
    	
    	Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]///~]");
    	
    	Matcher hasSpecial = special.matcher(address);
    	
    	if (hasSpecial.find()) return false; 
    	
    	return true;
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
