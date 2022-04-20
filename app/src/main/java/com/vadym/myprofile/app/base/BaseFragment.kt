package com.vadym.myprofile.app.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VBinding : ViewBinding>(
    private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) :
    Fragment() {

    private var _binding: VBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Debuging", "${this.javaClass.simpleName} onCreateView")
        _binding = inflaterMethod.invoke(inflater, container, false)
        setObservers()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Debuging", "${this.javaClass.simpleName}  Destroyed")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected open fun setObservers() {}

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }
}