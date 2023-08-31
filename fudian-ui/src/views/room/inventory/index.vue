<template>
  <div class="app-inventory">
    <el-row>
      <el-col :style="leftStyleName">
        <!-- 树结构 -->
        <div v-if="leftTree1" class="tree">
          <el-scrollbar wrap-class="scrollbar-wrapper">
            <div class="tree tree-style">
              <el-tree
                ref="roomNode"
                :data="data"
                node-key="id"
                highlight-current
                :expand-on-click-node="false"
                :props="defaultProps"
                icon-class="el-icon-folder-opened"
                :default-expand-all="true"
                @node-click="nodeClick"
              />
            </div>
          </el-scrollbar>
        </div>
        <div style="right: 0;top: 0;height: calc(100vh - 86px);width: 10px;position: absolute;"><div style="position: absolute;padding: 20px 0;width: 16px;height: 16px;left: 0;right: 0;top: 0;bottom: 0;margin: auto;" @click="divClick"><i :class="picClassname" /></div></div>
      </el-col>
      <el-col :span="23" :style="rightStyleName">
        <el-row :gutter="32" style="margin: 0 auto;margin-top: 16px">
          <el-col :xs="24" :sm="24" :lg="12">
            <div style="text-align: center;margin-top:30px;margin-left:5%;width: 90%;float: left;box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)">
              <div style="text-align: center;width: 100%">
                <div id="echarts" class="chart" :style="{height:chartheight,width:chartwidth}" />
              </div>
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="12">
            <div style="text-align: center;width: 90%;margin-top:30px;margin-left:5%;float: left;box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)">
              <div style="text-align: center;width: 90%">
                <div id="container" :hidden="zhidden" class="chart" :style="{height:chartheight,width:chartwidth,}" />
              </div>
            </div>
          </el-col>
        </el-row>
<!--        <div style="width: 100%;height:50px"/>-->



<!--        <room-chart :width="chartwidth" :height="chartheight" :chartData="chartData" />-->
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import roomChart from './chart'
import { queryRoomList } from '@/api/room/room'
import { queryRoomA01Summary } from '@/api/room/arch'
import echarts from "echarts";
require('echarts/theme/macarons')
const animationDuration = 6000
export default {
  // components: {
  //   roomChart
  // },
  data() {
    return {
      zhidden:false,
      cchart:'',
      chart:'',

      coption : {

        tooltip: {
          trigger: 'axis',
          axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data:['在库','已借出']
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
            data: []
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          // {
          //   name: '直接访问',
          //   type: 'bar',
          //   data: [320, 332, 301, 334, 390, 330, 320]
          // },
          // {
          //   name: '邮件营销',
          //   type: 'bar',
          //   stack: '广告',
          //   data: [120, 132, 101, 134, 90, 230, 210]
          // },
          // {
          //   name: '联盟广告',
          //   type: 'bar',
          //   stack: '广告',
          //   data: [220, 182, 191, 234, 290, 330, 310]
          // },
          // {
          //   name: '视频广告',
          //   type: 'bar',
          //   stack: '广告',
          //   data: [150, 232, 201, 154, 190, 330, 410]
          // },
          // {
          //   name: '搜索引擎',
          //   type: 'bar',
          //   data: [862, 1018, 964, 1026, 1679, 1600, 1570],
          //   // markLine: {
          //   //   lineStyle: {
          //   //     type: 'dashed'
          //   //   },
          //   //   data: [
          //   //     [{type: 'min'}, {type: 'max'}]
          //   //   ]
          //   // }
          // },
          // {
          //   name: '百度',
          //   type: 'bar',
          //   barWidth: 5,
          //   stack: '搜索引擎',
          //   data: [620, 732, 701, 734, 1090, 1130, 1120]
          // },
          // {
          //   name: '谷歌',
          //   type: 'bar',
          //   stack: '搜索引擎',
          //   data: [120, 132, 101, 134, 290, 230, 220]
          // },
          // {
          //   name: '必应',
          //   type: 'bar',
          //   stack: '搜索引擎',
          //   data: [60, 72, 71, 74, 190, 130, 110]
          // },
          // {
          //   name: '其他',
          //   type: 'bar',
          //   stack: '搜索引擎',
          //   data: [62, 82, 91, 84, 109, 110, 120]
          // }
        ]
      },
      option:{
        title: {
          text: '库房盘点统计',
          subtext: '123',
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
          data: []
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: '65%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      a01Status:[
        {dictCode:1,fullName:'在库'},
        {dictCode:2,fullName:'已借出'}
      ],
      datas:[],
      picClassname: 'el-icon-caret-left',
      rightStyle: {
        width: '85%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
      },
      rightStyle1: {
        width: '99.5%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
      },
      leftStyle: {
        width: '15%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyle1: {
        width: '0.5%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyleName: {
        width: '15%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      rightStyleName: {
        width: '85%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
      },
      leftTree1: true,
      chartwidth: '100%',
      chartheight: '400px',
      // 树结构数据

      defaultProps: {
        children: 'children',
        // hasChildren: 'hasChildren',
        label: 'roomName',
        isLeaf: 'hasChildren'
      },
      data: []
    }
  },
  created() {
    this.getRoomList();
    this.getDatas('000');
    window.onresize = ()=>{
      this.chart.resize();
      this.cchart.resize();
    }

  },
  mounted() {

    // this.labelOption.position = this.app.config.position;
    // this.labelOption.distance = this.app.config.distance;
    // this.labelOption.align = this.app.config.align;
    // this.labelOption.verticalAlign = this.app.config.verticalAlign;
    // this.labelOption.rotate = this.app.config.rotate;
    //
    // this.coption.series[0].label = this.labelOption
    // this.coption.series[1].label = this.labelOption
    // this.coption.series[2].label = this.labelOption
    // this.coption.series[3].label = this.labelOption
    // this.distance.position = this.app.config.distance;
    // this.distance.position = this.app.config.distance;


    let data = [];
    this.a01Status.map(item=>{
      // this.chartData.data
      data.push(item.fullName);

    })
    this.option.legend.data = data;
    this.coption.legend.data = data;
    this.option.series[0].name = '所有库房';
    this.option.title.subtext = '所有库房';
    this.$nextTick(() => {
      this.initCharts();
    })
  },
  methods: {
    initCharts(){
      this.chart = echarts.init(document.getElementById("echarts"),'macarons');
      this.chart.setOption(this.getOption(),true);

      this.cchart = echarts.init(document.getElementById("container"),'macarons');
      this.cchart.setOption(this.coption,true);
    },
    getOption(){
      return this.option;
    },
    getDatas(roomId){
      if(roomId=='000'){
        roomId = '';
        // this.chartData.roomName = '所有库房';
      }
      queryRoomA01Summary(roomId).then(response=>{
        this.datas = response.data;
        this.option.series[0].data = [];
        //饼形图数据生成
        this.a01Status.map(item=>{
          let data = '';
          response.data.map(item2=>{
            if(item2.status == item.dictCode){
              if(data!=''){
                data.value = item2.num + data.value;
              }else{
                data = {name :item.fullName,value:item2.num};
              }
            }
          })
          if(data==''){
            data = {name :item.fullName,value:0};
          }
          this.option.series[0].data.push(data);
        })
        this.chart.setOption(this.getOption(),true);
        let rooms = [];
        if(roomId == ''){
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
          this.coption.xAxis[0].data = rooms;
          this.coption.series = ccdatas;
          this.cchart.setOption(this.coption,true);
        }
      })
    },
    divClick() {
      this.leftTree1 = !this.leftTree1
      if (this.leftTree1) {
        this.rightStyleName = this.rightStyle
        this.leftStyleName = this.leftStyle
        this.picClassname = 'el-icon-caret-left'
      } else {
        this.rightStyleName = this.rightStyle1
        this.leftStyleName = this.leftStyle1
        this.picClassname = 'el-icon-caret-right'
      }
    },
    nodeClick(no, node, tree) {
      this.option.title.subtext = node.data.roomName;
      this.getDatas(node.data.id)

    },
    getRoomList() {
      queryRoomList(null).then(response => {
        // this.data = response.data
        this.data = [{
          id:'000',
          roomName:'库房列表',
          children:response.data
        }]
      })
    }

  }
}
</script>

<style lang="scss" scoped>
.app-inventory {
  height: calc(100vh - 86px);
}
.tree-style{
  /deep/.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
    background: rgba(204, 204, 204, 0.18);
  }

}
.tree {
  width: 100%;
  padding: 10px 10px 26px 10px;
  /*border-right: 1px solid #e2e3e6;*/
}
</style>
