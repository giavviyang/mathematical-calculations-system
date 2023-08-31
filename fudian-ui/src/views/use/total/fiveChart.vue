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
    setOptions(options){
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data:["在库","已借出"]
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
          data: options.rooms,
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
        series:options.datas
      })
    },
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
          let ccdata = {
            name:item.fullName,
            data:[],
            type:'bar',
            barGap: 0,
            barWidth: '30',
            label:{
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
        console.log(ccdatas);
        this.setOptions({rooms:rooms,datas:datas});
      })

    }
  }
}
</script>
