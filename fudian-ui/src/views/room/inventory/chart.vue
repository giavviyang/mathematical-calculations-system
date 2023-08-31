<template>
  <div id="echarts" :class="className" :style="{height:height,width:width}" />
</template>

<script>
  import echarts from 'echarts'
  require('echarts/theme/macarons') // echarts theme

  export default{
    props:{
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
        default: '350px'
      },
      autoResize: {
        type: Boolean,
        default: true
      },
      chartData: {
        type: Object,
        required: false
      }
    },
    data(){
      return {
        chart:'',
        option:{
          title: {
            text: '库房盘点统计',
            subtext: this.chartData.roomName,
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            data: this.chartData.data
          },
          series: [
            {
              name: this.chartData.roomName,
              type: 'pie',
              radius: '65%',
              center: ['50%', '50%'],
              selectedMode: 'single',
              data: this.chartData.datas,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
      }
    },
    created() {
      this.$nextTick(() => {
        this.initCharts()
      })
    },
    methods: {
      initCharts(){
        this.chart = echarts.init(document.getElementById("echarts"));
        this.chart.setOption(this.getOption(),true);
      },
      getOption(){
        return this.option;
      }
    }
  }
</script>
