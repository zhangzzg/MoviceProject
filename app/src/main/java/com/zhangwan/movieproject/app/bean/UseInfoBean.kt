package com.zhangwan.movieproject.app.bean

import java.io.Serializable

/**
 * Created by zzg on 2018/4/16.
 */

data class UseInfoBean(var unique:String?,var nickname:String?,var face:String?,var grade:String?,
                      var wnickname:String?,var tel:String?,var createtime:String?,var viptime:String? ) :Serializable{
}