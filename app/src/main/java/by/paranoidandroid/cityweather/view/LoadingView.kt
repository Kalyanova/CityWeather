package by.paranoidandroid.cityweather.view

interface LoadingView {

    fun onStartLoading()

    /**
     * This method is invoked from background thread.
     */
    fun onStopLoading()
}