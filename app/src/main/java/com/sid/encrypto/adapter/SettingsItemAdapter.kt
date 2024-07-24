package com.sid.encrypto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sid.encrypto.data.model.SettingsItem
import com.sid.encrypto.databinding.SettingsItemBinding

class SettingsItemAdapter(
    val context: Context,
    private val settingsItemList: ArrayList<SettingsItem>,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SettingsItemAdapter.SettingsViewHolder>() {

    class SettingsViewHolder(val binding: SettingsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(
            SettingsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val newList = settingsItemList[position]
        holder.binding.settingsItemName.apply {
            text = newList.itemName

        }

        holder.binding.root.setOnClickListener {
            listener(position)
        }
        holder.binding.settingsItemIcon.setImageResource(newList.drawableInt)
    }

    override fun getItemCount() = settingsItemList.size
}