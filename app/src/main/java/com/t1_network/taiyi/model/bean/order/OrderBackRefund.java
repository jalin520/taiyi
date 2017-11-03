package com.t1_network.taiyi.model.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 2016/2/23.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class OrderBackRefund implements Parcelable {

    private int voucher;
    private double amount;
    private double refund;

    public void setVoucher(int voucher) {
        this.voucher = voucher;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }

    public int getVoucher() {
        return voucher;
    }

    public double getAmount() {
        return amount;
    }

    public double getRefund() {
        return refund;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.voucher);
        dest.writeDouble(this.amount);
        dest.writeDouble(this.refund);
    }

    public OrderBackRefund() {
    }

    protected OrderBackRefund(Parcel in) {
        this.voucher = in.readInt();
        this.amount = in.readDouble();
        this.refund = in.readDouble();
    }

    public static final Parcelable.Creator<OrderBackRefund> CREATOR = new Parcelable.Creator<OrderBackRefund>() {
        public OrderBackRefund createFromParcel(Parcel source) {
            return new OrderBackRefund(source);
        }

        public OrderBackRefund[] newArray(int size) {
            return new OrderBackRefund[size];
        }
    };
}
