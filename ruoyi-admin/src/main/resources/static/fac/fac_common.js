// 订单状态
var ORDER_STATUS = {
    1: "已付款",
    2: "待付款",
    3: "已取消",
    4: "未取消"
};

// 提现功能：申请记录状态
var CASH_STATUS = {
    "1": "待处理",
    "2": "失败",
    "3": "成功"
};
// 跳转类型
var JUMP_TYPE = {
    "1": "页面",
    "2": "商品",
    "3": "分类"
}
//显示状态
var STATUS_VISIBLE = {
    "1": "显示",
    "2": "隐藏"
}

// option：首页相关统计图
var homepageOption = {
    title: {
        text: 'FAC数据总体一览'
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    toolbox: {
        show: true,
        top: 10,
        right: 10,
        feature: {
            mark: {show: true},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [], // 后端重新附值
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '',
            type: 'bar',
            barWidth: '60%',
            data: [] // 后端重新附值
        }
    ]
};

// 订单统计
var orderOption = {
    title: {
        text: '每日订单金额与数量统计'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c}"
    },
    toolbox: {
        show: true,
        top: 10,
        right: 10,
        feature: {
            mark: {show: true},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    legend: {
        top: 32,
        left: 'center',
        data: ['订单金额', '订单数量']
    },
    calculable: true,
    xAxis: [
        {
            type: 'category',
            data: []
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: "订\n单\n金\n额\n︵\n元\n︶",
            nameLocation: "center",
            nameGap: 35,
            nameRotate: 0,
            nameTextStyle: {
                fontSize: 12,
            },
            //默认以千分位显示，不想用的可以在这加一段
            axisLabel: {   //调整左侧Y轴刻度， 直接按对应数据显示
                show: true,
                showMinLabel: true,
                showMaxLabel: true,
                formatter: function (value) {
                    return value;
                }
            }
        },
        {
            type: 'value',
            name: "订\n单\n个\n数\n︵\n个\n︶",
            nameLocation: "center",
            nameGap: 35,
            nameRotate: 0,
            nameTextStyle: {
                fontSize: 12,
            },
            //默认以千分位显示，不想用的可以在这加一段
            axisLabel: {   //调整左侧Y轴刻度， 直接按对应数据显示
                show: true,
                showMinLabel: true,
                showMaxLabel: true,
                formatter: function (value) {
                    return value;
                }
            }
        }
    ],
    series: [
        {
            name: '订单金额',
            type: 'line',
            smooth: true,
            yAxisIndex: 0,
            data: [],
            itemStyle: {normal: {label: {show: true}}},
        },
        {
            name: '订单数量',
            type: 'line',
            smooth: true,
            yAxisIndex: 1,
            data: [],
            itemStyle: {normal: {label: {show: true}}},
        }
    ]
};

// 新增用户统计
var userOption = {
    title: {
        text: '每日新增用户统计'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    },
    toolbox: {
        show: true,
        top: 10,
        right: 10,
        feature: {
            mark: {show: true},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    legend: {
        data: ['新增用户']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            boundaryGap: false,
            data: []
        }
    ],
    yAxis: {
        type: 'value'
    },
    series: [{
        name: '新增用户',
        data: [],
        type: 'line',
        smooth: true,
        areaStyle: {}
    }]
};

// 初始化商品介绍富文本编辑器组件
var initProdIntroductionEditor = function (editorId, data) {
    CKEDITOR.replace(editorId, {
        toolbar: null,
        toolbarGroups: null,
        removeButtons: null,
        height: 100
    });
    CKEDITOR.config.extraPlugins = 'selectall,notification,notificationaggregator,widgetselection,filetools,lineutils,widget,uploadwidget,uploadimage';
    // 其它配置项
    var uploadUrl = ctx + "ajax/upload/image/batch";
    CKEDITOR.config.filebrowserImageUploadUrl = uploadUrl; // 图片上传路径
    CKEDITOR.config.removeDialogTabs = 'image:advanced;image:Link'; // 移除图片上传页面的'高级','链接'页签
    // CKEDITOR.config.removePlugins = 'elementspath,resize'; // 移除编辑器底部状态栏显示的元素路径和调整编辑器大小的按钮
    CKEDITOR.config.uploadImgSupportedTypes = '/image\\/(jpeg|png|gif|bmp)/';  // 上传图片格式限制
    CKEDITOR.config.image_previewText = "";

    // CKEDITOR.on('instanceReady', function (evt) {
    //     var editor = evt.editor;
    //     editor.setData(data);
    // });
}

//建立一個可存取到該file的url
var getObjectURL = function (file) {
    var url = null;
    // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

// 初始化图片上传组件:商品
var initFileInput = function (id, uploadUrl, maxFilesNum) {
    var control = $('#' + id);
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: uploadUrl, //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        maxFilesNum: maxFilesNum,//上传最大的文件数量
        //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
        uploadAsync: true, //默认异步上传
        showUpload: true, //是否显示上传按钮
        showRemove: true, //显示移除按钮
        showPreview: true, //是否显示预览
        showCaption: false,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式
        //dropZoneEnabled: true,//是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        //maxFileCount: 10, //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount: true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

    }).on('filepreupload', function (event, data, previewId, index) {     //上传中
        var form = data.form, files = data.files, extra = data.extra, response = data.response, reader = data.reader;
        console.log('文件正在上传');
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
        console.log('文件上传成功！' + data.id);
    }).on('fileerror', function (event, data, msg) {  //一个文件上传失败
        console.log('文件上传失败！' + data.id);
    })
}

// 关闭选项卡菜单
function closeTab() {
    var closeTabId = $(this).parents('.menuTab').data('id');
    var currentWidth = $(this).parents('.menuTab').width();

    // 当前元素处于活动状态
    if ($(this).parents('.menuTab').hasClass('active')) {

        // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
        if ($(this).parents('.menuTab').next('.menuTab').size()) {

            var activeId = $(this).parents('.menuTab').next('.menuTab:eq(0)').data('id');
            $(this).parents('.menuTab').next('.menuTab:eq(0)').addClass('active');

            $('.mainContent .RuoYi_iframe').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.RuoYi_iframe').hide();
                    return false;
                }
            });

            var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
            if (marginLeftVal < 0) {
                $('.page-tabs-content').animate({
                        marginLeft: (marginLeftVal + currentWidth) + 'px'
                    },
                    "fast");
            }

            //  移除当前选项卡
            $(this).parents('.menuTab').remove();

            // 移除tab对应的内容区
            $('.mainContent .RuoYi_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }

        // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
        if ($(this).parents('.menuTab').prev('.menuTab').size()) {
            var activeId = $(this).parents('.menuTab').prev('.menuTab:last').data('id');
            $(this).parents('.menuTab').prev('.menuTab:last').addClass('active');
            $('.mainContent .RuoYi_iframe').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.RuoYi_iframe').hide();
                    return false;
                }
            });

            //  移除当前选项卡
            $(this).parents('.menuTab').remove();

            // 移除tab对应的内容区
            $('.mainContent .RuoYi_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }
    }
    // 当前元素不处于活动状态
    else {
        //  移除当前选项卡
        $(this).parents('.menuTab').remove();

        // 移除相应tab对应的内容区
        $('.mainContent .RuoYi_iframe').each(function () {
            if ($(this).data('id') == closeTabId) {
                $(this).remove();
                return false;
            }
        });
        scrollToTab($('.menuTab.active'));
    }
    return false;
}

//滚动到指定选项卡
function scrollToTab(element) {
    var marginLeftVal = calSumWidth($(element).prevAll()),
        marginRightVal = calSumWidth($(element).nextAll());
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content').animate({
            marginLeft: 0 - scrollVal + 'px'
        },
        "fast");
}

//计算元素集合的总宽度
function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function () {
        width += $(this).outerWidth(true);
    });
    return width;
}






