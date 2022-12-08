package com.example.allergicdetectorapplication.UserTools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.example.allergicdetectorapplication.databinding.ItemMonthBinding
import kotlin.random.Random

class ViewPagerAdapter : PagerAdapter() {
    var items = emptyList<String>()

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = ItemMonthBinding.inflate(LayoutInflater.from(container.context), container, false)
        view.recycler.layoutManager = LinearLayoutManager(container.context)
        val adapter = NestedRecyclerAdapter()
        view.recycler.adapter = adapter
        adapter.setNewItems(generateListOfPylenie())
        view.monthName.text = items[position]
        container.addView(view.root)
        return view.root
    }

    private fun generateListOfPylenie(): List<Pylenie> {

        return List(5) { Pylenie(Random.nextBoolean(), Random.nextBoolean(), Random.nextBoolean()) }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setNewItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }


}