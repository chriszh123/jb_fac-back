var TIP_DEALING = "正在处理中，请稍后...";
//各页面对应的data_id
var DATA_ID = {
    "PRODUCT": '/fac/product'
};

// 各对象预置数量
var GENDER = {
    "1": "男",
    "2": "女"
};

// 用户留言状态
var LEAVE_STATUS = {
    false: "未回复",
    true: "已回复"
};

// 各对象预置数量
var OBJECT_COUNT = {
    "PRODUCT_IMG": 5,
    "PRODUCT_IMG_CATEGORY": 1
};

// 订单状态
var ORDER_STATUS = {
    0: "待付款",
    1: "待核销",
    2: "待评价",
    3: "已完成",
    4: "已取消",
    5: "商家待核销"
};

// 商品核销状态
var WRITE_OFF_STATUS = {
    1: "已核销",
    2: "待核销",
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

// 积分类型
var POINT_TYPE = {
    0: "签到(积分)",
    1: "购物反赠(积分)",
    2: "购物消费(积分)",
    3: "购物消费(金额)",
    4: "分享赠送积分",
    5: "分享赠送奖金",
    6: "已提现奖金"
};

// 商品砍价活动状态
var STATUS_PRODUCT_KANJIA = {
    "1": "进行中",
    "2": "已完成",
    "3":"未完成(已过期)"
};

// fileInput上传文件支持扩展名
var FILEINPUTEXTENDTION = ["jpg", "jpeg", "gif", "bmp", "png"];

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

// 初始化图片上传组件:商品
var initFileInput = function (id, uploadUrl, maxFilesNum) {
    var control = $('#' + id);
    control.fileinput({
        language: 'zh', //设置语言
        browseLabel: '选择图片',
        uploadUrl: uploadUrl, //上传的地址
        allowedFileExtensions: FILEINPUTEXTENDTION,
        maxFilesNum: maxFilesNum,//上传最大的文件数量
        //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
        uploadAsync: false, //默认异步上传
        showUpload: true, //是否显示上传按钮，输入框后面的按钮
        showRemove: true, //显示移除按钮，输入框后面的按钮
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
        layoutTemplates: {
            actionDelete: '',
            actionUpload: ''
        }
    }).on('filepreupload', function (event, data, previewId, index) {     //上传中
        var form = data.form, files = data.files, extra = data.extra, response = data.response, reader = data.reader;
        console.log('文件正在上传');
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
        // 异步上传图片时，用fileuploaded事件处理回调
        console.log('fileuploaded：文件上传成功！index = ' + index);
        if (index == 0) {
            // 只要上传的索引又是从一个0开始，证明用户又做了新一次的上传，以最新这次上传的图片数据为准
            $('#picture').val("");
            $('#imgPath').val("");
            console.log('文件上传成功！picture ,imgPath 值清空了');
        }
        if (data && data.response && data.response.code && data.response.code == "0") {
            var lastPicture = $('#picture').val();
            var lastImgPath = $('#imgPath').val();
            lastPicture = lastPicture + "," + data.response.imgPath;  // 这里取完整的图片路径
            lastImgPath = lastImgPath + "," + data.response.imgPath;
            $('#picture').val(lastPicture);
            $('#imgPath').val(lastImgPath);
        } else {
            $('#picture').val("");
            $('#imgPath').val("");
        }
    }).on("filebatchuploadsuccess", function (event, data, previewId, index) {    //一个文件上传成功
        // 同步上传图片时，用filebatchuploadsuccess事件处理回调
        console.log('filebatchuploadsuccess：文件上传成功！index = ' + index);
        if (index == 0) {
            // 只要上传的索引又是从一个0开始，证明用户又做了新一次的上传，以最新这次上传的图片数据为准
            $('#picture').val("");
            $('#imgPath').val("");
            console.log('文件上传成功！picture ,imgPath 值清空了');
        }
        if (data && data.response && data.response.code && data.response.code == "0") {
            var lastPicture = $('#picture').val();
            var lastImgPath = $('#imgPath').val();
            lastPicture = lastPicture + "," + data.response.imgPath;  // 这里取完整的图片路径;
            lastImgPath = lastImgPath + "," + data.response.imgPath;
            $('#picture').val(lastPicture);
            $('#imgPath').val(lastImgPath);
        } else {
            $('#picture').val("");
            $('#imgPath').val("");
        }
    }).on('fileerror', function (event, data, msg) {  //一个文件上传失败
        console.log('文件上传失败！' + data.id);
    });
}

// 关闭选项卡菜单:从iFrame内部调用
var closeCurrentTab = function (tip) {
    if (tip && tip == "1") {
        $.modal.confirm("确定要关闭当前页面吗？", function () {
            parent.window.$(".tabCloseCurrent").click();
        });
    } else {
        parent.window.$(".tabCloseCurrent").click();
    }
}

// 刷新指定iframe页面数据:从iFrame内部调用
var refreshParentIFramePage = function (parentDataId) {
    var target = parent.window.$('.RuoYi_iframe[data-id="' + parentDataId + '"]');
    var url = parentDataId;
    target.attr('src', url).ready();
}

// 初始化fileinput组件图片数据
// 图片的地址 path:
//      [
//         "http://lorempixel.com/800/460/nature/1",
//         "http://lorempixel.com/800/460/nature/2",
//      ]
// con 参数外层是数组形式，里面则为对象
//     [
//     {caption: "People-1.jpg", size: 576237, width: "120px", url: "/site/file-delete", key: 1},
//     ]
var initFileInputWithImgData = function (id, uploadUrl, maxFilesNum, imgPaths, cfg) {
    var control = $('#' + id);
    control.fileinput({
        language: 'zh', //设置语言
        browseLabel: '选择图片',
        uploadUrl: uploadUrl, //上传到后台处理的方法
        uploadAsync: false, //设置同步，异步 （同步）
        showUpload: true, //是否显示上传按钮，输入框后面的按钮
        showRemove: true, //显示移除按钮，输入框后面的按钮
        showPreview: true, //是否显示预览
        overwriteInitial: false, //不覆盖已存在的图片
        maxFilesNum: maxFilesNum,//上传最大的文件数量
        removeFromPreviewOnError: true, //当选择的文件不符合规则时，例如不是指定后缀文件、大小超出配置等，选择的文件不会出现在预览框中，只会显示错误信息
        maxFileCount: 5,
        maxFileSize: 1024 * 500,
        allowedFileExtensions: FILEINPUTEXTENDTION,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        //下面几个就是初始化预览图片的配置
        initialPreviewAsData: true,
        initialPreviewFileType: 'image',
        initialPreview: imgPaths, //要显示的图片的路径
        initialPreviewConfig: cfg,
        layoutTemplates: {
            // actionDelete: '',
            actionUpload: ''
        }
    }).on('filepredelete', function (event, key, jqXHR, data) {
        console.log('Key = ' + key);
        console.log(jqXHR);
        console.log(data);
        var selected = confirm("确定删除这个图片？删除后不可恢复");
        console.log("selected = " + selected);
        if (!selected) {
            return false;
        } else {
            var picture = $("#picture").val();
            var delPicture = key.split(";")[1];
            var pictureArr = picture.split(",");
            var result = "";
            for (var i = 0; i < pictureArr.length; i++) {
                if (delPicture != pictureArr[i]) {
                    result = result + pictureArr[i] + ",";
                }
            }
            if (result != "") {
                result = result.substr(0, result.length - 1);
            }
            $("#picture").val(result);
        }
    });
}






