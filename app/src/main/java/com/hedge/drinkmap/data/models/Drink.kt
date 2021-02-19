package com.hedge.drinkmap.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Drink(
    @SerializedName("idDrink")
    val id:String="",
    @SerializedName("strDrinkThumb")
    val image: String = "",
    @SerializedName("strDrink")
    val name: String = "",
    @SerializedName("strInstructions")
    val description: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Drink> {
        override fun createFromParcel(parcel: Parcel): Drink {
            return Drink(parcel)
        }

        override fun newArray(size: Int): Array<Drink?> {
            return arrayOfNulls(size)
        }
    }

}

data class DrinkList(
    @SerializedName("drinks")
    val drinkList:List<Drink>)

@Entity
data class DrinkEntity(
    @PrimaryKey
    val drinkId: String,
    @ColumnInfo(name = "drink_image")
    val image: String,
    @ColumnInfo(name = "drink_name")
    val name: String,
    @ColumnInfo(name = "drink_instructions")
    val description: String
)