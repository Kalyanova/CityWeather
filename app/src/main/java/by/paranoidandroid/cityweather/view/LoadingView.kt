package by.paranoidandroid.cityweather.view

interface LoadingView {

    fun onStartLoading()

    /**
     * This method is invoked from background thread.
     */
    fun onStopLoading()

    /**
     * This method is invoked from background thread.
     */
    fun onLoadingError(errorMsg: String)

    /**
     * This method is invoked from background thread.
     */
    fun onLoadingSuccess()
}