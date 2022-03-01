package com.example.youtubetto.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.youtubetto.R
import com.example.youtubetto.dto.IYoutubeObject

class CustomListViewAdapter(context: Context, youtubeList : ArrayList<IYoutubeObject>) : BaseAdapter() {

    private val listContext : Context
    private val list : ArrayList<IYoutubeObject>

    init {
        listContext = context
        list = youtubeList
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): IYoutubeObject {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(listContext)
        val subRow = layoutInflater.inflate(R.layout.subscription_list_row, viewGroup, false)

        subRow.findViewById<TextView>(R.id.Name_textView).text = getItem(position).title

        return subRow
    }
}