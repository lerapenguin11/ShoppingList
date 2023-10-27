package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.presentation.viewmodel.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var tilName : TextInputLayout
    private lateinit var tilCount : TextInputLayout
    private lateinit var btSave : Button
    private lateinit var etName : EditText
    private lateinit var etCount : EditText
    private var shopID = ShopItem.UNDEFINED_ID
    private lateinit var viewModel : ShopItemViewModel
    private var screenMode = MODE_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ShopItemFragment", "onCreate")
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_item, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initViews(view)
        countTextListener()
        nameTextListener()
        launchRightMode()
        observeViewModel()
    }

     private fun observeViewModel() {
         viewModel.errorInputCount.observe(viewLifecycleOwner){
             val message = if (it){
                 getString(R.string.error_input_count)
             }else{
                 null
             }
             tilCount.error = message
         }

         viewModel.errorInputName.observe(viewLifecycleOwner){
             val message = if (it){
                 getString(R.string.error_input_name)
             }else{
                 null
             }
             tilName.error = message
         }

         viewModel.shouldCloseScree.observe(viewLifecycleOwner){
             activity?.onBackPressed()
     }
 }

     private fun launchRightMode() {
         when (screenMode) {
             MODE_EDIT -> launchEditMode()
             MODE_ADD -> launchAddMode()
         }
     }

     private fun countTextListener() {
         etCount.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 viewModel.resetErrorInputCount()
             }

             override fun afterTextChanged(p0: Editable?) {}
         })
     }

     private fun nameTextListener() {
         etName.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 viewModel.resetErrorInputName()
             }

             override fun afterTextChanged(p0: Editable?) {}
         })
     }

     private fun launchAddMode() {
         btSave.setOnClickListener {
             viewModel.addShopItem(
                 inputName = etName.text.toString(),
                 inputCount = etCount.text.toString()
             )
         }
     }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId = shopID)
        viewModel.shopItem.observe(viewLifecycleOwner, Observer {
            etName.setText(it.name)
            etCount.setText(it.count)
        })
        btSave.setOnClickListener {
            viewModel.updateShopItem(inputName = etName.text.toString(),
                inputCount = etCount.text.toString())

        }
    }

    private fun parseParams(){
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString((EXTRA_SCREEN_MODE))
        if (mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT){
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param shop id is absent")
            }
            shopID = args.getInt(EXTRA_SHOP_ITEM_ID, -1)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        btSave = view.findViewById(R.id.save_button)
        etName = view.findViewById(R.id.ed_name)
        etCount = view.findViewById(R.id.ed_count)
    }

    companion object{

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem() : ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId : Int) : ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}