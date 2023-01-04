package com.example.expensestracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense (
    val name:String ,
    val type:String,
    val price:Double,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)