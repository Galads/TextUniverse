package com.galads.textgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

class MainActivity : AppCompatActivity() {

    private var characterData = CharacterGenerator.generate()

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putSerializable(CHARACTER_DATA_KEY, characterData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterData = savedInstanceState?.let {
            it.getSerializable(CHARACTER_DATA_KEY)
            as CharacterGenerator.CharacterData
        } ?: CharacterGenerator.generate()

        generateButton.setOnClickListener {
            characterData = CharacterGenerator.generate()
            displayCharacterData()
        }

        displayCharacterData()
    }
    private fun displayCharacterData() {
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}