package com.vacral.notepadapply.ui.main.on_board.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vacral.notepadapply.databinding.ItemNotesBinding
import com.vacral.notepadapply.model.NoteModel

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    val notesList = arrayListOf<NoteModel>()

    fun addNotes(notes: List<NoteModel>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesViewHolder {
        return NotesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int
    ) {
        holder.onBind(notesList[position])
    }

    override fun getItemCount() = notesList.size


    class NotesViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: NoteModel) {
            binding.apply {
                tvTitle.text = model.title
                tvDesc.text = model.desc
                tvTime.text = model.time.toString()
            }
        }
    }
}
