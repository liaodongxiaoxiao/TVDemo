package com.ldxx.tv.entity

import android.graphics.drawable.Drawable

data class PackageInfoBean(var packageName: String? = null,
                           var appName: String? = null,
                           var versionCode: Int = 0,
                           var versionName: String? = null,
                           var icon: Drawable? = null)