package com.ssafy.withssafy.src.binding

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.BindingAdapter
import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.src.dto.Message
import com.ssafy.withssafy.src.dto.Notification
import com.ssafy.withssafy.src.dto.Recruit
import com.ssafy.withssafy.src.dto.User
import com.ssafy.withssafy.src.dto.notice.Notice
import com.ssafy.withssafy.src.dto.report.Report
import com.ssafy.withssafy.src.dto.study.Study
import com.ssafy.withssafy.src.main.board.*
import com.ssafy.withssafy.src.main.home.EmployInfoAdapter
import com.ssafy.withssafy.src.main.home.ReportAdapter
import com.ssafy.withssafy.src.main.home.RequestAdapter
import com.ssafy.withssafy.src.main.notification.MessageDetailAdapter
import com.ssafy.withssafy.src.main.notification.MessageGroupAdapter
import com.ssafy.withssafy.src.main.notification.NotiListAdapter
import com.ssafy.withssafy.src.main.team.TeamAdapter
import com.ssafy.withssafy.util.CommonUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("studyListData")
fun bindingStudyList(recyclerView: RecyclerView, data:List<Study>?){
    var adapter = recyclerView.adapter as TeamAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as TeamAdapter
    }
    adapter.list = data as MutableList<Study>
    adapter.notifyDataSetChanged()
}
@SuppressLint("SetTextI18n")
@BindingAdapter("studyLimitText")
fun bindStudyLimitText(textView: TextView, study:Study){
    if(study.studyMembers?.size == null){
        textView.text = "0/${study.sbLimit}"
    }
    textView.text = "${study.studyMembers?.size}/${study.sbLimit}"

}

@BindingAdapter("noticeLimitText")
fun bindNoticeLimitText(textView: TextView, str:String){
    if(str!!.length > 12){
        textView.text = "${str.substring(0, 12)}..."
    } else {
        textView.text = str
    }
}

@BindingAdapter("studyImageUrl")
fun bindImageUrl(imageView: ImageView, url:String?){
    Glide.with(imageView.context)
        .load("${ApplicationClass.IMGS_URL}${url}")
        .into(imageView)
}
@BindingAdapter("jobImageUrl")
fun bindJobImageUrl(imageView: ImageView, url:String?){
    if(url == null || url == ""){
        Glide.with(imageView.context)
            .load(R.drawable.images)
            .into(imageView)
    }else{
        Glide.with(imageView.context)
            .load("${ApplicationClass.IMGS_URL}${url}")
            .into(imageView)
    }
}

@BindingAdapter("textViewPeople")
fun textViewConvertPeople(textView: TextView, size:Int){
    textView.text = "${size}명"
}
@BindingAdapter("textViewWeek")
fun textViewConvertWeek(textView: TextView, week:Int){
    if(week == 0){
        textView.text = "시간표가 없습니다."
    }else{
        textView.text = "${week}주차"
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("converMilliSecondToString")
fun textViewConvertDate(textView: TextView, time:String){
//    var formatter = SimpleDateFormat("yyyy-MM-dd HH:ss")
//    var date = formatter.format(time.toLong())
    textView.text = time.toString()
}


var isClicked = false
//데이터 바인딩에서 사용하기 위한 fun
@BindingAdapter("app:onThrottleFirstClick", "app:onThrottleInterval", requireAll = false)
fun onThrottleFirstClick(view: View, onClickListener: View.OnClickListener, isWithoutInterval: Boolean = false) {
    view.setOnClickListener { v ->
        if (isClicked.not()) {
            isClicked = true
            v?.run {
                if (isWithoutInterval) {
                    isClicked = false
                    onClickListener.onClick(v)
                } else {
                    postDelayed({
                        isClicked = false
                    }, 1000L)
                    onClickListener.onClick(v)
                }
            }
        }
    }
}

@BindingAdapter("unixToDate")
fun bindBoardTime(textView: TextView, time: String) {
    textView.text = CommonUtils.unixTimeToDateFormatInBoard(time.toLong())
}

@BindingAdapter("decodingImg")
fun bindBitmapImg(imageView: ImageView, imgString: String) {
    Glide.with(imageView.context)
        .load(imgString)
        .into(imageView)
}

@BindingAdapter("ellipsisContent")
fun bindEllipsisContent(textView: TextView, content: String) {
    if(content.length > 45) {
        textView.text = "${content.substring(0, 40)} •••"
    } else {
        textView.text = content
    }
}

@BindingAdapter("messageTextView")
fun bindMsgTextView(textView: TextView, msg:Message){
    if(msg.u_fromId == ApplicationClass.sharedPreferencesUtil.getUser().id){
        textView.text = "익명${msg.u_toId}과의 대화"
    }else{
        textView.text = "익명${msg.u_fromId}과의 대화"
    }

}

@BindingAdapter("messageGroupDataList")
fun bindRecyclerViewMessageGroup(recyclerView: RecyclerView, data:List<Message>?){
    var adapter = recyclerView.adapter as MessageGroupAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as MessageGroupAdapter
    }
    adapter.list = data as MutableList<Message>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("messageTalkDataList")
fun bindRecyclerViewMessageTalk(recyclerView: RecyclerView, data:List<Message>?){
    var adapter = recyclerView.adapter as MessageDetailAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as MessageDetailAdapter
    }
    adapter.list = data as MutableList<Message>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("recruitListData")
fun bindingRecruitList(recyclerView: RecyclerView, data:List<Recruit>?){
    var adapter = recyclerView.adapter as JobAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as JobAdapter
    }
    adapter.list = data as MutableList<Recruit>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("recruitRecentListData")
fun bindingRecruitRecentList(recyclerView: RecyclerView, data:List<Recruit>?){
    var adapter = recyclerView.adapter as EmployInfoAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as EmployInfoAdapter
    }
    adapter.list = data as MutableList<Recruit>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("requestListData")
fun bindingRequestList(recyclerView: RecyclerView, data:List<User>?){
    var adapter = recyclerView.adapter as RequestAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as RequestAdapter
    }
    adapter.list = data as MutableList<User>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("reportListData")
fun bindingReportList(recyclerView: RecyclerView, data:List<Report>?){
    var adapter = recyclerView.adapter as ReportAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as ReportAdapter
    }
    adapter.list = data as MutableList<Report>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("dateConvert")
fun bindingTvDataConvert(textView: TextView,date:String){
    var formatter = SimpleDateFormat("yyyy-MM-dd")
    var dates = date.substring(0,11)
//    var dates = formatter.parse(date)
    textView.text = dates.toString()
}
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("weekConvert")
fun bindingTvWeekConvert(textView: TextView, date:String){
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss")
    var dates = LocalDate.parse(date, formatter)
    var week = dates.dayOfWeek
    var numWeek = week.value
    textView.text = CommonUtils.convertWeek(numWeek)
}
@BindingAdapter("findStartDate")
fun bindingTvStartTime(textView: TextView, date:String){
    var times = date.substring(date.length-5,date.length)
    Log.d("BindingAdapter", "bindingTvStartTime: $times")
    var hour = times.substring(0,2)
    Log.d("BindingAdapter", "bindingTvStartTime: $hour")
    var minutes = times.substring(3,5)
    Log.d("BindingAdapter", "bindingTvStartTime: $minutes")
    if(hour.toInt() > 12){
        textView.text = "PM ${Math.abs(hour.toInt()-12)}:${minutes}"
    }else{
        textView.text = "AM ${hour}:${minutes}"
    }
}
@BindingAdapter("boardNoticeAllListData")
fun bindingBoardNoticeAllList(recyclerView: RecyclerView, data:List<Notice>?){
    var adapter = recyclerView.adapter as BoardNoticeAllAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as BoardNoticeAllAdapter
    }
    if(data == null) {
        adapter.list = mutableListOf<Notice>()
    } else {
        adapter.list = data as MutableList<Notice>
    }
    adapter.notifyDataSetChanged()
}

@BindingAdapter("boardClassNoticeListData")
fun bindingClassNoticeList(recyclerView: RecyclerView, data:List<Notice>?){
    var adapter = recyclerView.adapter as BoardClassNoticeAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as BoardClassNoticeAdapter
    }
    if(data == null) {
        adapter.list = mutableListOf<Notice>()
    } else {
        adapter.list = data as MutableList<Notice>
    }
    adapter.notifyDataSetChanged()
}

@BindingAdapter("boardImg")
fun bindBoardImage(imageView: ImageView, photoPath: String?) {
    if(photoPath.toString().isEmpty() || photoPath == null) {
        imageView.visibility = View.GONE
    } else {
        imageView.visibility = View.VISIBLE

        Glide.with(imageView.context)
            .load("${ApplicationClass.IMGS_URL}${photoPath}")
            .into(imageView)
    }
}

@BindingAdapter("boardNoticeFilterListData")
fun bindingBoardNoticeFilterList(recyclerView: RecyclerView, data:List<Notice>?){
    var adapter = recyclerView.adapter as BoardNoticeListAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as BoardNoticeListAdapter
    }
    adapter.list = data as MutableList<Notice>
    adapter.notifyDataSetChanged()
}

@BindingAdapter("wirteDateConvert")
fun bindingWriteDateConvert(textView: TextView, date:String){
    var date = date.split("T")
    textView.text = date.get(0)
}

@BindingAdapter("noticeImageUrl")
fun bindNoticeImageUrl(imageView: ImageView, url:String?){
    if(url == "") {
        imageView.visibility = View.GONE
    } else {
        imageView.visibility = View.VISIBLE
        Glide.with(imageView.context)
            .load("${ApplicationClass.IMGS_URL}${url}")
            .into(imageView)
    }
}

@BindingAdapter("wirteDateClassNoticeConvert")
fun bindingWriteDateClassNoticeConvert(textView: TextView, date:String){
    var date = date.split("T")
    var time = date.get(1).split(":")
    textView.text = "${date.get(0)} ${time.get(0)}:${time.get(1)}"
}

@BindingAdapter("classNoticeListData")
fun bindingCNoticeList(recyclerView: RecyclerView, data:List<Notice>?){
    var adapter = recyclerView.adapter as BoardClassNoticeListAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as BoardClassNoticeListAdapter
    }
    adapter.list = data as MutableList<Notice>
    adapter.notifyDataSetChanged()
}
@BindingAdapter("authConvert")
fun bindingAuthConvert(textView: TextView, auth:Int){
    if(auth == 0) {
        textView.text = "(관리자)"
    } else if(auth == 1) {
        textView.text = "(컨설턴트)"
    } else if(auth == 2) {
        textView.text = "(프로)"
    }

}


@BindingAdapter("timeConvert")
fun bindTimeConvert(textView: TextView, date:String){
    textView.text = date.substring(date.length-8,date.length-3)
}

@BindingAdapter("notiListData")
fun bindingNotiList(recyclerView: RecyclerView, data:List<Notification>?){
    var adapter = recyclerView.adapter as NotiListAdapter
    if(recyclerView.adapter == null){
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter
    }else{
        adapter = recyclerView.adapter as NotiListAdapter
    }
    adapter.list = data as MutableList<Notification>
    adapter.notifyDataSetChanged()
}
@BindingAdapter("textViewJob")
fun bindTextViewJob(textView: TextView, job:String){
    if(job.length > 10) {
        textView.text = "${job.substring(0, 10)}···"
    }else{
        textView.text = job
    }
}