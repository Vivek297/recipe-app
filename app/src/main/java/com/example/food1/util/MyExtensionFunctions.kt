package com.example.food1.util

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {

        override fun onChanged(value: T) {
            removeObserver(this)
            observer.onChanged(value)

        }
    })
}

//inline fun <reified T> Bundle.getParcelableCompat(key: String): T? = when {
//    Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
//    else -> @Suppress("DEPRECATION") getParcelable(key) as? T?
//}

//inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? =
//
//    BundleCompat.getParcelable(this, key, T::class.java)
inline fun <reified T : Parcelable> Bundle.retrieveParcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}