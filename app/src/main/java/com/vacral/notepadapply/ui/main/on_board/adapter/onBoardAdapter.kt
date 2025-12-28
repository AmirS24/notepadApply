package com.vacral.notepadapply.ui.main.on_board.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.vacral.notepadapply.databinding.ItemOnBoardBinding
import com.vacral.notepadapply.data.local.model.OnBoardModel

class OnBoardAdapter(
    private val listOnBoard: List<OnBoardModel>,
    private val onStartClick: () -> Unit,
    private val onSkipClick: (Int) -> Unit
) : RecyclerView.Adapter<OnBoardAdapter.OnBoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {
        return OnBoardViewHolder(
            ItemOnBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {
        holder.onBind(listOnBoard[position], position)
    }

    override fun getItemCount() = listOnBoard.size

    inner class OnBoardViewHolder(private val binding: ItemOnBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: OnBoardModel, position: Int) {
            binding.apply {
                tvConvenience.text = model.title
                tvDesc.text = model.desc
                lottie.setAnimation(model.lottie)
                lottie.repeatCount = LottieDrawable.INFINITE
                lottie.playAnimation()

                if (position == listOnBoard.lastIndex) {
                    btnStart.visibility = View.VISIBLE
                    tvSkip.visibility = View.GONE
                } else {
                    btnStart.visibility = View.GONE
                    tvSkip.visibility = View.VISIBLE
                }

                btnStart.setOnClickListener {
                    onStartClick()
                }
                tvSkip.setOnClickListener {
                    onSkipClick(position + 1)
                }
            }
        }
    }
}
