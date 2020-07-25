package com.lukelorusso.toomuchofscrolling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
            activate?.setOnClickListener { bind() }
            deactivate?.setOnClickListener { unbind() }
        }
    }

    private fun initView() {
        base10RecyclerView.adapter = base10Adapter.apply {
            data = listOf(
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
        }

        binaryRecyclerView.adapter = binaryAdapter.apply {
            data = listOf(
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
        }

        hexadecimalRecyclerView.adapter = hexadecimalAdapter.apply {
            data = listOf(
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
        bind()
    }

    private fun bind() {
        activate?.isEnabled = false
        deactivate?.isEnabled = false

        base10RecyclerView.apply {
            base10Listener = bindScrollTo(binaryRecyclerView, hexadecimalRecyclerView)
        }

        binaryRecyclerView.apply {
            binaryListener = bindScrollTo(base10RecyclerView, hexadecimalRecyclerView)
        }

        hexadecimalRecyclerView.apply {
            hexadecimalListener = bindScrollTo(base10RecyclerView, binaryRecyclerView)
        }

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
