package com.cactus.marvelcomics.common.base

import com.cactus.marvelcomics.common.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    inline fun <reified VM : BaseViewModel> appViewModel(): FragmentViewModelDelegate<VM> {
        return FragmentViewModelDelegate(VM::class, this) { this.vmFactory }
    }

}