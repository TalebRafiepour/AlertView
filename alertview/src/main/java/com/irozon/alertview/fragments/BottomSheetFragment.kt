package com.irozon.alertview.fragments

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertTheme
import com.irozon.alertview.R
import com.irozon.alertview.objects.AlertAction
import kotlinx.android.synthetic.main.action_layout_light.view.*
import kotlinx.android.synthetic.main.alert_layout_light.view.*
import java.util.*

/**
 * Created by hammad.akram on 3/14/18.
 */

@SuppressLint("ValidFragment")
class BottomSheetFragment(private val title: String, private val message: String, private val actions: ArrayList<AlertAction>, private val style: AlertStyle, private val theme: AlertTheme,private val fontPath:String? = null,private val textSize:Float = 14f) : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate view according to theme selected. Default is AlertTheme.LIGHT
        var view: View? = null
        if (theme == AlertTheme.LIGHT)
            view = inflater.inflate(R.layout.alert_layout_light, container, false)
        else if (theme == AlertTheme.DARK)
            view = inflater.inflate(R.layout.alert_layout_dark, container, false)


        // Set up view
        initView(view)

        return view
    }

    private fun initView(view: View?) {
        view!!.tvTitle.text = title
        view.tvMessage.text = message
        setFont(view.tvTitle)
        setFont(view.tvMessage)
        setFont(view.tvCancel)

        // In case of title or message is empty
        if (title.isEmpty()) view.tvTitle.visibility = View.GONE
        if (message.isEmpty()) view.tvMessage.visibility = View.GONE

        // Change view according to selected Style
        if (style == AlertStyle.BOTTOM_SHEET)
            view.tvCancel.visibility = View.GONE
        else if (style == AlertStyle.IOS)
            view.tvCancel.visibility = View.VISIBLE
        view.tvCancel.setOnClickListener({ dismiss() })

        // Inflate action views
        inflateActionsView(view.actionsLayout, actions)
    }

    private fun setFont(textView: TextView) {
        textView.textSize = textSize
        if (fontPath !== null) {
            try {
                textView.typeface = Typeface.createFromAsset(context?.resources?.assets, fontPath)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Inflate action views
     */
    private fun inflateActionsView(actionsLayout: LinearLayout, actions: ArrayList<AlertAction>) {
        for (action in actions) {

            // Inflate action view according to theme selected
            var view: View? = null
            if (theme == AlertTheme.LIGHT)
                view = LayoutInflater.from(context).inflate(R.layout.action_layout_light, null)
            else if (theme == AlertTheme.DARK)
                view = LayoutInflater.from(context).inflate(R.layout.action_layout_dark, null)

            view!!.tvAction.text = action.title
            setFont(view.tvAction)

            // Click listener for action.
            view.tvAction.setOnClickListener({
                dismiss()

                // For Kotlin
                action.action?.invoke(action)

                // For Java
                action.actionListener?.onActionClick(action)
            })

            // Action text color according to AlertActionStyle
            if (context != null) {
                when (action.style) {
                    AlertActionStyle.POSITIVE -> {
                        view.tvAction.setTextColor(ContextCompat.getColor(context!!, R.color.green))
                    }
                    AlertActionStyle.NEGATIVE -> {
                        view.tvAction.setTextColor(ContextCompat.getColor(context!!, R.color.red))
                    }
                    AlertActionStyle.DEFAULT -> {
                        if (theme == AlertTheme.LIGHT)
                            view.tvAction.setTextColor(ContextCompat.getColor(context!!, R.color.darkGray))
                        else if (theme == AlertTheme.DARK)
                            view.tvAction.setTextColor(ContextCompat.getColor(context!!, R.color.lightWhite))
                    }

                }
            }

            // Add view to layout
            actionsLayout.addView(view)
        }
    }
}