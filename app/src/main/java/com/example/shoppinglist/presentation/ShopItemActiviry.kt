package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.entity.ShopItem
import com.example.shoppinglist.presentation.viewmodel.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemActiviry : AppCompatActivity() {
    private lateinit var viewModel : ShopItemViewModel
    private lateinit var tilName : TextInputLayout
    private lateinit var tilCount : TextInputLayout
    private lateinit var btSave : Button
    private lateinit var etName : EditText
    private lateinit var etCount : EditText
    private var shopID = ShopItem.UNDEFINED_ID

    private var screenMode = MODE_UNKNOWN
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_activiry)
        parseIntent()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initViews()
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
        /*nameTextListener()
        countTextListener()*/
    }

    private fun countTextListener() {
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tilCount.error = null
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun nameTextListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tilName.error = null
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun launchAddMode() {
        btSave.setOnClickListener {
            if (etCount.text.isEmpty()){
                tilCount.error = "Error"
            } else if(etName.text.isEmpty()){
                tilName.error = "Error"
            } else if(etName.text.isEmpty() && etCount.text.isEmpty()){
                tilCount.error = "Error"
                tilName.error = "Error"
                if (etName.text.isNotEmpty() && etCount.text.isNotEmpty()){
                    nameTextListener()
                    countTextListener()
                }
            }
            else{
                viewModel.addShopItem(
                    inputName = etName.text.toString(),
                    inputCount = etCount.text.toString()
                )
                intentMainActivity()
            }
        }
    }

    private fun intentMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId = shopID)
        viewModel.shopItem.observe(this, Observer {
            etName.setText(it.name)
            etCount.setText(it.count)
            btSave.setOnClickListener {
                viewModel.updateShopItem(inputName = etName.text.toString(),
                    inputCount = etCount.text.toString())
                intentMainActivity()
            }
        })
    }

    private fun parseIntent(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra((EXTRA_SCREEN_MODE))
        if (mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if(screenMode == MODE_EDIT){
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param shop id is absent")
            }
            shopID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        }

    }
    private fun initViews(){
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        btSave = findViewById(R.id.save_button)
        etName = findViewById(R.id.ed_name)
        etCount = findViewById(R.id.ed_count)
    }

    companion object{

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context) : Intent{
            val intent = Intent(context, ShopItemActiviry::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId : Int) : Intent{
            val intent = Intent(context, ShopItemActiviry::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}