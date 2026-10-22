package org.insbaixcamp.bmicalculatorxml

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Valors inicials
    private var height = 186
    private var weight = 82

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        // -----------------------------
        // REFERÈNCIES A LA VISTA
        // -----------------------------

        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val etName = findViewById<TextInputEditText>(R.id.etName)

        val tvHeight = findViewById<TextView>(R.id.tvHeight)
        val sliderHeight = findViewById<Slider>(R.id.sliderHeight)

        val tvWeight = findViewById<TextView>(R.id.tvWeight)
        val btnMinus = findViewById<MaterialButton>(R.id.btnMinus)
        val btnPlus = findViewById<MaterialButton>(R.id.btnPlus)

        val btnCalculate = findViewById<MaterialButton>(R.id.btnCalculate)

        // Referències de la Carta Pokémon Resultat
        val cardResult = findViewById<MaterialCardView>(R.id.cardResult)
        val layoutCardContent = findViewById<LinearLayout>(R.id.layoutCardContent)

        val tvPokemonStage = findViewById<TextView>(R.id.tvPokemonStage)
        val tvPokemonName = findViewById<TextView>(R.id.tvPokemonName)
        val tvPokemonHP = findViewById<TextView>(R.id.tvPokemonHP)
        val ivTypeIcon = findViewById<ImageView>(R.id.ivTypeIcon)

        val ivPokemonArt = findViewById<ImageView>(R.id.ivPokemonArt)
        val tvTrainerTag = findViewById<TextView>(R.id.tvTrainerTag)
        val tvRarityBadge = findViewById<TextView>(R.id.tvRarityBadge)

        val tvPokemonInfo = findViewById<TextView>(R.id.tvPokemonInfo)

        val ivMoveEnergy1 = findViewById<ImageView>(R.id.ivMoveEnergy1)
        val ivMoveEnergy2 = findViewById<ImageView>(R.id.ivMoveEnergy2)
        val tvMoveName = findViewById<TextView>(R.id.tvMoveName)
        val tvBMIDamage = findViewById<TextView>(R.id.tvBMIDamage)
        val tvBMIStatus = findViewById<TextView>(R.id.tvBMIStatus)
        val tvMoveDescription = findViewById<TextView>(R.id.tvMoveDescription)
        val tvCardFooter = findViewById<TextView>(R.id.tvCardFooter)


        // -----------------------------
        // AMAGUEM EL RESULTAT AL PRINCIPI
        // -----------------------------

        cardResult.visibility = View.GONE


        // -----------------------------
        // ALÇADA
        // -----------------------------

        sliderHeight.addOnChangeListener { _, value, _ ->
            height = value.toInt()
            tvHeight.text = height.toString()
        }


        // -----------------------------
        // PES
        // -----------------------------

        btnMinus.setOnClickListener {
            if (weight > 30) {
                weight--
                tvWeight.text = weight.toString()
            }
        }

        btnPlus.setOnClickListener {
            if (weight < 250) {
                weight++
                tvWeight.text = weight.toString()
            }
        }


        // -----------------------------
        // CALCULAR IMC & MOSTRAR CARTA POKÉMON
        // -----------------------------

        btnCalculate.setOnClickListener {

            val name = etName.text.toString().trim()

            // Comprovem que hi hagi nom
            if (name.isEmpty()) {
                tilName.error = "Introdueix el teu nom d'entrenador"
                return@setOnClickListener
            } else {
                tilName.error = null
            }

            // Convertim cm a metres
            val heightMeters = height / 100.0

            // Fórmula IMC
            val bmi = weight / (heightMeters * heightMeters)

            // Nom de l'entrenador
            tvTrainerTag.text = "Entrenador: $name"
            tvBMIDamage.text = String.format(Locale.getDefault(), "IMC: %.1f", bmi)

            // Segons el resultat IMC mostrem la carta Pokémon corresponent:
            // 1. BMI baix (< 18.5) -> MAGIKARP
            // 2. BMI mol bé (18.5 - 24.9) -> GYARADOS SHINY ✨
            // 3. BMI mol mal (>= 25) -> CATERPIE

            when {
                bmi < 18.5 -> {
                    // CARTA MAGIKARP (BMI BAJO)
                    tvPokemonStage.text = "BÁSICO"
                    tvPokemonName.text = "MAGIKARP"
                    tvPokemonHP.text = "30 HP"
                    ivTypeIcon.setImageResource(R.drawable.ic_water_energy)

                    ivPokemonArt.setImageResource(R.drawable.ic_magikarp)
                    tvRarityBadge.text = "◆ COMÚN"
                    tvPokemonInfo.text = "Nº 129 Pokémon Pez  |  Alt: 0.9m  |  Peso: 10.0kg"

                    ivMoveEnergy1.setImageResource(R.drawable.ic_water_energy)
                    ivMoveEnergy2.setImageResource(R.drawable.ic_water_energy)
                    tvMoveName.text = "Salpicadura / Splash"

                    tvBMIStatus.text = "●  BAIX PES"
                    tvBMIStatus.setTextColor(Color.parseColor("#6EA8FE"))
                    tvBMIStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#17345C"))

                    tvMoveDescription.text = "¡Salpicadura sin efecto! El teu IMC és baix (< 18,5). Magikarp necessita menjar més per poder evolucionar!"

                    layoutCardContent.setBackgroundColor(Color.parseColor("#102A43"))
                    cardResult.strokeColor = Color.parseColor("#29B6F6")
                    tvCardFooter.text = "129/151 ◆ Carta Pokémon BMI"
                }

                bmi < 25.0 -> {
                    // CARTA GYARADOS SHINY ✨ (BMI MUY BIEN / SALUDABLE)
                    tvPokemonStage.text = "FASE 1 - SHINY"
                    tvPokemonName.text = "GYARADOS SHINY ✨"
                    tvPokemonHP.text = "130 HP"
                    ivTypeIcon.setImageResource(R.drawable.ic_dragon_energy)

                    ivPokemonArt.setImageResource(R.drawable.ic_gyarados_shiny)
                    tvRarityBadge.text = "★ RARA SHINY ✨"
                    tvPokemonInfo.text = "Nº 130 Pokémon Atrocidad  |  Alt: 6.5m  |  Peso: 235.0kg"

                    ivMoveEnergy1.setImageResource(R.drawable.ic_dragon_energy)
                    ivMoveEnergy2.setImageResource(R.drawable.ic_dragon_energy)
                    tvMoveName.text = "Hiperrayo / Hyper Beam"

                    tvBMIStatus.text = "●  PES SALUDABLE"
                    tvBMIStatus.setTextColor(Color.parseColor("#54E69A"))
                    tvBMIStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#193D31"))

                    tvMoveDescription.text = "¡Ataque devastador! El teu IMC està en un estat impecable i legendari. ¡Tens la salut i força d'un Gyarados Shiny!"

                    layoutCardContent.setBackgroundColor(Color.parseColor("#2D1226"))
                    cardResult.strokeColor = Color.parseColor("#FFD700")
                    tvCardFooter.text = "130/151 ★ Carta Pokémon Shiny"
                }

                else -> {
                    // CARTA CATERPIE (BMI MUY MAL / SOBREPÈS - OBESITAT)
                    tvPokemonStage.text = "BÁSICO"
                    tvPokemonName.text = "CATERPIE"
                    tvPokemonHP.text = "40 HP"
                    ivTypeIcon.setImageResource(R.drawable.ic_bug_energy)

                    ivPokemonArt.setImageResource(R.drawable.ic_caterpie)
                    tvRarityBadge.text = "● COMÚN"
                    tvPokemonInfo.text = "Nº 010 Pokémon Gusano  |  Alt: 0.3m  |  Peso: 2.9kg"

                    ivMoveEnergy1.setImageResource(R.drawable.ic_bug_energy)
                    ivMoveEnergy2.setImageResource(R.drawable.ic_bug_energy)
                    tvMoveName.text = "Disparo Demora / String Shot"

                    if (bmi < 30) {
                        tvBMIStatus.text = "●  SOBREPÈS"
                        tvBMIStatus.setTextColor(Color.parseColor("#FFB84D"))
                        tvBMIStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4A3517"))
                    } else {
                        tvBMIStatus.text = "●  OBESITAT"
                        tvBMIStatus.setTextColor(Color.parseColor("#FF6B78"))
                        tvBMIStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4A2028"))
                    }

                    tvMoveDescription.text = "¡Disparo Demora frena el teu moviment! El teu IMC és elevat (≥ 25). Caterpie necessita un bon entrenament per moure's millor!"

                    layoutCardContent.setBackgroundColor(Color.parseColor("#142918"))
                    cardResult.strokeColor = Color.parseColor("#66BB6A")
                    tvCardFooter.text = "010/151 ● Carta Pokémon BMI"
                }
            }

            // Fem visible la targeta de resultat Pokémon
            cardResult.visibility = View.VISIBLE
        }
    }
}