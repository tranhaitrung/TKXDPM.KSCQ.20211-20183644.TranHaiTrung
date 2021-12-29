package controller.calculate;

import controller.PlaceOrderController;

import java.util.Random;
import java.util.logging.Logger;

public class NormalShippingFee implements ShippingFeeCalculator{
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    @Override
    public int calculateShippingFee(int amount) {
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * amount);
        LOGGER.info("Order Amount: " + amount + " -- Shipping Fees: " + fees);
        return fees;
    }
}
