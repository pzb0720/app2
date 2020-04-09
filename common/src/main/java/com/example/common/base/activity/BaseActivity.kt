package com.example.common.base.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.common.AppManager
import com.example.common.BaseApplication
import com.example.common.R
import com.example.common.utils.AppUtils
import com.example.common.utils.ColorUtils
import com.example.common.utils.StatusBarUtil
import com.example.mylibrary.base.widget.WaitProgressDialog
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import kotlin.properties.Delegates

abstract class BaseActivity : SupportActivity(){
    open var binder: Unbinder? = null
    var context: Context by Delegates.notNull()

    private var mApp: BaseApplication? = null
    private var isTransAnim: Boolean = false
    private lateinit var mWaitProgressDialog: WaitProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binder = ButterKnife.bind(this)
        setContentView(getLayoutId())
        setStatusBar();
        initData();
        initView(savedInstanceState)
        AppManager.appManager.addActivity(this)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    private fun setStatusBar() {
        StatusBarUtil.setColor(this, ColorUtils.getColorPrimary(this))
    }

    open fun initData() {
        mApp = application as BaseApplication
        mWaitProgressDialog = WaitProgressDialog(this, 0)
        isTransAnim = true
    }

    fun initToolBar(toolbar: Toolbar, title: String) {
        toolbar.setBackgroundColor(ColorUtils.getColorPrimary(this))
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white)
        toolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    fun initToolBarWithOutBackgroundColor(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white)
        toolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            when (ev.getAction()) {
                MotionEvent.ACTION_DOWN -> {
                    val view = currentFocus
                    AppUtils.hideKeyboard(ev, view, this)//调用方法判断是否需要隐藏键盘
                }
                else -> {
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    fun setStatusBarColorTransparent() {
        StatusBarUtil.setTransparent(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        binder!!.unbind()
        AppManager.appManager.finishActivity(this)
    }

    fun showProgressDialog(message: String) {
        mWaitProgressDialog.setTitle(message)
        mWaitProgressDialog.show()

    }

    fun hideProgressDialog() {
        mWaitProgressDialog.dismiss()
    }

    fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator();
    }


    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
        if (isTransAnim)
            overridePendingTransition(
                R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out
            )
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    fun startActivity(clz: Class<*>, intent: Intent) {
        intent.setClass(this, clz)
        startActivity(intent)
        if (isTransAnim)
            overridePendingTransition(
                R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out
            )
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (isTransAnim)
            overridePendingTransition(
                R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out
            )
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    fun startActivityForResult(
        clz: Class<*>, bundle: Bundle?,
        requestCode: Int
    ) {
        val intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
        if (isTransAnim)
            overridePendingTransition(
                R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out
            )
    }
    abstract fun getLayoutId(): Int
}
