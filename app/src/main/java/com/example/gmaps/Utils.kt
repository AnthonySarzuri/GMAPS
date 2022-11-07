package com.example.gmaps

import androidx.viewbinding.ViewBinding

object Utils {
    var binding: ViewBinding? = null

    //densidad de pixeles en pantalla
    fun dp(pixeles: Int): Int{
        if (binding==null) return  0//diseno de la pantalla
        val scale = binding!!.root.resources.displayMetrics.density
        return (scale*pixeles + 0.5f).toInt()
        //obtiene la densidad de pantalla
    }
}