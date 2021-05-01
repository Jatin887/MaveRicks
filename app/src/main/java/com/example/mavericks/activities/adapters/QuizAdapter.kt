package com.example.mavericks.activities.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mavericks.R
import com.example.mavericks.activities.activities.QuestionActivity
import com.example.mavericks.activities.models.Quiz
import com.example.mavericks.activities.utils.ColorPicker
import com.example.mavericks.activities.utils.IconPicker
import kotlinx.android.synthetic.main.quiz_item.view.*

class QuizAdapter(val context:Context,val quizess:List<Quiz>): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    inner class QuizViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textViewTitle:TextView=itemView.findViewById(R.id.quizTitle)
        var iconView:ImageView=itemView.findViewById(R.id.quizIcon)
        var cardContainer:CardView=itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val v =LayoutInflater.from(parent.context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(v)
    }

    override fun getItemCount(): Int {
        return quizess.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTitle.text=quizess[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener{
            Toast.makeText(context,quizess[position].title, Toast.LENGTH_LONG).show()
            val intent = Intent(context,QuestionActivity::class.java)
            intent.putExtra("Date",quizess[position].title)
            context.startActivity(intent)

        }
    }
}