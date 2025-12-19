package com.vacral.notepadapply.ui.main.add_note

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vacral.notepadapply.App
import com.vacral.notepadapply.R
import com.vacral.notepadapply.data.local.model.NoteModel
import com.vacral.notepadapply.databinding.FragmentAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private var selectedContainer: FrameLayout? = null
    private var noteColor: String = "#535353"

    private val args: AddNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAddNoteBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.note?.let {
            binding.etTitle.setText(it.title)
            binding.etDesc.setText(it.desc)
            binding.tvComplete.text = "Сохранить"
            noteColor = it.color
        }

        binding.tvComplete.setOnClickListener { saveNote() }
        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
        binding.ivSetColor.setOnClickListener { showColorPopup(binding.ivSetColor) }
    }

    private fun saveNote() {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val realTime = formatter.format(Date())
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val note: NoteModel? = args.note

        if (note != null) {
            App.database.dao().updateNote(
                NoteModel(
                    title = title,
                    desc = desc,
                    time = realTime,
                    color = noteColor,
                    id = note.id
                )
            )
        } else {
            App.database.dao().addNote(
                NoteModel(
                    title = title,
                    desc = desc,
                    time = realTime,
                    color = noteColor
                )
            )
        }
        findNavController().navigateUp()
    }

    private fun showColorPopup(anchor: View) {
        val popupView = layoutInflater.inflate(R.layout.dialog_note_menu, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true


        popupWindow.showAsDropDown(anchor, 0, -anchor.height + 100)


        val containers = mapOf(
            "#FFF59D" to popupView.findViewById<FrameLayout>(R.id.containerYellow),
            "#B39DDB" to popupView.findViewById<FrameLayout>(R.id.containerPurple),
            "#FD99FF" to popupView.findViewById<FrameLayout>(R.id.containerPink),
            "#91f48f" to popupView.findViewById<FrameLayout>(R.id.containerGreen),
            "#9effff" to popupView.findViewById<FrameLayout>(R.id.container_light_green),
            "#ff9e9e" to popupView.findViewById<FrameLayout>(R.id.containerWhite_pink)
        )

        fun selectColor(container: FrameLayout, color: String) {
            selectedContainer?.background = null
            container.setBackgroundResource(R.drawable.bg_color_selected)
            selectedContainer = container
            noteColor = color
        }


        containers[noteColor]?.let { selectColor(it, noteColor) }


        containers.forEach { (color, container) ->
            container.setOnClickListener {
                selectColor(container, color)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

