package com.ssafy.withssafy.src.main.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentClassCurriculumBinding

class ClassCurriculumFragment : BaseFragment<FragmentClassCurriculumBinding>(FragmentClassCurriculumBinding::bind,R.layout.fragment_class_curriculum) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClassCurriculumFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}