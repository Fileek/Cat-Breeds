package com.storage.catbreeds.adapter

import com.storage.catbreeds.entity.Cat

interface CatListener {

    fun add(cat: Cat)

    fun edit(cat: Cat)

    fun delete(cat: Cat)
}
