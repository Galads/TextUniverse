package com.galads.textgame

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.io.Serializable
import java.net.URL

private const val CHARACTER_DATA_API = "https://chargen-api.herokuapp.com/"

private fun <T> List<T>.rand() = shuffled().first()

private fun Int.roll() = (0 until this)
    .map { (1..6).toList().rand() }
    .sum()
    .toString()

private val firstName = listOf("Galads", "Andrey", "Bi*ch")
private val lastName = listOf("Lightweaver","Bookin","Pronin")

object CharacterGenerator {
    data class CharacterData(val name:String,
                             val race: String,
                             val dex: String,
                             val wis: String,
                             val str: String ) : Serializable

    private fun name() = "${firstName.rand()} ${lastName.rand()}"

    private fun race() = listOf("dwarf", "elf", "human", "halfing").rand()

    private fun dex() = 4.roll()

    private fun wis() = 3.roll()

    private fun str() = 5.roll()

    fun generate() = CharacterData(
        name = name(),
        race = race(),
        dex = dex(),
        wis = wis(),
        str = str()
    )

    fun fromApiData(apiData: String): CharacterData {
        val (name, race, dex, wis, str) = apiData.split(",")

        return CharacterData(name, race, dex, wis, str)
    }
}

fun fetchCharacterData(): Deferred<CharacterGenerator.CharacterData> {
    return async {
        val apiData = URL(CHARACTER_DATA_API).readText()
        CharacterGenerator.fromApiData(apiData)
    }
}