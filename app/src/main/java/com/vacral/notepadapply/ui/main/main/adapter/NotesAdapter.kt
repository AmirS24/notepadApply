package com.vacral.notepadapply.ui.main.main.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
                onLongClick(model)
                true
            }

            itemView.setOnClickListener {
                onClick(model)
            }
        }
    }
}
