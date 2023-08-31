<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import {querySummaryA01Status} from '@/api/use/total'
const animationDuration = 3000

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
      querySummaryA01Status().then(response=>{
        let data = response.data;
        let xAxisData = [];
        let summaryData = [];
        data.map(item=>{
          xAxisData.push(item.full_name);
          summaryData.push(item.num);
        })
        this.initChart({xAxisData,summaryData})
      })

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
    initChart(options) {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        // color:['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570'],
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data:['数量']
        },
        grid: {
          // top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          // type: 'category',
          data: options.xAxisData,
          type: 'category',
          axisTick: {show: false},
        }],
        yAxis: [{
          type: 'value',
          // axisTick: {
          //   show: false
          // }
        }],
        series:{
          barWidth : 30,
          data:options.summaryData,
          name:'数量',
          type: 'bar',
          label: {
            show: true,
            position: 'top',
            distance: 15,
            align: 'center',
            verticalAlign: 'middle',
            rotate: 0,
            // formatter: '{c}  {name|{a}}',
            fontSize: 13,
            rich: {
              name: {
                color:'#fff'
              }
            }
          },
        }

        })

    }
  }
}
</script>
