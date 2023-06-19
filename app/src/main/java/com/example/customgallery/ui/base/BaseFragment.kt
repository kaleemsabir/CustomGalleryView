package com.example.customgallery.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutRes: Int) :
    Fragment() {

    private var _bindings: DB? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val bindings get() = _bindings!!
    protected lateinit var viewModel: VM

    protected abstract fun getBindingVariable(): Int
    protected abstract fun getViewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[getViewModelClass()]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindings = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        bindings.lifecycleOwner = viewLifecycleOwner
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings.setVariable(getBindingVariable(), viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindings = null
    }


}