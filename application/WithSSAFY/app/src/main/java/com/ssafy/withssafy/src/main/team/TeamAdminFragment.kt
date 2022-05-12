package com.ssafy.withssafy.src.main.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamAdminBinding

class TeamAdminFragment : BaseFragment<FragmentTeamAdminBinding>(FragmentTeamAdminBinding::bind, R.layout.fragment_team_admin) {
    var minPeople = 0
    var maxPeople = 0;
    var classification = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initSpinner()
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentTeamAdminPrev.setOnClickListener {
            this@TeamAdminFragment.findNavController().popBackStack()
        }
        binding.fragmentTeamAdminSuccess.setOnClickListener {
            teamViewModel.maxPeople = maxPeople
            teamViewModel.minPeople = minPeople
            teamViewModel.classificationType = classification
            teamViewModel.option = binding.fragmentTeamAdminOptionEdit.text.toString()
            this@TeamAdminFragment.findNavController().navigate(R.id.teamFragment)
        }
    }
    private fun initSpinner(){
        var bigClassification = arrayListOf<String>("공통","특화","자율")

        binding.fragmentTeamAdminBigClassificationSpinner.adapter = ArrayAdapter(requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,bigClassification)
        binding.fragmentTeamAdminBigClassificationSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                classification = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        var people = arrayListOf(1,2,3,4,5,6,7,8,9,10)
        binding.fragmentTeamAdminMinPeopleSpinner.adapter = ArrayAdapter(requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,people)
        binding.fragmentTeamAdminMaxPeopleSpinner.adapter = ArrayAdapter(requireContext(),
            com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,people)
        binding.fragmentTeamAdminMinPeopleSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                minPeople = people[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.fragmentTeamAdminMaxPeopleSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                maxPeople = people[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamAdminFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}