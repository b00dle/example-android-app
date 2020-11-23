package com.example.exampleapp.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(lifecycleOwner, Observer { data ->
        if (data == null) return@Observer
        observer(data)
    })
}

fun Router.push(controller: Controller) {
    pushController(RouterTransaction.with(controller))
}

fun Router.pushAndFade(controller: Controller) {
    pushController(fadeTransaction(controller))
}

fun Router.pushAndKeepBackstack(transaction: RouterTransaction) {
    setBackstack(backstack.dropLast(1).toMutableList().also {
        it.add(transaction)
    }, transaction.pushChangeHandler())
}

fun fadeTransaction(controller: Controller): RouterTransaction {
    return RouterTransaction
        .with(controller)
        .pushChangeHandler(FadeChangeHandler())
        .popChangeHandler(FadeChangeHandler())
}

fun Router.pushAndSlideHorizontal(controller: Controller) {
    pushController(slideHorizontalTransaction(controller))
}

fun Router.pushAndSlideVertical(controller: Controller) {
    pushController(slideVerticalTransaction(controller))
}

fun slideHorizontalTransaction(controller: Controller): RouterTransaction {
    return RouterTransaction
        .with(controller)
        .pushChangeHandler(HorizontalChangeHandler())
        .popChangeHandler(HorizontalChangeHandler())
}

fun slideVerticalTransaction(controller: Controller): RouterTransaction {
    return RouterTransaction
        .with(controller)
        .pushChangeHandler(VerticalChangeHandler())
        .popChangeHandler(VerticalChangeHandler())
}