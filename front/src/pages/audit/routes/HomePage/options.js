/*
 * @(#) options.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-08-17 15:04:03
 */
export const stateOption = {
    title:{
        text:"审批状态占比统计",
        padding: [0,0,0,15],
        textStyle:{
            color:"#999",
            fontSize:14,
            fontWeight: 'normal',
        }
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
        orient: 'vertical',
        x:'65%',      //可设定图例在左、右、居中
        y:'center',     //可设定图例在上、下、居中
        data: ['审批通过', '审批中', '审批不通过']
    },
    color:['#52CAFC', '#FFBA56','#FF5648'],
    series: [
        {
            name: '统计占比',
            type: 'pie',
            radius: ['54%', '70%'],
            center: ["35%", "50%"],
            avoidLabelOverlap: false,
            label: {
                show: false,
                position: 'center'
            },
            emphasis: {
                label: {
                    show: true,
                    fontSize: '16',
                    fontWeight: 'bold'
                }
            },
            labelLine: {
                show: false
            },
            data: [
                {value: 0, name: '审批通过'},
                {value: 0, name: '审批中'},
                {value: 0, name: '审批不通过'},
            ]
        }
    ]
};

export const schoolOption = {
    // backgroundColor: '#FFFFFF',
    // backgroundColor: '#FFF',
    title: {
        text: '审批通过星级学校分布占比统计',
        padding: [0,0,0,15],
        textStyle:{
            color:"#999",
            fontSize:14,
            fontWeight: 'normal',
        }
    },

    tooltip: {
        trigger: 'item',
        formatter: '{b} <br/>{a} : {c} ({d}%)'
    },
    legend: {
        orient: 'vertical',
        x: '65%',
        y: 'center',
        data: ['一星','二星','三星','四星','五星']
    },
    color:["#56CAFD","#9ACA61","#05B6B8","#FFBB54","#FF554D"],
    series: [
        {
            name: '数量占比',
            type: 'pie',
            radius: '75%',
            center: ["35%", "50%"],
            data: [
                {value: 1, name: '一星'},
                {value: 7, name: '二星'},
                {value: 10, name: '三星'},
                {value: 4, name: '四星'},
                {value: 9, name: '五星'}
            ]
                // .sort(function (a, b) { return a.value - b.value; })
            ,
            itemStyle: {
                // normal:{
                //     color:function(params) {
                //         //自定义颜色
                //         let colorList = ["#56CAFD","#9ACA61","#05B6B8","#FFBB54","#FF554D"];
                //         return colorList[params.dataIndex]
                //     }
                // }
            },
            roseType: 'radius',
            label: {
                color: 'rgba(255, 255, 255, 0.3)'
            },
            labelLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 0.3)'
                },
                smooth: 0.2,
                length: 10,
                length2: 20
            },
            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function (idx) {
                return Math.random() * 200;
            }
        }
    ]
};

export const stateTotalBar = {
    title:{
        text:"已通过学校按区域统计",
        padding: [0,0,0,15],
        textStyle:{
            color:"#999",
            fontSize:14,
            fontWeight: 'normal',
        }
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        },
    },
    legend: {
        bottom:8,
        left:'center',
        data: ['100-120分','120-140分', '140-160分', '160-180分', '180分以上'],
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '15%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: ['思明区', '湖里区',"集美区", '海沧区', '同安区', '翔安区', '市属区'],
            axisLine:{
                lineStyle:{
                    color:"#CCC",
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#000'
                }
            },
            axisTick: {
                show: false
            }
        }
    ],
    yAxis: [
        {
            type: 'value',
            minInterval: 1,
            axisLine:{
                lineStyle:{
                    color:"#CCC",
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#000'
                }
            },
            axisTick: {
                show: false
            }
        }
    ],
    series: [
        {
            name: '100-120分',
            type: 'bar',
            data: [0, 0, 0,0,0,0,0],
            stack: '统计',
            itemStyle:{
                normal:{color:"#0141B1"},
            }
        },
        {
            name: '120-140分',
            type: 'bar',
            data: [0, 0,0,0,0,0,0],
            stack: '统计',
            barWidth: 16,
            itemStyle:{
                normal:{color:"#1066CE"},
            }
        },
        {
            name: '140-160分',
            type: 'bar',
            data: [0, 0, 0,0,0,0,0],
            stack: '统计',
            barWidth: 16,
            itemStyle:{
                normal:{color:"#3389E9"},
            }
        },
        {
            name: '160-180分',
            type: 'bar',
            data: [0, 0, 0,0,0,0,0],
            stack: '统计',
            barWidth: 16,
            itemStyle:{
                normal:{color:"#77BEFC"},
            }
        },
        {
            name: '180分以上',
            type: 'bar',
            data: [0, 0, 0,0,0,0,0],
            stack: '统计',
            barWidth: 16,
            itemStyle:{
                normal:{color:"#ACD7FF"},
            }
        }
    ]
};

export const schoolTotalBar = {
    title:{
        text:"已审批通过学校类型统计",
        padding: [0,0,0,15],
        textStyle:{
            color:"#999",
            fontSize:14,
            fontWeight: 'normal',
        }
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        },
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '15%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: ['小学', '初中', '高中', '中职', '九年制', '其他'],
            axisLine:{
                lineStyle:{
                    color:"#CCC",
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#000'
                }
            },
            axisTick: {
                show: false
            }
        }
    ],
    yAxis: [
        {
            type: 'value',
            minInterval: 1,
            axisLine:{
                lineStyle:{
                    color:"#CCC",
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#000'
                }
            },
            axisTick: {
                show: false
            }
        }
    ],
    series: [
        {
            name: '数量',
            type: 'bar',
            barWidth: 16,
            data: [0, 0, 0, 0, 0, 0, 0],
            itemStyle: {
                //通常情况下：
                normal: {
                    //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                    color: function (params) {
                        let colorList = ["#54CCFF","#98CC67","#01BABC","#FFBC55","#FF5544","#3288ED"];
                        return colorList[params.dataIndex];
                    }
                },
            }
        }
    ]
};


