package com.t1_network.taiyi.model.bean.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 2016/1/18.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class AfterSaleDetail {

    @SerializedName("orderback")
    private OrderbackEntity orderback;

    public void setOrderback(OrderbackEntity orderback) {
        this.orderback = orderback;
    }

    public OrderbackEntity getOrderback() {
        return orderback;
    }

    public static class OrderbackEntity {
        @SerializedName("process")
        private List<ProcessEntity> process;

        public List<ProcessEntity> getProcess() {
            return process;
        }

        public void setProcess(List<ProcessEntity> process) {
            this.process = process;
        }

        public static class ProcessEntity {
            @SerializedName("content")
            private String content;
            @SerializedName("time")
            private String time;

            public void setContent(String content) {
                this.content = content;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContent() {
                return content;
            }

            public String getTime() {
                return time;
            }
        }
    }
}
