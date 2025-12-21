package com.vacral.notepadapply.ui.main.main.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.vacral.notepadapply.R
import com.vacral.notepadapply.databinding.ItemNotesBinding
import com.vacral.notepadapply.data.local.model.NoteModel

class NotesAdapter(
    val onLongClick: (NoteModel) -> Unit,
    val onClick: (NoteModel) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    val notesList = arrayListOf<NoteModel>()

    fun addNotes(notes: List<NoteModel>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.onBind(notesList[position])
    }

    override fun getItemCount() = notesList.size

    inner class NotesViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: NoteModel) {
            binding.apply {
                tvTitle.text = model.title
                tvDesc.text = model.desc
                tvTime.text = model.time.toString()

                // Меняем цвет через GradientDrawable
                val bgDrawable = backColor.background.mutate() as GradientDrawable
                bgDrawable.setColor(Color.parseColor(model.color))
            }

            itemView.setOnLongClickListener {
                showDeleteAlertDialog(model)
                true
            }

            itemView.setOnClickListener {
                onClick(model)
            }
        }

        // Находится внутри класса NotesViewHolder
        private fun showDeleteAlertDialog(model: NoteModel) {
            val builder = AlertDialog.Builder(itemView.context)
            val dialogView =
                LayoutInflater.from(itemView.context).inflate(R.layout.dialog_delete_note, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val deleteButton = dialogView.findViewById<View>(R.id.btn_delete_note)
            val cancelButton = dialogView.findViewById<View>(R.id.btn_cancellation)


            deleteButton.setOnClickListener {
                onLongClick(model)
                dialog.dismiss()
            }

            cancelButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

    }
}




