<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import {
  getA01States
} from '@/api/home'
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
      /*getA01States().then(res=>{
        let a01Datas = res.data;
        let nameList = [];
        let dataList = [];
        for(let n in a01Datas){
          nameList.push(a01Datas[n].name);
          dataList.push(a01Datas[n].value);
        }
        this.chart = echarts.init(this.$el, 'macarons')
        this.chart.setOption({
          // color:['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570'],
          tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
              type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            top: 10,
            left: '2%',
            right: '2%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [{
            type: 'category',
            data: nameList,
            axisTick: {
              alignWithLabel: true
            }
          }],
          yAxis: [{
            type: 'value',
            // axisTick: {
            //   show: false
            // }
          }],
          series:{barWidth : 30,data:dataList,type: 'bar'}
          // series: [{
          //   name: '在职',
          //   type: 'bar',
          //   stack: 'vistors',
          //   barWidth: '60%',
          //   data: 321,
          //   animationDuration
          // }, {
          //   name: '离职',
          //   type: 'bar',
          //   stack: 'vistors',
          //   barWidth: '60%',
          //   data: [50],
          //   animationDuration
          // }, {
          //   name: '退休',
          //   type: 'bar',
          //   stack: 'vistors',
          //   barWidth: '60%',
          //   data: [50],
          //   animationDuration
          // }, {
          //   name: '死亡',
          //   type: 'bar',
          //   stack: 'vistors',
          //   barWidth: '60%',
          //   data: [1],
          //   animationDuration
          // }]
        })
      })*/


      // this.chart = echarts.init(this.$el, 'macarons')
      //
      // this.chart.setOption({
      //   tooltip: {
      //     trigger: 'axis',
      //     axisPointer: { // 坐标轴指示器，坐标轴触发有效
      //       type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
      //     }
      //   },
      //   radar: {
      //     radius: '66%',
      //     center: ['50%', '42%'],
      //     splitNumber: 8,
      //     splitArea: {
      //       areaStyle: {
      //         color: 'rgba(127,95,132,.3)',
      //         opacity: 1,
      //         shadowBlur: 45,
      //         shadowColor: 'rgba(0,0,0,.5)',
      //         shadowOffsetX: 0,
      //         shadowOffsetY: 15
      //       }
      //     },
      //     indicator: [
      //       { name: 'Sales', max: 10000 },
      //       { name: 'Administration', max: 20000 },
      //       { name: 'Information Techology', max: 20000 },
      //       { name: 'Customer Support', max: 20000 },
      //       { name: 'Development', max: 20000 },
      //       { name: 'Marketing', max: 20000 },
      //       { name: 'Development2', max: 20000 },
      //       { name: 'Marketing2', max: 20000 },
      //       { name: 'Development3', max: 20000 },
      //       { name: 'Marketing3', max: 20000 }
      //     ]
      //   },
      //   legend: {
      //     left: 'center',
      //     bottom: '10',
      //     data: ['Allocated Budget', 'Expected Spending', 'Actual Spending']
      //   },
      //   series: [{
      //     type: 'radar',
      //     symbolSize: 0,
      //     areaStyle: {
      //       normal: {
      //         shadowBlur: 13,
      //         shadowColor: 'rgba(0,0,0,.2)',
      //         shadowOffsetX: 0,
      //         shadowOffsetY: 10,
      //         opacity: 1
      //       }
      //     },
      //     data: [
      //       {
      //         value: [5000, 7000, 12000, 11000, 15000, 14000],
      //         name: 'Allocated Budget'
      //       },
      //       {
      //         value: [4000, 9000, 15000, 15000, 13000, 11000],
      //         name: 'Expected Spending'
      //       },
      //       {
      //         value: [5500, 11000, 12000, 15000, 12000, 12000],
      //         name: 'Actual Spending'
      //       }
      //     ],
      //     animationDuration: animationDuration
      //   }]
      // })
    }
  }
}
</script>
