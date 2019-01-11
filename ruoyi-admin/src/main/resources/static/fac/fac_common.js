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
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
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
var colors = ['#5793f3', '#d14a61', '#675bba'];
var orderOption = {
    color: colors,
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    legend: {
        data: ['订单金额', '新增用户']
    },
    grid: {
        top: 70,
        bottom: 50
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLine: {
                onZero: false,
                lineStyle: {
                    color: colors[1]
                }
            },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return '订单金额  ' + params.value
                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                    }
                }
            },
            data: []
        },
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLine: {
                onZero: false,
                lineStyle: {
                    color: colors[0]
                }
            },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return '新增用户  ' + params.value
                            + (params.seriesData.length ? '：' + params.seriesData[1].data : '');
                    }
                }
            },
            data: []
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
            type: 'line',
            xAxisIndex: 1,
            smooth: true,
            data: []
        },
        {
            name: '',
            type: 'line',
            smooth: true,
            data: []
        }
    ]
};

// 新增用户统计
var userOption = {
    tooltip: {
        trigger: 'axis'
    },
    xAxis: {
        type: 'category',
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [],
        type: 'line',
        smooth: true
    }]
};



