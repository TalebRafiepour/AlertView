package com.irozon.alertview

import android.support.v7.app.AppCompatActivity
import com.irozon.alertview.fragments.BottomSheetFragment
import com.irozon.alertview.fragments.DialogFragment
import com.irozon.alertview.objects.AlertAction

/**
 * Created by hammad.akram on 3/14/18.
 */
class AlertView(private var title: String, private var message: String, private var style: AlertStyle) {

    private var theme: AlertTheme = AlertTheme.LIGHT
    private var actions: ArrayList<AlertAction> = ArrayList()
    private var fontPath:String? = null
    private var textSize:Float = 14f
    private var cancelText:String? = null

    /**
     * Add Actions to AlertView
     * @param action: AlertAction
     */
    fun addAction(action: AlertAction) {
        actions.add(action)
    }

    /**
     * Show AlertView
     * @param activity: AppCompatActivity
     */
    fun show(activity: AppCompatActivity) {
        when (style) {
            AlertStyle.BOTTOM_SHEET -> {
                val bottomSheet = BottomSheetFragment(title, message, actions, style, theme,fontPath,textSize)
                if (cancelText != null){bottomSheet.setCancelButtonText(cancelText!!)}
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }
            AlertStyle.IOS -> {
                val bottomSheet = BottomSheetFragment(title, message, actions, style, theme,fontPath,textSize)
                if (cancelText != null){bottomSheet.setCancelButtonText(cancelText!!)}
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }
            AlertStyle.DIALOG -> {
                val bottomSheet = DialogFragment(title, message, actions, theme,fontPath,textSize)
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }
        }
    }

    /**
     * Set theme for the AlertView
     * @param theme: AlertTheme
     */
    fun setTheme(theme: AlertTheme) {
        this.theme = theme
    }

    fun setFontPath(path: String){
        this.fontPath = path
    }

    fun setTextSize(size: Float){
        this.textSize = size
    }

    fun setCancelButtonText(text: String){
        this.cancelText = text
    }
}