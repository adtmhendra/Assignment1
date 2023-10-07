package com.example.mealapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.mealapp.databinding.CustomChipsBinding
import com.google.android.material.chip.Chip

object Helper {
    fun Context.createChip(inflater: LayoutInflater, text: String): Chip {
        val chip = CustomChipsBinding.inflate(inflater).root
        chip.text = text
        return chip
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.startActivity(to: Class<*>, bundle: Bundle) {
        val intent = Intent(this, to)
        intent.putExtras(bundle)
        startActivity(intent, bundle)
    }
}