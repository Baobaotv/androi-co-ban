package com.example.healthyapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.healthyapp.helper.SharedPreferenceHelper


abstract class BaseFragment<V : ViewBinding> : Fragment() {

    private var _binding: V? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> V
    protected var hasInitializedRootView = false
    protected val binding: V get() = _binding as V
    private val preferenceHelper by lazy { SharedPreferenceHelper(requireContext()) }
    protected val onBackPressed:(()->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            if (_binding == null)
                _binding = bindingInflater.invoke(layoutInflater, container, false)
            else (binding.root.parent as ViewGroup).removeView(binding.root)
        } catch (e: Exception) {

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            onBackPressed?.let { onBackPressed.invoke() }?:requireActivity().onBackPressed()
        }
        onSetupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun onSetupView()

}
