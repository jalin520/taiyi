package com.t1_network.taiyi.model.bean.good;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by David on 2016/2/19.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class GoodDetailInfoBean implements Parcelable {




    private String name;

    private List<CategoryEntity> category;

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(List<CategoryEntity> category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public List<CategoryEntity> getCategory() {
        return category;
    }

    public static class CategoryEntity implements Parcelable {
        private String name;
        private String value;

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.value);
        }

        public CategoryEntity() {
        }

        protected CategoryEntity(Parcel in) {
            this.name = in.readString();
            this.value = in.readString();
        }

        public static final Creator<CategoryEntity> CREATOR = new Creator<CategoryEntity>() {
            public CategoryEntity createFromParcel(Parcel source) {
                return new CategoryEntity(source);
            }

            public CategoryEntity[] newArray(int size) {
                return new CategoryEntity[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(category);
    }

    public GoodDetailInfoBean() {
    }

    protected GoodDetailInfoBean(Parcel in) {
        this.name = in.readString();
        this.category = in.createTypedArrayList(CategoryEntity.CREATOR);
    }

    public static final Parcelable.Creator<GoodDetailInfoBean> CREATOR = new Parcelable.Creator<GoodDetailInfoBean>() {
        public GoodDetailInfoBean createFromParcel(Parcel source) {
            return new GoodDetailInfoBean(source);
        }

        public GoodDetailInfoBean[] newArray(int size) {
            return new GoodDetailInfoBean[size];
        }
    };
}
