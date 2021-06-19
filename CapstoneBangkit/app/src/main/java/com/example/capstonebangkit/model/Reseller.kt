package com.example.capstonebangkit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Reseller (
        val logo : String?,
        val contact : String?,
        val name : String?,
        val modal : String?,
        val regulation : String?,
        val description : String?

) : Parcelable

