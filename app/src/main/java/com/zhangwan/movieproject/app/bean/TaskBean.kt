package com.zhangwan.movieproject.app.bean

import java.io.Serializable

/**
 * Created by zzg on 2018/4/16.
 */
data class TaskBean(var fdate:Int?,var count:Int?,var fans:Fans?,var rask:MutableList<Task>?) :Serializable {
//  fdate	总共获得vip天数
//  count	优惠券数量

  data class Fans(var nickname:String?,var face:String?,var viptime:String?) :Serializable

  data class Task(var id:String?,var name:String?,var desc:String?,var finish:String?,var url:String?) :Serializable //finish 是否完成，1完成，0未完成

}