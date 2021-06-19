package com.example.capstonebangkit.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.capstonebangkit.model.Franchise
import com.example.capstonebangkit.model.Reseller
import com.google.firebase.firestore.FirebaseFirestore

class RemoteDataSource {
    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }
    private val db = FirebaseFirestore.getInstance()

    fun getReseller () : MutableLiveData<List<Reseller>> {

        val listReseller : MutableLiveData<List<Reseller>> = MutableLiveData()
        val docRef = db.collection("reseller")

        val list = ArrayList<Reseller>()
        docRef.get().addOnCompleteListener { document->
            list.clear()
            for (i in document.result!!) {
                val reseller = Reseller(
                        i.getString("logo"),
                        i.getString("contact"),
                        i.getString("name"),
                        i.getString("modal"),
                        i.getString("description"),
                        i.getString("regulation")

                )
                list.add(reseller)
            }
            listReseller.postValue(list)
        }.addOnFailureListener { e->
            Log.d("Remote data source","${e.message}")
        }
        return listReseller
    }

    fun getFranchise () : MutableLiveData<List<Franchise>> {

        val listFranchise : MutableLiveData<List<Franchise>> = MutableLiveData()
        val docRef = db.collection("franchise")

        val list = ArrayList<Franchise>()
        docRef.get().addOnCompleteListener { document->
            list.clear()
            for (i in document.result!!) {
                val franchise = Franchise(
                        i.getString("logo"),
                        i.getString("contact"),
                        i.getString("name"),
                        i.getString("modal"),
                        i.getString("range"),
                        i.getString("regulation")
                )
                list.add(franchise)
            }
            listFranchise.postValue(list)
        }.addOnFailureListener { e->
            Log.d("Remote data source","${e.message}")
        }
        return listFranchise
    }
}