package com.example.youtubetto.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.youtubetto.R
import com.example.youtubetto.dto.IYoutubeObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(listContext)
        val subRow = layoutInflater.inflate(R.layout.subscription_list_row, viewGroup, false)

        println("photoUrl " + getItem(position).photoUrl)

        var title : String = getItem(position).title

        println(title)
        var formattedTitle = title.replace("'", "\\\\'");
        formattedTitle = formattedTitle.replace("\"", "\\\\\"");
        formattedTitle = formattedTitle.replace("&quot;", "\"");
        formattedTitle = formattedTitle.replace("&amp;", "&");
        formattedTitle = formattedTitle.replace("&#39;", "\'");
        subRow.findViewById<TextView>(R.id.Name_textView).text = formattedTitle

        var formattedPublishTime : String = ""
        if(getItem(position).publishTime != null) {
            val zonedTime = ZonedDateTime.parse(getItem(position).publishTime)
            formattedPublishTime = zonedTime.format(DateTimeFormatter.ofPattern("d MMMM u, HH:mm"))
        }

        subRow.findViewById<TextView>(R.id.publish_time_textView).text = formattedPublishTime

        var thumbnailView = subRow.findViewById<ImageView>(R.id.video_thumbnail)
        bindImage(thumbnailView, getItem(position).photoUrl)

        return subRow
    }


    @BindingAdapter("video_thumbnail")
    fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            imgView.load(imgUri)
        }
    }
}