<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import { queryRoomA01Summary } from '@/api/room/arch'
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
      chart: null,
      a01Status:[
        {dictCode:1,fullName:'在库'},
        {dictCode:2,fullName:'已借出'}
      ],
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
      let datas = [];
      let rooms = [];
      queryRoomA01Summary('').then(response=>{
        let datas = response.data;
        //生成柱状图数据
        //取出所有库房列表
        response.data.map(item=>{
          let b = true;
          rooms.map(room=>{
            if(room==item.room_name){
              b = false;
            }
          })
          if(b){
            rooms.push(item.room_name);
          }
        })
        //取出数据列表
        let ccdatas = [];
        this.a01Status.map(item=>{
          //     name: 'pageB',
          //     type: 'bar',
          //     stack: 'vistors',
          //     barWidth: '60%',
          //     data: [80, 52, 200, 334, 390, 330, 220],
          //     animationDuration
          let ccdata = {
            name:item.fullName,
            data:[],
            stack: 'vistors',
            type:'bar',
            barWidth: '60%',
            animationDuration

          }
          rooms.map(room=>{
            let value = 0 ;
            response.data.map(item2=>{
              if(item2.status == item.dictCode && item2.room_name == room){
                value = item2.num;
              }
            })
            ccdata.data.push(value);
          })
          ccdatas.push(ccdata);
        })
        datas = ccdatas;
        console.log(datas);
        console.log(rooms);
        this.chart = echarts.init(this.$el, 'macarons')
        this.chart.setOption({
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
            data: rooms,
            axisTick: {
              alignWithLabel: true
            }
          }],
          yAxis: [{
            type: 'value',
            axisTick: {
              show: false
            }
          }],
          series:datas
        })
      })
      // this.chart = echarts.init(this.$el, 'macarons')
      //
      // this.chart.setOption({
      //   tooltip: {
      //     trigger: 'axis',
      //     axisPointer: { // 坐标轴指示器，坐标轴触发有效
      //       type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
      //     }
      //   },
      //   grid: {
      //     top: 10,
      //     left: '2%',
      //     right: '2%',
      //     bottom: '3%',
      //     containLabel: true
      //   },
      //   xAxis: [{
      //     type: 'category',
      //     data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      //     axisTick: {
      //       alignWithLabel: true
      //     }
      //   }],
      //   yAxis: [{
      //     type: 'value',
      //     axisTick: {
      //       show: false
      //     }
      //   }],
      //   series: [{
      //     name: 'pageA',
      //     type: 'bar',
      //     stack: 'vistors',
      //     barWidth: '60%',
      //     data: [79, 52, 200, 334, 390, 330, 220],
      //     animationDuration
      //   }, {
      //     name: 'pageB',
      //     type: 'bar',
      //     stack: 'vistors',
      //     barWidth: '60%',
      //     data: [80, 52, 200, 334, 390, 330, 220],
      //     animationDuration
      //   }, {
      //     name: 'pageC',
      //     type: 'bar',
      //     stack: 'vistors',
      //     barWidth: '60%',
      //     data: [30, 52, 200, 334, 390, 330, 220],
      //     animationDuration
      //   }]
      // })
    }
  }
}
</script>
