package com.lukelorusso.toomuchofscrolling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private val base10List = listOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15"
        )
        private val binaryList = listOf(
            "0",
            "1",
            "10",
            "11",
            "100",
            "101",
            "110",
            "111",
            "1000",
            "1001",
            "1010",
            "1011",
            "1100",
            "1101",
            "1110",
            "1111"
        )
        private val hexadecimalList = listOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "A",
            "B",
            "C",
            "D",
            "E",
            "F"
        )
    }

    // Properties
    private val base10Adapter = Base10Adapter()
    private val binaryAdapter = BinaryAdapter()
    private val hexadecimalAdapter = HexadecimalAdapter()

    private var base10Listener: RecyclerView.OnScrollListener? = null
    private var binaryListener: RecyclerView.OnScrollListener? = null
    private var hexadecimalListener: RecyclerView.OnScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeActivity(savedInstanceState)
    }

    private fun initializeActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            initView()
        }
    }

    private fun initView() {
        base10RecyclerView.adapter = base10Adapter.apply { data = base10List }
        binaryRecyclerView.adapter = binaryAdapter.apply { data = binaryList }
        hexadecimalRecyclerView.adapter = hexadecimalAdapter.apply { data = hexadecimalList }

        bind()
        activate?.setOnClickListener { bind() }
        deactivate?.setOnClickListener { unbind() }
    }

    private fun bind() {
        activate?.isEnabled = false
        deactivate?.isEnabled = false

        base10Listener = base10RecyclerView
            .bindScrollTo(binaryRecyclerView, hexadecimalRecyclerView)
        binaryListener = binaryRecyclerView

            .bindScrollTo(base10RecyclerView, hexadecimalRecyclerView)

        hexadecimalListener = hexadecimalRecyclerView
            .bindScrollTo(base10RecyclerView, binaryRecyclerView)

        activate?.isEnabled = true
        deactivate?.isEnabled = true
    }

    private fun unbind() {
        activate?.isEnabled = false
        deactivate?.isEnabled = false

        base10Listener?.also { listener ->
            base10RecyclerView.removeOnScrollListener(listener)
        }

        binaryListener?.also { listener ->
            binaryRecyclerView.removeOnScrollListener(listener)
        }

        hexadecimalListener?.also { listener ->
            hexadecimalRecyclerView.removeOnScrollListener(listener)
        }

        activate?.isEnabled = true
        deactivate?.isEnabled = true
    }

}
