# 自定义View练习

学习 Android 自定义 View 的练习，涉及常用绘制及动画API，方便以后查看及使用。
## 目录
- [ProgressMarkView](###ProgressMarkView)
- [DashBoardView](###DashBoardView)
- [PieChartView](###PieChartView)
- [RoundImageView](###RoundImageView)
- [CyclePercentView](###CyclePercentView)
- [ImageTextView](###ImageTextView)
- [AnimateCameraView](###AnimateCameraView)
- [MaterialEditTextView](###MaterialEditTextView)

## ProgressMarkView

ProgressMarkView 是一个加载的转圈动画，包含加载成功及失败的动画效果，动画效果连贯不突兀。效果如下:

## DashBoard

DashBoard 是一个仪表盘视图，可以自定义刻度总长及刻度个数。

## PieChartView

PieChartView 为扇形分区图，每部分的颜色不同，指定区域可突出显示。

## RoundImageView

RoundImageView 是通过裁切形成圆形的图片。

## CyclePercentView

百分比加载动画，支持中心文字及圆环颜色定义。










## 学习时的注意事项

### 自定义属性时的重名问题
在开发中使用两两组自定义属性均含有`<attr name="board_width" format="dimension" />`编译时报出如下错误：

```
/CustomView/app/src/main/res/values/attrs.xml: Error: Found item Attr/board_width more than one time
```


```
<attr name="board_width" format="dimension" />

    <declare-styleable name="RoundImageAttrs">
        <attr name="board_width"  />
        <attr name="board_color" format="color" />
        <attr name="avatar_res" format="integer" />
    </declare-styleable>

    <declare-styleable name="CyclePercentAttrs">
        <attr name="board_width"  />
        <attr name="board_color" format="color" />
        <attr name="board_bg_color" format="color" />
    </declare-styleable>


```

解决方法时将两组属性提出来，定义一次，使用的时候声明即可。

### 画文字时的 style 
画文字时 Paint 的 Style 应当是 Paint.Style.FILL