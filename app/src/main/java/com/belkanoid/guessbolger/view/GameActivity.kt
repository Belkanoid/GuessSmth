package com.belkanoid.guessbolger.view

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.belkanoid.guessbolger.R
import java.util.*
import kotlin.collections.ArrayList


class GameActivity : AppCompatActivity() {

    private lateinit var backButton : ImageButton
    private lateinit var levelTextView : TextView
    private lateinit var coinsImageButton : ImageButton
    private lateinit var contentImageView : ImageView
    private lateinit var wordGridView: GridView
    private lateinit var lettersGridView : GridView

    var data = arrayListOf( "i", "m", "a", "f", "r", "h", "e", "a", "t", "c", "r", "n")
    var word : ArrayList<String> = ArrayList()
    val guess = "minecraft"

    init {
        for (i in guess.indices)
            word.add("")
    }




    private lateinit var LettersAdapter: DataAdapter
    private lateinit var WordAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        findView()

        LettersAdapter = DataAdapter(this, data)
        WordAdapter = DataAdapter(this, word)
        lettersGridView.adapter = LettersAdapter
        wordGridView.adapter = WordAdapter
        adjustGridView()
        lettersGridView.setOnItemClickListener(letterGridViewListener)
        wordGridView.setOnItemClickListener(wordGridViewListener)
    }

    private val letterGridViewListener =
        OnItemClickListener { parent, v, position, id -> // выводим номер позиции
            Toast.makeText(this, data[position], Toast.LENGTH_SHORT).show()
            var seted = false
            if (word.contains("")) {
                for (i in 0 until word.size) {
                    if (word[i] == "") {
                        word[i] = data[position]
                        seted = true
                        break
                    }
                }
                if(!seted) word.add(data[position])
                val letterTextView : TextView = v.findViewById(R.id.game_letter)
                letterTextView.setBackgroundResource(R.drawable.letter_button_pressed)
                letterTextView.text = ""
                WordAdapter.notifyDataSetChanged()
            }
            else {
                Toast.makeText(this, "WRONG ANSWER!!!", Toast.LENGTH_LONG).show()
            }
        }

    private val wordGridViewListener =
        OnItemClickListener { parent, v, position, id ->
            Toast.makeText(this, "${data.size}", Toast.LENGTH_SHORT).show()
            word[position] = ""
            val letterTextView : TextView = v.findViewById(R.id.game_letter)
            WordAdapter.notifyDataSetChanged()
            LettersAdapter
        }

    fun adjustGridView() {
        wordGridView.numColumns = guess.length

        lettersGridView.verticalSpacing = 5;
        lettersGridView.horizontalSpacing = 5;

        wordGridView.verticalSpacing = 5;
        wordGridView.horizontalSpacing = 5;
    }


    class DataAdapter(context: Context, letters: List<String>) :
        BaseAdapter() {
        val letters = letters

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getCount() = letters.size
        override fun getItem(position: Int) = letters[position]
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
              view = layoutInflater.inflate(R.layout.list_item_view, parent, false)
            }
            val letterTextView = view?.findViewById<TextView>(R.id.game_letter)
            letterTextView?.text = letters[position]
            when {
                letterTextView?.text != "" -> {
                    letterTextView?.setBackgroundResource(R.drawable.letter_button_normal)
                }
                else -> {
                    letterTextView.setBackgroundResource(R.drawable.letter_button_pressed)
                }
            }


            return view!!
        }
    }

    private fun findView() {
        backButton = findViewById(R.id.game_back_btn)
        levelTextView = findViewById(R.id.game_level)
        coinsImageButton = findViewById(R.id.game_coins)
        contentImageView = findViewById(R.id.game_content_image)
        wordGridView = findViewById<View>(R.id.game_word) as GridView
        lettersGridView = findViewById<View>(R.id.letters_recyclerview) as GridView
    }

    companion object {
        fun newIntent(context : Context) : Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

}