package com.weaponoid.uiguru.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.model.UI
import com.weaponoid.uiguru.util.loadListImage
import com.weaponoid.uiguru.views.AdobeXDDirections
import com.weaponoid.uiguru.views.FigmaSearchDirections
import com.weaponoid.uiguru.views.SketchSearchDirections
import com.weaponoid.uiguru.views.XdSearchDirections
import kotlinx.android.synthetic.main.list_item.view.*

class SearchAdapter(private val uiList: ArrayList<UI>,val type: String) :
    RecyclerView.Adapter<SearchAdapter.uiViewHolder>() {


    fun updateUiList(newUiList: ArrayList<UI>) {
        uiList.clear()
        uiList.addAll(newUiList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): uiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return uiViewHolder(view)
    }

    override fun onBindViewHolder(holder: uiViewHolder, position: Int) {
        holder.view.uiListImage.loadListImage(uiList[position].imageUrl)
        holder.view.uiListTitle.setText(uiList[position].title)
        holder.view.uiListItem.setOnClickListener { view ->


            println(type)
          //  val action1  = SearchResultsDirections.

            when(type){
                "xd" ->{
                    val action = XdSearchDirections.xdSearchToDetail(uiList[position])
                    Navigation.findNavController(view).navigate(action)
                }

                "sketch" -> {
                    val action = SketchSearchDirections.sketchSearchToDetail(uiList[position])
                    Navigation.findNavController(view).navigate(action)
                }

                "figma" -> {
                    val action = FigmaSearchDirections.figmaSearchToDetail(uiList[position])
                    Navigation.findNavController(view).navigate(action)
                }
            }

//            val action = SearchResultsDirections.searchToDetail(uiList[position])
//            Navigation.findNavController(view).navigate(action)


        }
    }


    override fun getItemCount(): Int {
        return uiList.size
    }


    class uiViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }
}