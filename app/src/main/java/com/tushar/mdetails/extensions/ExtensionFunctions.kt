package com.tushar.mdetails.extensions

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import com.tushar.mdetails.data.repository.Resource

/**
 * To add fragment from activity
 */
fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, backStackTag: String? = null) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment)
        backStackTag?.let { addToBackStack(it) }
    }
}

/**
 * To remove fragment from activity
 */
fun AppCompatActivity.removeFragment(frameId: Int) {
    val fragment = supportFragmentManager.findFragmentById(frameId)
    if (fragment != null)
        supportFragmentManager.beginTransaction().remove(fragment).commit()
}

/**
 * To replace fragment from activity
 */
fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    frameId: Int,
    backStackTag: String? = null
) {
    supportFragmentManager.inTransaction {
        replace(frameId, fragment)
        backStackTag?.let { addToBackStack(it) }
    }
}

/**
 * To get the fragment transaction
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

/**
 * To replace fragment from fragment
 */
fun Fragment.replaceFragment(
    context: AppCompatActivity,
    fragment: Fragment,
    frameId: Int,
    backStackTag: String? = null,
    imageView: ImageView
) {
    fragment.apply {
        enterTransition = Slide(Gravity.END)
        exitTransition = Slide(Gravity.START)
    }
    context.supportFragmentManager.inTransaction {
        replace(frameId, fragment)

        addSharedElement(imageView,ViewCompat.getTransitionName(imageView)!!)
        backStackTag?.let { addToBackStack(it) }
    }
}

/**
 * To Show a view
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * To Hide a view
 */
fun View.hide() {
    visibility = View.GONE
}

/**
 * Can show [Toast] from every [Activity].
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}