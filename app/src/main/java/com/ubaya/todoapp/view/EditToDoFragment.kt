package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.ubaya.todoapp.R
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel

class EditToDoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_to_do, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        view.findViewById<TextView>(R.id.txtJudulToDo).text = "Edit Todo"
        view.findViewById<Button>(R.id.btnAdd).text = "Save Changes"

        val uuid = EditToDoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()
        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val rg = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            var radio = view.findViewById<RadioButton>(rg.checkedRadioButtonId)
            viewModel.update(view.findViewById<TextView>(R.id.txtTitles).text.toString(), view.findViewById<TextView>(R.id.txtNotes).text.toString(),radio.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }

    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            view?.findViewById<TextInputEditText>(R.id.txtTitles)?.setText(it.title)
            view?.findViewById<TextInputEditText>(R.id.txtNotes)?.setText(it.notes)
            when (it.priority) {
                1 -> view?.findViewById<RadioButton>(R.id.radioLow)?.isChecked = true
                2 -> view?.findViewById<RadioButton>(R.id.radioMedium)?.isChecked = true
                else -> view?.findViewById<RadioButton>(R.id.radioHigh)?.isChecked = true
            }

        })
    }

}