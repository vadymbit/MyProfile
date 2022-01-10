package com.vadym.myprofile.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding>(
    private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) :
    Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Debuging", "${this.javaClass.simpleName}  onCreate")
    }

    private var _binding: VBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Debuging", "${this.javaClass.simpleName} onCreateView")
        _binding = inflaterMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        initialize(savedInstanceState)
        setUpListeners()
        setUpObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Debuging", "${this.javaClass.simpleName}  Destroyed")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected open fun setUpListeners() {}
    protected open fun setUpObservers() {}
    protected open fun initialize() {}
    protected open fun initialize(savedInstanceState: Bundle?) {}
}