package com.example.mylibrary.base.fragment

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.common.BaseApplication
import com.example.mylibrary.base.widget.WaitProgressDialog
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment : SupportFragment() {
     lateinit var TAG: String
     lateinit var mContext: Context
     lateinit var mActivity: Activity

     lateinit var mApplication: BaseApplication
    private  var mWaitProgressDialog: WaitProgressDialog? = null
    private var binder: Unbinder? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        TAG = javaClass.simpleName
        binder = ButterKnife.bind(this, view)
        getBundle(arguments)
        initData()
        initView(view, savedInstanceState)
    }

    protected abstract fun initView(view: View, savedInstanceState: Bundle?)


    /**
     * 在监听器之前把数据准备好
     */
     fun initData() {
        mContext = BaseApplication.context
        mApplication = mActivity.application as BaseApplication
        mWaitProgressDialog = WaitProgressDialog(mActivity, 0)

    }

    @Nullable
    override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return if (getLayoutView() != null) {
            getLayoutView()
        } else {
            //            return inflater.inflate(getLayoutId(), null);
            inflater.inflate(getLayoutId(), container, false)
        }
    }

    private fun getLayoutView(): View? {
        return null
    }

    protected abstract fun getLayoutId(): Int

    /**
     * 得到Activity传进来的值
     */
    private fun getBundle(bundle: Bundle?) {}


    /**
     * 处理回退事件
     *
     * @return true 事件已消费
     *
     *
     * false 事件向上传递
     */
    override fun onBackPressedSupport(): Boolean {
        if (fragmentManager!!.backStackEntryCount > 1) {
            //如果当前存在fragment>1，当前fragment出栈
            pop()
        } else {
            //已经退栈到root fragment，交由Activity处理回退事件
            return false
        }
        return true
    }


    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    protected fun showProgressDialog(msg: String) {
        if (mWaitProgressDialog!!.isShowing) {
            mWaitProgressDialog!!.dismiss()
        }

        mWaitProgressDialog!!.setMessage(msg)
        mWaitProgressDialog!!.show()
    }

    /**
     * 隐藏提示框
     */
    protected fun hideProgressDialog() {
        if (mWaitProgressDialog != null) {
            mWaitProgressDialog!!.dismiss()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binder != null)
            binder!!.unbind()
    }


}