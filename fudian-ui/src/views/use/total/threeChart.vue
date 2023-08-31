<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import {summaryE01z5,summaryTransfer} from '@/api/use/total'

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
    },
    autoResize: {
      type: Boolean,
      default: true
    },

  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
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
      this.chart = echarts.init(this.$el, 'macarons')
      summaryE01z5().then(response=>{
        let e01z5Data = response.data;
        summaryTransfer().then(response2=>{
          let transData = response2.data;
          let months = this.getSixAMonth();
          let trans = [];
          let e01z5s = [];
          months.map(item=>{
            let tran = 0;
            let e01z5 = 0;
            let month = item.toString().replace("-","");
            console.log(month);
            e01z5Data.map(item1=>{
              if(item1.month == month){
                e01z5 = item1.num;
              }
            })
            transData.map(item1=>{
              if(item1.month == month){
                tran = item1.num;
              }
            })
            trans.push(tran);
            e01z5s.push(e01z5);
          })
          this.setOptions({
            expectedData: e01z5s,
            actualData: trans,
            months:months
          })
        })
      })

    },
    getSixAMonth(){
      let data = new Date();
      let months = [];
      for(let i = 5;i>=0;i--){
        let year = data.getFullYear();
        let month = (data.getMonth()+1)-i;
        if(month<1){
          year = data.getFullYear() - 1;
          month = (data.getMonth()+1+12)-i
        }
        months.push(year + "-" + (month<10?'0' + month:month));
      }
      return months;
    },
    setOptions({ expectedData, actualData,months } = {}) {
      this.chart.setOption({
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: months,
          // offset:1,
          // // axisLabel: {interval:0,rotate:40 },
          // // boundaryGap: false,
          // boundaryGap: false,
          // axisLabel:{
          //   margin :5,
          //   padding:[0,10,0,10]
          // },
          // axisTick: {
          //   show: false
          // }
        },
        grid: {
          top:30,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          // axisPointer: {
          //   type: 'cross'
          // },
          // padding: [5, 10]
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
        legend: {
          data: ['档案转入', '档案转出']
        },
        series: [{
          name: '档案转入', itemStyle: {
            normal: {
              color: '#FF005A',
              lineStyle: {
                color: '#FF005A',
                width: 2
              }
            }
          },
          smooth: true,
          type: 'line',
          data: expectedData,
          animationDuration: 2800,
          animationEasing: 'cubicInOut'
        },
        {
          name: '档案转出',
          smooth: true,
          type: 'line',
          itemStyle: {
            normal: {
              color: '#3888fa',
              lineStyle: {
                color: '#3888fa',
                width: 2
              },
              areaStyle: {
                color: '#f3f8ff'
              }
            }
          },
          data: actualData,
          animationDuration: 2800,
          animationEasing: 'quadraticOut'
        }]
      })
    }
  }
}
</script>
