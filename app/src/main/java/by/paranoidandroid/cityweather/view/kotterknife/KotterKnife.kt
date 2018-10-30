package by.paranoidandroid.cityweather.view.kotterknife

import android.app.Activity
import android.app.Dialog
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Find view by [id] on first access and save it for future usage.
 * Throw exception if view was not found.
 */
fun <V : View> View.bindView(@IdRes id: Int): ReadOnlyProperty<View, V> {
    return required(id) { this@bindView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 * Throw exception if view was not found.
 */
fun <V : View> Activity.bindView(@IdRes id: Int): ReadOnlyProperty<Activity, V> {
    return required(id) { this@bindView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 * Throw exception if view was not found.
 */
fun <V : View> Dialog.bindView(@IdRes id: Int): ReadOnlyProperty<Dialog, V> {
    return required(id) { this@bindView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 * Throw exception if view was not found.
 */
fun <V : View> Fragment.bindView(@IdRes id: Int): ReadOnlyProperty<Fragment, V> {
    return required(id) { this@bindView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 * Throw exception if view was not found.
 */
fun <V : View> ViewHolder.bindView(@IdRes id: Int): ReadOnlyProperty<ViewHolder, V> {
    return required(id) { this@bindView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 */
fun <V : View> View.bindOptionalView(@IdRes id: Int): ReadOnlyProperty<View, V?> {
    return optional(id) { this@bindOptionalView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 */
fun <V : View> Activity.bindOptionalView(@IdRes id: Int): ReadOnlyProperty<Activity, V?> {
    return optional(id) { this@bindOptionalView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 */
fun <V : View> Dialog.bindOptionalView(@IdRes id: Int): ReadOnlyProperty<Dialog, V?> {
    return optional(id) { this@bindOptionalView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 */
fun <V : View> Fragment.bindOptionalView(@IdRes id: Int): ReadOnlyProperty<Fragment, V?> {
    return optional(id) { this@bindOptionalView.findView(id) }
}

/**
 * Find view by [id] on first access and save it for future usage.
 */
fun <V : View> ViewHolder.bindOptionalView(@IdRes id: Int): ReadOnlyProperty<ViewHolder, V?> {
    return optional(id) { this@bindOptionalView.findView(id) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Throw exception if any of views was not found.
 */
fun <V : View> View.bindViews(vararg ids: Int): ReadOnlyProperty<View, List<V>> {
    return required(ids) { this@bindViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Throw exception if any of views was not found.
 */
fun <V : View> Activity.bindViews(vararg ids: Int): ReadOnlyProperty<Activity, List<V>> {
    return required(ids) { this@bindViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Throw exception if any of views was not found.
 */
fun <V : View> Dialog.bindViews(vararg ids: Int): ReadOnlyProperty<Dialog, List<V>> {
    return required(ids) { this@bindViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Throw exception if any of views was not found.
 */
fun <V : View> Fragment.bindViews(vararg ids: Int): ReadOnlyProperty<Fragment, List<V>> {
    return required(ids) { this@bindViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Throw exception if any of views was not found.
 */
fun <V : View> ViewHolder.bindViews(vararg ids: Int): ReadOnlyProperty<ViewHolder, List<V>> {
    return required(ids) { this@bindViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Views that was not found will be skipped in result list.
 */
fun <V : View> View.bindOptionalViews(vararg ids: Int): ReadOnlyProperty<View, List<V>> {
    return optional(ids) { this@bindOptionalViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Views that was not found will be skipped in result list.
 */
fun <V : View> Activity.bindOptionalViews(vararg ids: Int): ReadOnlyProperty<Activity, List<V>> {
    return optional(ids) { this@bindOptionalViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Views that was not found will be skipped in result list.
 */
fun <V : View> Dialog.bindOptionalViews(vararg ids: Int): ReadOnlyProperty<Dialog, List<V>> {
    return optional(ids) { this@bindOptionalViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Views that was not found will be skipped in result list.
 */
fun <V : View> Fragment.bindOptionalViews(vararg ids: Int): ReadOnlyProperty<Fragment, List<V>> {
    return optional(ids) { this@bindOptionalViews.findView(it) }
}

/**
 * Find views by [ids] on first access and save they for future usage.
 * Views that was not found will be skipped in result list.
 */
fun <V : View> ViewHolder.bindOptionalViews(vararg ids: Int): ReadOnlyProperty<ViewHolder, List<V?>> {
    return optional(ids) { this@bindOptionalViews.findView(it) }
}

private fun <V : View> View.findView(@IdRes viewId: Int): V? = findViewById(viewId)

private fun <V : View> Activity.findView(@IdRes viewId: Int): V? = findViewById(viewId)

private fun <V : View> Dialog.findView(@IdRes viewId: Int): V? = findViewById(viewId)

private fun <V : View> Fragment.findView(@IdRes viewId: Int): V? = view?.findViewById(viewId)

private fun <V : View> ViewHolder.findView(@IdRes viewId: Int): V? = itemView.findViewById(viewId)

private fun viewNotFound(id: Int, desc: KProperty<*>): Nothing {
    throw IllegalStateException("View ID $id for '${desc.name}' not found.")
}

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?): ReadOnlyProperty<T, V> {
    return Lazy { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }
}

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?): ReadOnlyProperty<T, V?> {
    return Lazy { t: T, _ -> t.finder(id) as V? }
}

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(
    ids: IntArray,
    finder: T.(Int) -> View?
): ReadOnlyProperty<T, List<V>> {
    return Lazy { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }
}

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(
    ids: IntArray,
    finder: T.(Int) -> View?
): ReadOnlyProperty<T, List<V>> {
    return Lazy { t: T, _ -> ids.map { t.finder(it) as V? }.filterNotNull() }
}

// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it
private class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY

    private var value: Any? = EMPTY

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }
}