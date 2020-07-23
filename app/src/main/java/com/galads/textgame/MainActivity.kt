package com.galads.textgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch

private var Bundle.characterDatas
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY, value)

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

class MainActivity : AppCompatActivity() {

    private var characterData = CharacterGenerator.generate()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.characterDatas = characterData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterData = savedInstanceState?.characterDatas ?: CharacterGenerator.generate()

        generateButton.setOnClickListener {
            launch(UI){
                characterData = fetchCharacterData().await()
                displayCharacterData()
            }
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