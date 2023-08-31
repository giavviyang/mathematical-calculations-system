<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import {
  getRoomA01States
} from '@/api/home'

const animationDuration = 6000

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      /*getRoomA01States().then(res=>{
        let roomDatas = res.data;
        this.chart = echarts.init(this.$el, 'macarons')
        let name = [];
        for(let n in roomDatas){
          name.push(roomDatas[n].name)
        }
        this.chart.setOption({
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            left: 'center',
            bottom: '10',
            data: name
            // data: ['Industries', 'Technology', 'Forex', 'Gold', 'Forecasts']
          },
          series: [
            {
              name: '库房整体盘点统计',
              type: 'pie',
              roseType: 'radius',
              radius: [15, 95],
              center: ['50%', '45%'],
              data: roomDatas,
              animationEasing: 'cubicInOut',
              animationDuration: 2600
            }
          ]
        })
      })*/

    }
    // initChart() {
    //   this.chart = echarts.init(this.$el, 'macarons')

    //   this.chart.setOption({
    //     tooltip: {
    //       trigger: 'axis',
    //       axisPointer: { // 坐标轴指示器，坐标轴触发有效
    //         type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
    //       }
    //     },
    //     grid: {
    //       top: 10,
    //       left: '2%',
    //       right: '2%',
    //       bottom: '3%',
    //       containLabel: true
    //     },
    //     xAxis: [{
    //       type: 'category',
    //       data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    //       axisTick: {
    //         alignWithLabel: true
    //       }
    //     }],
    //     yAxis: [{
    //       type: 'value',
    //       axisTick: {
    //         show: false
    //       }
    //     }],
    //     series: [{
    //       name: 'pageA',
    //       type: 'bar',
    //       stack: 'vistors',
    //       barWidth: '60%',
    //       data: [79, 52, 200, 334, 390, 330, 220],
    //       animationDuration
    //     }, {
    //       name: 'pageB',
    //       type: 'bar',
    //       stack: 'vistors',
    //       barWidth: '60%',
    //       data: [80, 52, 200, 334, 390, 330, 220],
    //       animationDuration
    //     }, {
    //       name: 'pageC',
    //       type: 'bar',
    //       stack: 'vistors',
    //       barWidth: '60%',
    //       data: [30, 52, 200, 334, 390, 330, 220],
    //       animationDuration
    //     }]
    //   })
    // }
  }
}
</script>
