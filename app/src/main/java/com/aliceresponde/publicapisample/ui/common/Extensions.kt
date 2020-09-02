package com.aliceresponde.countingapp.presentation.common

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aliceresponde.publicapisample.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

fun Fragment.setNavigationResult(result: String, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    } else
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Fragment.hideKeyboard() {
    val activity = this.activity
    if (activity is AppCompatActivity) {
        activity.hideKeyboard()
    }
}

fun Fragment.getStringArray(@ArrayRes id: Int): List<String> {
    activity?.let {
        return resources.getStringArray(id).toList()
    } ?: return listOf()
}

fun ImageView.loadFromUrl(url: String) {
    if(url.isNotEmpty()){
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_restauran)
            .error(R.drawable.ic_restauran)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}

fun ImageView.loadFromDrawable( @DrawableRes res: Int){
    Glide.with(this)
        .load(res)
        .into(this)
}