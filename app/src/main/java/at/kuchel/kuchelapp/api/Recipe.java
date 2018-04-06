package at.kuchel.kuchelapp.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Recipe implements Parcelable {

    private Long id;

    private String username;

    private String name;

    private String duration;

    private String difficulty;

    private Date modifiedDate;

    private List<Instruction> instructions = new ArrayList<>();

    private List<Ingredient> ingredients = new ArrayList<>();

    private List<Image> images = new ArrayList<>();

    public Recipe(){

    }


    protected Recipe(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        username = in.readString();
        name = in.readString();
        duration = in.readString();
        difficulty = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(duration);
        dest.writeString(difficulty);
    }
}
