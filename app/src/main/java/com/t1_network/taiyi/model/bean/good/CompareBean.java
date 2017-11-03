package com.t1_network.taiyi.model.bean.good;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 2016/1/20.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class CompareBean {


    @SerializedName("spec")
    private List<SpecEntity> spec;

    public void setSpec(List<SpecEntity> spec) {
        this.spec = spec;
    }

    public List<SpecEntity> getSpec() {
        return spec;
    }


    public static class SpecEntity {
        @SerializedName("dest")
        private String dest;
        @SerializedName("name")
        private String name;
        @SerializedName("src")
        private String src;

        public void setDest(String dest) {
            this.dest = dest;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDest() {
            return dest;
        }

        public String getName() {
            return name;
        }

        public String getSrc() {
            return src;
        }
    }
}
