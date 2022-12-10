package be.ap.edu.mapsaver.adapters

import Attributes
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import be.ap.edu.mapsaver.R
import be.ap.edu.mapsaver.ToiletDetailsActivity
import be.ap.edu.mapsaver.fragments.ToiletDetailDialog
import kotlinx.android.synthetic.main.toilet_item.view.*

class ToiletListAdapter(private val activity: AppCompatActivity, val context: Context, var dataSet: List<Attributes>): RecyclerView.Adapter<ToiletListAdapter.ToiletListViewHolder>(){
    class ToiletListViewHolder(val view: View): RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToiletListViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.toilet_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ToiletListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToiletListViewHolder, position: Int) {
        val item = dataSet[position]
        if(dataSet[position].doelgroep == null){
            holder.view.toilet_genderlbl.text = "Genderneutraal"
        } else {
            holder.view.toilet_genderlbl.text = dataSet[position].doelgroep
        }
        if(dataSet[position].straat != null){
            holder.view.toilet_street.text = dataSet[position].straat
        }

        holder.itemView.setOnClickListener {
            val fragment = ToiletDetailDialog()
            val args = Bundle()
            args.putSerializable("item",item)
            fragment.arguments = args
            fragment.show(activity.supportFragmentManager,"dialog")
        }
    }
    override fun getItemCount() = dataSet.count()

}