<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
  import echarts from 'echarts'
  require('echarts/theme/macarons') // echarts theme
  import resize from './mixins/resize'
  import {summaryE01z9} from '@/api/use/total'

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
        summaryE01z9().then(response=>{
          let data = response.data;
          let year = new Date().getFullYear();
          let years = []
          for(let i = 4;i>=0;i--){
            years.push(year - i);
          }
          let batchNums = [];
          let nums = [];
          years.map(item=>{
            let batchNum = 0;
            let num = 0;
            data.map(ditem=>{
              if(item == ditem.year){
                batchNum = ditem.batchNum;
                num = ditem.num;
              }
            })
            batchNums.push(batchNum);
            nums.push(num);
          })
          // console.log(years);
          // console.log(batchNums);
          // console.log(nums);
          this.initChart({years,batchNums,nums})
        })
        // this.initChart()
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
      initChart(option) {
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
            data:["借阅次数","借阅本数"]
          },
          grid: {
            // top: 10,
            left: '2%',
            right: '2%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [{
            type: 'category',
            axisTick: {show: false},
            data: option.years
          }],
          yAxis: [{
            type: 'value',
            // axisTick: {
            //   show: false
            // }
          }],
          series:[
            {
              name: '借阅次数',
              type: 'bar',
              barWidth : 30,
              barGap: 0,
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
              data: option.batchNums
            },
            {
              name: '借阅本数',
              type: 'bar',
              barWidth : 30,
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
              data: option.nums
            },

          ]

        })


      }
    }
  }
</script>
