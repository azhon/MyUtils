## 个人整理的一个工具类的集合，包括一些自定义的View
### ● TimeClock.java——>自定义时钟View——>[使用方法](http://blog.csdn.net/a_zhon/article/details/53027501)——>效果图

<img src="http://img.blog.csdn.net/20161105231805323" />

### ● LogUtil.java——>Log日志工具类
### ● ScratchCard.java——>刮刮卡效果——>效果图

<img src="http://img.blog.csdn.net/20161104233422147" width="337px" height="460px" />

### ● LoadingView.java——>菊花进度——>[使用方法](http://blog.csdn.net/a_zhon/article/details/53143034)——>效果图
<img src="http://i.imgur.com/PGQ8CIt.gif" />

### ● WordsNavigation.java——>联系人列表字母索引——>[使用方法](http://blog.csdn.net/a_zhon/article/details/53214849)——>效果图

<img width="369px" height="660px" src="http://img.blog.csdn.net/20161118122207199" /> 

### ● BaseRecyclerAdapter.java——>RecyclerView适配器——>[使用方法](http://blog.csdn.net/a_zhon/article/details/66971369)

```
public class MyAdapter extends BaseRecyclerAdapter<DataBean, BaseRecyclerAdapter.BaseViewHolder> {

    public MyAdapter(int layoutResId, List<DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void bindTheData(BaseRecyclerAdapter.BaseViewHolder holder, DataBean data, int position) {
        holder.setText(android.R.id.text1, data.getName());
    }
}

```
---
```
MyAdapter adapter = new MyAdapter(android.R.layout.simple_list_item_1, list);
//添加头布局
adapter.addHeadView(R.layout.head_view);
//添加尾布局
adapter.addFootView(R.layout.foot_view);
recyclerView.setAdapter(adapter);
adapter.setClickListener(new BaseRecyclerAdapter.onItemClickListener() {
    @Override
    public void onItemClick(int position, View v) {
        Toast.makeText(MainActivity.this, "点击 position = " + position, Toast.LENGTH_SHORT).show();
    }
});
```
### ● ScreenSizeUtils.java——>获取手机屏幕大小
### ● SharePreUtils.java——>SharedPreferences数据存储工具类
### ● AmountEditText.java——>只允许输入两位小数的输入框
```
//添加文本改变监听
editeText.setListener(new AmountEditText.OnTextChangeListener() {
    @Override
    public void onTextChanged(String s) {
		//回调
    }
});
```
### ● TimeUtils.java——>时间戳转化为年月日格式
### ● WaveView.java——>水波球——>[使用方法](http://blog.csdn.net/a_zhon/article/details/77842615)——>效果图
<img src="http://img.blog.csdn.net/20170904215012826?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYV96aG9u/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" />

### ● ActionBarToast.java——>顶部消息弹框——>[使用方法](http://blog.csdn.net/a_zhon/article/details/78988653)——>效果图
<img src="http://img.blog.csdn.net/20180106002901033?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYV96aG9u/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" />

```
ActionBarToast barToast = new ActionBarToast(this);
barToast.showToast(ActionBarToast.LENGTH_SHORT);
//barTost.cancel();
```

### ● MaskView.java——>观看视频开灯/关灯效果——>[使用方法](http://blog.csdn.net/a_zhon/article/details/78988653)——>效果图
<img src="http://img.blog.csdn.net/20180106002852653?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYV96aG9u/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" />

```
MaskView maskView = new MaskView(this);
maskView.attachView(view);
//maskView.removeMask();
```