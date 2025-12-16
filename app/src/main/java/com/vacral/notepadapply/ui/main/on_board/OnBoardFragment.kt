package com.vacral.notepadapply.ui.main.on_board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.navigation.fragment.findNavController
import com.vacral.notepadapply.MainActivity
import com.vacral.notepadapply.data.local.Pref
import com.vacral.notepadapply.databinding.FragmentOnBoardBinding
import com.vacral.notepadapply.databinding.ItemOnBoardBinding
import com.vacral.notepadapply.model.OnBoardModel
import com.vacral.notepadapply.ui.main.on_board.adapter.OnBoardAdapter


class OnBoardFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardBinding

    private lateinit var adapter: OnBoardAdapter

    private lateinit var pref: Pref


    override fun onCreateView(
        
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = Pref(requireContext())

        adapter = OnBoardAdapter(getOnBoardList(), ::navigateToMain, ::onSkip)
        binding.vpOnBoard.adapter = adapter
        binding.circleIndicator.setViewPager(binding.vpOnBoard)


    }

    private fun navigateToMain() {
        Pref(requireContext()).setOnBoardShow()
        findNavController().navigate(
            OnBoardFragmentDirections.actionOnBoardFragmentToMainFragment()
        )
    }

    private fun onSkip(number: Int) {
        binding.vpOnBoard.currentItem = number
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.enterImmersiveMode()
    }

    private fun getOnBoardList(): List<OnBoardModel> {
        return listOf(
            OnBoardModel(
                title = "Удобство",
                desc = "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно",
                lottie = "board_one.json"
            ),
            OnBoardModel(
                title = "Организация",
                desc = "Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время.",
                lottie = "board_two.json"
            ),
            OnBoardModel(
                title = "Синхронезация",
                desc = "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте.",
                lottie = "board_three.json"
            ),

            )
    }

}
