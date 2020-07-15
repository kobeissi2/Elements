package com.kobeissidev.elements

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var selectedElementPosition: Int = -1
        private set
    var selectedItemPosition: Int = -1
        private set

    fun onElementSelected(position: Int) {
        selectedElementPosition = position
    }

    fun onItemSelected(position: Int) {
        selectedItemPosition = position
    }
}