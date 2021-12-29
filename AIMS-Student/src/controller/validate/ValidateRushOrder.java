package controller.validate;

import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.shipping.Location;

import java.util.List;

public class ValidateRushOrder {

    /**
     * Kiểm tra trong giỏ hàng có sản phẩm hỗ trợ giao hàng nhanh hay không
     * @param mediaList : list sản phẩm trong giỏ hàng
     * @return : true/false
     */
    public Boolean validateProductSupport(List mediaList) {
        int count = 0;

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
        return new Location().checkAddressSupport(province);
    }

    public boolean checkProduct() {
        return false;
    }

    /**
     * This method calculates the shipping fees of order
     * @param amount: đơn đặt hàng
     * @return shippingFee
     */
}
